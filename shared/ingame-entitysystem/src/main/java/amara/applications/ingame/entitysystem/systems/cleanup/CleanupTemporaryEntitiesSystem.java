package amara.applications.ingame.entitysystem.systems.cleanup;

import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.casts.EffectCastTargetComponent;
import amara.applications.ingame.entitysystem.components.effects.movement.TeleportComponent;
import amara.applications.ingame.entitysystem.components.effects.spells.EnqueueSpellCastComponent;
import amara.applications.ingame.entitysystem.components.general.TemporaryComponent;
import amara.applications.ingame.entitysystem.components.movements.MovementTargetComponent;
import amara.applications.ingame.entitysystem.components.units.AttackMoveComponent;
import amara.applications.ingame.entitysystem.components.units.SpellCastQueueComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class CleanupTemporaryEntitiesSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int temporaryEntity : entityWorld.getEntitiesWithAny(TemporaryComponent.class)) {
            if (isUnreferencedTemporaryEntity(entityWorld, temporaryEntity)){
                entityWorld.removeEntity(temporaryEntity);
            }
        }
    }

    private static boolean isUnreferencedTemporaryEntity(EntityWorld entityWorld, int temporaryEntity) {
        // Effect cast targets
        for (int effectCastEntity : entityWorld.getEntitiesWithAny(EffectCastTargetComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectCastEntity, EffectCastTargetComponent.class).getTargetEntity();
            if (targetEntity == temporaryEntity) {
                return false;
            }
        }
        // Effect impact targets
        for (int effectCastEntity : entityWorld.getEntitiesWithAny(ApplyEffectImpactComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectCastEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            if (targetEntity == temporaryEntity) {
                return false;
            }
        }
        // Movements
        for (int movementEntity : entityWorld.getEntitiesWithAny(MovementTargetComponent.class)) {
            int targetEntity = entityWorld.getComponent(movementEntity, MovementTargetComponent.class).getTargetEntity();
            if (targetEntity == temporaryEntity) {
                return false;
            }
        }
        // AttackMoves
        for (int entity : entityWorld.getEntitiesWithAny(AttackMoveComponent.class)) {
            int targetEntity = entityWorld.getComponent(entity, AttackMoveComponent.class).getTargetEntity();
            if (targetEntity == temporaryEntity) {
                return false;
            }
        }
        // Teleports
        for (int entity : entityWorld.getEntitiesWithAny(TeleportComponent.class)) {
            int targetEntity = entityWorld.getComponent(entity, TeleportComponent.class).getTargetEntity();
            if (targetEntity == temporaryEntity) {
                return false;
            }
        }
        // Enqueue spell casts
        for (int entity : entityWorld.getEntitiesWithAny(EnqueueSpellCastComponent.class)) {
            int targetEntity = entityWorld.getComponent(entity, EnqueueSpellCastComponent.class).getTargetEntity();
            if (targetEntity == temporaryEntity) {
                return false;
            }
        }
        // Spell casts queues
        for (int effectCastEntity : entityWorld.getEntitiesWithAny(SpellCastQueueComponent.class)) {
            for (SpellCastQueueComponent.SpellCastQueueEntry entry : entityWorld.getComponent(effectCastEntity, SpellCastQueueComponent.class).getEntries()) {
                if (entry.getTargetEntity() == temporaryEntity) {
                    return false;
                }
            }
        }
        return true;
    }
}
