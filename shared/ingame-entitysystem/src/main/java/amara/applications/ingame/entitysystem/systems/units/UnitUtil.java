package amara.applications.ingame.entitysystem.systems.units;

import amara.applications.ingame.entitysystem.components.units.AggroTargetComponent;
import amara.applications.ingame.entitysystem.components.units.CurrentCastEffectCastsComponent;
import amara.applications.ingame.entitysystem.components.units.IsCastingComponent;
import amara.applications.ingame.entitysystem.components.units.MovementComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerOnCancelComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerSourceComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerTemporaryComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.CastCancelledTriggerComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.CastingFinishedTriggerComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.CollisionTriggerComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.TargetReachedTriggerComponent;
import amara.applications.ingame.entitysystem.components.visuals.AnimationComponent;
import amara.applications.ingame.entitysystem.systems.effects.triggers.EffectTriggerUtil;
import amara.applications.ingame.entitysystem.systems.movement.MovementSystem;
import amara.libraries.entitysystem.EntityWorld;

public class UnitUtil {

    public static boolean tryCancelAction(EntityWorld entityWorld, int entity) {
        if (isActionCancelable(entityWorld, entity)) {
            cancelAction(entityWorld, entity);
            return true;
        }
        return false;
    }

    private static boolean isActionCancelable(EntityWorld entityWorld, int entity) {
        return (isMovementCancelable(entityWorld, entity) && isCastCancelable(entityWorld, entity));
    }

    public static void cancelAction(EntityWorld entityWorld, int entity) {
        cancelMovement(entityWorld, entity);
        cancelCast(entityWorld, entity);
    }

    public static boolean tryCancelMovement(EntityWorld entityWorld, int entity) {
        if (isMovementCancelable(entityWorld, entity)) {
            cancelMovement(entityWorld, entity);
            return true;
        }
        return false;
    }

    private static boolean isMovementCancelable(EntityWorld entityWorld, int entity) {
        MovementComponent movementComponent = entityWorld.getComponent(entity, MovementComponent.class);
        return ((movementComponent == null) || (!MovementSystem.isMovementUncancelable(entityWorld, movementComponent.getMovementEntity())));
    }

    public static void cancelMovement(EntityWorld entityWorld, int entity) {
        MovementComponent movementComponent = entityWorld.getComponent(entity, MovementComponent.class);
        if (movementComponent != null) {
            entityWorld.removeComponent(entity, MovementComponent.class);
            entityWorld.removeEntity(movementComponent.getMovementEntity());
            removeTemporaryTriggers(entityWorld, entity, TargetReachedTriggerComponent.class);
            removeTemporaryTriggers(entityWorld, entity, CollisionTriggerComponent.class);
        }
    }

    public static boolean tryCancelCast(EntityWorld entityWorld, int entity) {
        if (isCastCancelable(entityWorld, entity)) {
            cancelCast(entityWorld, entity);
            return true;
        }
        return false;
    }

    private static boolean isCastCancelable(EntityWorld entityWorld, int entity) {
        IsCastingComponent isCastingComponent = entityWorld.getComponent(entity, IsCastingComponent.class);
        return ((isCastingComponent == null) || isCastingComponent.isCancelable());
    }

    public static void cancelCast(EntityWorld entityWorld, int entity) {
        entityWorld.removeComponent(entity, AggroTargetComponent.class);
        entityWorld.removeComponent(entity, AnimationComponent.class);
        CurrentCastEffectCastsComponent currentCastEffectCastsComponent = entityWorld.getComponent(entity, CurrentCastEffectCastsComponent.class);
        if (currentCastEffectCastsComponent != null) {
            for (int effectCastEntity : currentCastEffectCastsComponent.getEffectCastEntities()) {
                entityWorld.removeEntity(effectCastEntity);
            }
        }
        entityWorld.removeComponent(entity, IsCastingComponent.class);
        triggerCastCancelledTriggers(entityWorld, entity);
        removeTemporaryTriggers(entityWorld, entity, CastingFinishedTriggerComponent.class);
    }

    private static void triggerCastCancelledTriggers(EntityWorld entityWorld, int entity) {
        for (int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, CastCancelledTriggerComponent.class)) {
            int triggerSourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
            if (triggerSourceEntity == entity) {
                EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, -1);
            }
        }
    }

    private static void removeTemporaryTriggers(EntityWorld entityWorld, int sourceEntity, Class triggerComponentClass) {
        for (int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, triggerComponentClass)) {
            int triggerSourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
            if (triggerSourceEntity == sourceEntity) {
                if (entityWorld.hasComponent(effectTriggerEntity, TriggerOnCancelComponent.class)) {
                    EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, -1);
                }
                if (entityWorld.hasComponent(effectTriggerEntity, TriggerTemporaryComponent.class)) {
                    entityWorld.removeEntity(effectTriggerEntity);
                } else {
                    EffectTriggerUtil.removeTriggerOnceTrigger(entityWorld, effectTriggerEntity);
                }
            }
        }
    }
}
