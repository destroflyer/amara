package amara.applications.ingame.entitysystem.systems.effects.triggers;

import java.util.LinkedList;
import amara.applications.ingame.entitysystem.components.buffs.status.*;
import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.casts.*;
import amara.applications.ingame.entitysystem.components.game.NextEffectActionIndexComponent;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.*;
import amara.applications.ingame.entitysystem.systems.conditions.ConditionUtil;
import amara.applications.ingame.entitysystem.systems.spells.casting.SetCooldownOnCastingSystem;
import amara.applications.ingame.entitysystem.systems.targets.TargetUtil;
import amara.applications.ingame.shared.maps.Map;
import amara.core.Util;
import amara.libraries.entitysystem.*;

public class EffectTriggerUtil {

    public static LinkedList<EntityWrapper> triggerEffects(EntityWorld entityWorld, int[] effectTriggerEntities, int targetEntity) {
        return triggerEffects(entityWorld, effectTriggerEntities, targetEntity, -1);
    }

    public static LinkedList<EntityWrapper> triggerEffects(EntityWorld entityWorld, int[] effectTriggerEntities, int targetEntity, int effectActionIndex) {
        LinkedList<EntityWrapper> effectCasts = new LinkedList<>();
        for (int effectTriggerEntity : effectTriggerEntities) {
            EntityWrapper effectCast = triggerEffect(entityWorld, effectTriggerEntity, targetEntity, effectActionIndex);
            if (effectCast != null) {
                effectCasts.add(effectCast);
                effectActionIndex = effectCast.getComponent(EffectSourceActionIndexComponent.class).getIndex();
            }
        }
        return effectCasts;
    }

    public static EntityWrapper triggerEffect(EntityWorld entityWorld, int effectTriggerEntity, int targetEntity) {
        return triggerEffect(entityWorld, effectTriggerEntity, targetEntity, -1);
    }

    private static EntityWrapper triggerEffect(EntityWorld entityWorld, int effectTriggerEntity, int targetEntity, int effectActionIndex) {
        if (areTriggerConditionsMet(entityWorld, effectTriggerEntity, targetEntity)) {
            EntityWrapper effectCast = entityWorld.getWrapped(entityWorld.createEntity());
            TriggeredEffectComponent triggeredEffectComponent = entityWorld.getComponent(effectTriggerEntity, TriggeredEffectComponent.class);
            // Check if this trigger and his effect have already been cleanuped
            if (triggeredEffectComponent != null) {
                int effectEntity = triggeredEffectComponent.getEffectEntity();
                effectCast.setComponent(new PrepareEffectComponent(effectEntity));
                if (effectActionIndex == -1) {
                    effectActionIndex = getAndIncreaseNextEffectActionIndex(entityWorld);
                }
                effectCast.setComponent(new EffectSourceActionIndexComponent(effectActionIndex));
                TriggerSourceComponent triggerSourceComponent = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class);
                if (triggerSourceComponent != null) {
                    // EffectSource
                    EffectSourceComponent effectSourceComponent = entityWorld.getComponent(triggerSourceComponent.getSourceEntity(), EffectSourceComponent.class);
                    int effectSourceEntity = ((effectSourceComponent != null) ? effectSourceComponent.getSourceEntity() : triggerSourceComponent.getSourceEntity());
                    effectCast.setComponent(new EffectSourceComponent(effectSourceEntity));
                    // EffectSourceSpell
                    EntityUtil.transferComponents(entityWorld, triggerSourceComponent.getSourceEntity(), effectCast.getId(), new Class[] {
                        EffectSourceSpellComponent.class
                    });
                }
                if (targetEntity != -1) {
                    effectCast.setComponent(new EffectCastTargetComponent(targetEntity));
                }
                int[] targetEntities = getTargetEntities(entityWorld, effectTriggerEntity, targetEntity);
                effectCast.setComponent(new AffectedTargetsComponent(targetEntities));
                TriggerDelayComponent triggerDelayComponent = entityWorld.getComponent(effectTriggerEntity, TriggerDelayComponent.class);
                if (triggerDelayComponent != null) {
                    effectCast.setComponent(new RemainingEffectDelayComponent(triggerDelayComponent.getDuration()));
                }
                SetCooldownOnCastingSystem.setOnCooldown(entityWorld, effectTriggerEntity);
                TriggerOnceComponent triggerOnceComponent = entityWorld.getComponent(effectTriggerEntity, TriggerOnceComponent.class);
                if (triggerOnceComponent != null) {
                    if (triggerOnceComponent.isRemoveEntity()) {
                        entityWorld.removeEntity(effectTriggerEntity);
                    } else {
                        entityWorld.removeComponent(effectTriggerEntity, TriggerSourceComponent.class);
                    }
                }
                return effectCast;
            }
        }
        return null;
    }

    private static boolean areTriggerConditionsMet(EntityWorld entityWorld, int effectTriggerEntity, int targetEntity) {
        TriggerConditionsComponent triggerConditionsComponent = entityWorld.getComponent(effectTriggerEntity, TriggerConditionsComponent.class);
        if (triggerConditionsComponent != null) {
            for (int conditionEntity : triggerConditionsComponent.getConditionEntities()) {
                int[] conditionTargetEntities = getTargetEntities(entityWorld, conditionEntity, targetEntity);
                for (int conditionTargetEntity : conditionTargetEntities) {
                    if (!ConditionUtil.isConditionMet(entityWorld, conditionEntity, conditionTargetEntity)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static int getAndIncreaseNextEffectActionIndex(EntityWorld entityWorld) {
        int nextEffectActionIndex = entityWorld.getComponent(Map.GAME_ENTITY, NextEffectActionIndexComponent.class).getIndex();
        entityWorld.setComponent(Map.GAME_ENTITY, new NextEffectActionIndexComponent(nextEffectActionIndex + 1));
        return nextEffectActionIndex;
    }

    private static LinkedList<Integer> tmpTargetEntities = new LinkedList<>();
    private static int[] getTargetEntities(EntityWorld entityWorld, int entity, int targetEntity) {
        tmpTargetEntities.clear();
        TriggerSourceComponent triggerSourceComponent = entityWorld.getComponent(entity, TriggerSourceComponent.class);
        if (triggerSourceComponent != null) {
            int triggerSourceEntity = triggerSourceComponent.getSourceEntity();
            if (entityWorld.hasComponent(entity, SourceTargetComponent.class)){
                tmpTargetEntities.add(triggerSourceEntity);
            }
            EffectSourceComponent effectSourceComponent = entityWorld.getComponent(triggerSourceEntity, EffectSourceComponent.class);
            if (effectSourceComponent != null) {
                int effectSourceEntity = effectSourceComponent.getSourceEntity();
                if (entityWorld.hasComponent(entity, CasterTargetComponent.class)) {
                    tmpTargetEntities.add(effectSourceEntity);
                }
                RuleTargetComponent ruleTargetComponent = entityWorld.getComponent(entity, RuleTargetComponent.class);
                if (ruleTargetComponent != null) {
                    for (int otherEntity : entityWorld.getEntitiesWithAll()) {
                        if (TargetUtil.isValidTarget(entityWorld, effectSourceEntity, otherEntity, ruleTargetComponent.getTargetRulesEntity())) {
                            tmpTargetEntities.add(otherEntity);
                        }
                    }
                }
            }
        }
        if (entityWorld.hasComponent(entity, TargetTargetComponent.class)) {
            tmpTargetEntities.add(targetEntity);
        }
        BuffTargetsTargetComponent buffTargetTargetComponent = entityWorld.getComponent(entity, BuffTargetsTargetComponent.class);
        if (buffTargetTargetComponent != null) {
            for (int buffStatusEntity : entityWorld.getEntitiesWithAny(ActiveBuffComponent.class)) {
                ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class);
                if (activeBuffComponent.getBuffEntity() == buffTargetTargetComponent.getBuffEntity()) {
                    tmpTargetEntities.add(activeBuffComponent.getTargetEntity());
                }
            }
        }
        CustomTargetComponent customTargetComponent = entityWorld.getComponent(entity, CustomTargetComponent.class);
        if (customTargetComponent != null) {
            for (int customTargetEntity : customTargetComponent.getTargetEntities()) {
                tmpTargetEntities.add(customTargetEntity);
            }
        }
        TeamTargetComponent teamTargetComponent = entityWorld.getComponent(entity, TeamTargetComponent.class);
        if (teamTargetComponent != null) {
            for (int entityInTeam : entityWorld.getEntitiesWithAny(TeamComponent.class)) {
                int teamEntity = entityWorld.getComponent(entityInTeam, TeamComponent.class).getTeamEntity();
                if (teamEntity == teamTargetComponent.getTeamEntity()) {
                    tmpTargetEntities.add(entityInTeam);
                }
            }
        }
        return Util.convertToArray_Integer(tmpTargetEntities);
    }
}
