package amara.applications.ingame.entitysystem.systems.spells.casting;

import java.util.HashMap;
import java.util.LinkedList;

import amara.applications.ingame.entitysystem.components.effects.spells.*;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.input.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.applications.ingame.entitysystem.systems.commands.ExecutePlayerCommandsSystem;
import amara.applications.ingame.entitysystem.systems.targets.TargetUtil;
import amara.applications.ingame.entitysystem.systems.units.UnitUtil;
import amara.core.Util;
import amara.libraries.entitysystem.*;
import com.jme3.math.Vector2f;

public class CastSpellQueueSystem implements EntitySystem {

    private HashMap<Integer, LinkedList<int[]>> entitiesCastQueues = new HashMap<>();

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int casterEntity : Util.toArray(entitiesCastQueues.keySet(), Integer.class)) {
            LinkedList<int[]> castSpellQueue = entitiesCastQueues.get(casterEntity);
            int[] castEntities = castSpellQueue.removeFirst();
            int spellEntity = castEntities[0];
            int targetEntity = castEntities[1];
            tryCastSpell(entityWorld, casterEntity, spellEntity, targetEntity);
            if (castSpellQueue.isEmpty()) {
                entitiesCastQueues.remove(casterEntity);
            }
        }
    }

    public void enqueueSpellCast(int casterEntity, int spellEntity, int targetEntity) {
        LinkedList<int[]> castSpellQueue = entitiesCastQueues.computeIfAbsent(casterEntity, ce -> new LinkedList<>());
        castSpellQueue.add(new int[]{spellEntity, targetEntity});
    }

    private static void tryCastSpell(EntityWorld entityWorld, int casterEntity, int spellEntity, int targetEntity) {
        boolean isCasted = false;
        if(!entityWorld.hasComponent(spellEntity, RemainingCooldownComponent.class)){
            if(CastSpellSystem.canCast(entityWorld, casterEntity, spellEntity)){
                isCasted = true;
                if (targetEntity != -1) {
                    SpellTargetRulesComponent spellTargetRulesComponent = entityWorld.getComponent(spellEntity, SpellTargetRulesComponent.class);
                    if (spellTargetRulesComponent != null) {
                        isCasted = TargetUtil.isValidTarget(entityWorld, casterEntity, targetEntity, spellTargetRulesComponent.getTargetRulesEntity());
                    }
                }
                if (isCasted) {
                    isCasted = addSpellCastComponents(entityWorld, casterEntity, spellEntity, targetEntity);
                }
            }
        }
        if ((!isCasted) && entityWorld.hasComponent(targetEntity, TemporaryComponent.class)) {
            entityWorld.removeEntity(targetEntity);
        }
    }

    private static boolean addSpellCastComponents(EntityWorld entityWorld, int casterEntity, int spellEntity, int targetEntity) {
        boolean isCasted = false;
        AutoAttackComponent autoAttackComponent = entityWorld.getComponent(casterEntity, AutoAttackComponent.class);
        boolean isAutoAttack = ((autoAttackComponent != null) && (spellEntity == autoAttackComponent.getAutoAttackEntity()));
        boolean castInstant = true;
        if (entityWorld.hasComponent(spellEntity, RangeComponent.class)) {
            float minimumCastRange = CastSpellSystem.getMinimumCastRange(entityWorld, casterEntity, spellEntity, targetEntity);
            Vector2f casterPosition = entityWorld.getComponent(casterEntity, PositionComponent.class).getPosition();
            Vector2f targetPosition = entityWorld.getComponent(targetEntity, PositionComponent.class).getPosition();
            float distanceSquared = targetPosition.distanceSquared(casterPosition);
            if (distanceSquared > (minimumCastRange * minimumCastRange)) {
                int walkTargetEntity;
                // Clone temporary target to prevent the original from getting cleanuped
                if (entityWorld.hasComponent(targetEntity, TemporaryComponent.class)) {
                    walkTargetEntity = entityWorld.createEntity();
                    entityWorld.setComponent(walkTargetEntity, new TemporaryComponent());
                    entityWorld.setComponent(walkTargetEntity, new PositionComponent(targetPosition.clone()));
                } else {
                    walkTargetEntity = targetEntity;
                }
                if (ExecutePlayerCommandsSystem.tryWalk(entityWorld, casterEntity, walkTargetEntity, minimumCastRange)) {
                    // Cast Spell
                    EntityWrapper effectTrigger = entityWorld.getWrapped(entityWorld.createEntity());
                    effectTrigger.setComponent(new TriggerTemporaryComponent());
                    effectTrigger.setComponent(new TargetReachedTriggerComponent());
                    effectTrigger.setComponent(new SourceTargetComponent());
                    EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
                    effect.setComponent(new EnqueueSpellCastComponent(spellEntity, targetEntity));
                    effectTrigger.setComponent(new TriggeredEffectComponent(effect.getId()));
                    effectTrigger.setComponent(new TriggerSourceComponent(casterEntity));
                    if (isAutoAttack) {
                        entityWorld.setComponent(casterEntity, new AggroTargetComponent(targetEntity));
                    }
                    isCasted = true;
                }
                castInstant = false;
            }
        }
        if (castInstant) {
            boolean isAllowed = true;
            if (entityWorld.hasComponent(spellEntity, CastCancelActionComponent.class)) {
                isAllowed = UnitUtil.tryCancelAction(entityWorld, casterEntity);
            }
            if (isAllowed) {
                if (isAutoAttack) {
                    entityWorld.setComponent(casterEntity, new AggroTargetComponent(targetEntity));
                }
                entityWorld.setComponent(casterEntity, new CastSpellComponent(spellEntity, targetEntity));
                isCasted = true;
            }
        }
        return isCasted;
    }
}
