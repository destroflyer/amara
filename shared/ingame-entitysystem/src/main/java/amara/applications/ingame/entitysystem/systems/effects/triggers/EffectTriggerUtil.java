package amara.applications.ingame.entitysystem.systems.effects.triggers;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import amara.applications.ingame.entitysystem.components.buffs.status.*;
import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.casts.*;
import amara.applications.ingame.entitysystem.components.game.NextEffectActionIndexComponent;
import amara.applications.ingame.entitysystem.components.physics.PositionComponent;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.*;
import amara.applications.ingame.entitysystem.systems.conditions.ConditionUtil;
import amara.applications.ingame.entitysystem.systems.spells.casting.SetCooldownOnCastingSystem;
import amara.applications.ingame.entitysystem.systems.targets.TargetUtil;
import amara.applications.ingame.shared.maps.Map;
import amara.core.Util;
import amara.libraries.entitysystem.*;
import com.jme3.math.Vector2f;

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
                removeTriggerOnceTrigger(entityWorld, effectTriggerEntity);
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

    private static List<Integer> tmpTargetEntities = new LinkedList<>();
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
                if (entityWorld.hasComponent(entity, SourceCasterTargetComponent.class)) {
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
        if (entityWorld.hasComponent(entity, TargetCasterTargetComponent.class)) {
            EffectSourceComponent targetEffectSourceComponent = entityWorld.getComponent(targetEntity, EffectSourceComponent.class);
            if (targetEffectSourceComponent != null) {
                tmpTargetEntities.add(targetEffectSourceComponent.getSourceEntity());
            }
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
        if (entityWorld.hasComponent(entity, ExcludeTargetTargetComponent.class)) {
            tmpTargetEntities.remove((Integer) targetEntity);
        }
        MaximumTargetsComponent maximumTargetsComponent = entityWorld.getComponent(entity, MaximumTargetsComponent.class);
        if ((maximumTargetsComponent != null) && (tmpTargetEntities.size() > maximumTargetsComponent.getMaximum())) {
            Stream<Integer> targetStream = tmpTargetEntities.stream();
            if (triggerSourceComponent != null) {
                // Decide priority by distance if existing
                PositionComponent relevantPositionComponent = entityWorld.getComponent(triggerSourceComponent.getSourceEntity(), PositionComponent.class);
                if (relevantPositionComponent == null) {
                    relevantPositionComponent = entityWorld.getComponent(targetEntity, PositionComponent.class);;
                }
                if (relevantPositionComponent != null) {
                    Vector2f relevantPosition = relevantPositionComponent.getPosition();
                    targetStream = targetStream.sorted((target1, target2) -> {
                        PositionComponent positionComponentTarget1 = entityWorld.getComponent(target1, PositionComponent.class);
                        PositionComponent positionComponentTarget2 = entityWorld.getComponent(target2, PositionComponent.class);
                        if ((positionComponentTarget1 != null) && (positionComponentTarget2 != null)) {
                            float distanceSquaredTarget1 = relevantPosition.distanceSquared(positionComponentTarget1.getPosition());
                            float distanceSquaredTarget2 = relevantPosition.distanceSquared(positionComponentTarget2.getPosition());
                            return Float.compare(distanceSquaredTarget1, distanceSquaredTarget2);
                        }
                        return 0;
                    });
                }
            }
            tmpTargetEntities = targetStream.limit(maximumTargetsComponent.getMaximum()).collect(Collectors.toList());
        }
        return Util.convertToArray_Integer(tmpTargetEntities);
    }

    public static void removeTriggerOnceTrigger(EntityWorld entityWorld, int effectTriggerEntity) {
        TriggerOnceComponent triggerOnceComponent = entityWorld.getComponent(effectTriggerEntity, TriggerOnceComponent.class);
        if (triggerOnceComponent != null) {
            if (triggerOnceComponent.isRemoveEntity()) {
                entityWorld.removeEntity(effectTriggerEntity);
            } else {
                entityWorld.removeComponent(effectTriggerEntity, TriggerSourceComponent.class);
            }
        }
    }
}
