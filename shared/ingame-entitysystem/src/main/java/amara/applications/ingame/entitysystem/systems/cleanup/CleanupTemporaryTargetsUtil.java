package amara.applications.ingame.entitysystem.systems.cleanup;

import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.casts.EffectCastTargetComponent;
import amara.applications.ingame.entitysystem.components.effects.movement.TeleportComponent;
import amara.applications.ingame.entitysystem.components.general.TemporaryComponent;
import amara.applications.ingame.entitysystem.components.movements.MovementTargetComponent;
import amara.applications.ingame.entitysystem.components.units.AttackMoveComponent;
import amara.libraries.entitysystem.EntityWorld;

public class CleanupTemporaryTargetsUtil {

    public static void tryRemoveTemporaryTargetEntity(EntityWorld entityWorld, int targetEntity) {
        if (entityWorld.hasComponent(targetEntity, TemporaryComponent.class) && isUnreferencedTemporaryTarget(entityWorld, targetEntity)) {
            entityWorld.removeEntity(targetEntity);
        }
    }

    private static boolean isUnreferencedTemporaryTarget(EntityWorld entityWorld, int temporaryTargetEntity) {
        // Effect casts
        for (int effectCastEntity : entityWorld.getEntitiesWithAny(EffectCastTargetComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectCastEntity, EffectCastTargetComponent.class).getTargetEntity();
            if (targetEntity == temporaryTargetEntity) {
                return false;
            }
        }
        // Effect impacts
        for (int effectCastEntity : entityWorld.getEntitiesWithAny(ApplyEffectImpactComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectCastEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            if (targetEntity == temporaryTargetEntity) {
                return false;
            }
        }
        // Movements
        for (int movementEntity : entityWorld.getEntitiesWithAny(MovementTargetComponent.class)) {
            int targetEntity = entityWorld.getComponent(movementEntity, MovementTargetComponent.class).getTargetEntity();
            if (targetEntity == temporaryTargetEntity) {
                return false;
            }
        }
        // AttackMoves
        for (int movementEntity : entityWorld.getEntitiesWithAny(AttackMoveComponent.class)) {
            int targetEntity = entityWorld.getComponent(movementEntity, AttackMoveComponent.class).getTargetEntity();
            if (targetEntity == temporaryTargetEntity) {
                return false;
            }
        }
        // Teleports
        for (int movementEntity : entityWorld.getEntitiesWithAny(TeleportComponent.class)) {
            int targetEntity = entityWorld.getComponent(movementEntity, TeleportComponent.class).getTargetEntity();
            if (targetEntity == temporaryTargetEntity) {
                return false;
            }
        }
        return true;
    }
}
