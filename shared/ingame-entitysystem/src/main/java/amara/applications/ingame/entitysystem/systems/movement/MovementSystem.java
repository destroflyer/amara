package amara.applications.ingame.entitysystem.systems.movement;

import amara.applications.ingame.entitysystem.components.movements.*;
import amara.applications.ingame.entitysystem.components.physics.PositionComponent;
import amara.applications.ingame.entitysystem.components.spells.RemainingCooldownComponent;
import amara.applications.ingame.entitysystem.components.units.MovementComponent;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsBindedComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerSourceComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.MovementTriggerComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.MovementTriggerMovedDistanceComponent;
import amara.applications.ingame.entitysystem.systems.effects.triggers.EffectTriggerUtil;
import amara.applications.ingame.entitysystem.systems.spells.casting.CastSpellSystem;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.math.Vector2f;

public class MovementSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int entity : entityWorld.getEntitiesWithAny(MovementComponent.class)) {
            if (canMove(entityWorld, entity) || isDisplaced(entityWorld, entity)) {
                int movementEntity = entityWorld.getComponent(entity, MovementComponent.class).getMovementEntity();
                if (isMovementReadyToProceed(entityWorld, movementEntity)) {
                    Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
                    Vector2f direction = entityWorld.getComponent(movementEntity, MovementDirectionComponent.class).getDirection();
                    float speed = entityWorld.getComponent(movementEntity, MovementSpeedComponent.class).getSpeed();
                    float movedDistance = (speed * deltaSeconds);
                    MovedDistanceComponent movedDistanceComponent = entityWorld.getComponent(movementEntity, MovedDistanceComponent.class);
                    float totalMovedDistance = (((movedDistanceComponent != null) ? movedDistanceComponent.getDistance() : 0) + movedDistance);
                    DistanceLimitComponent distanceLimitComponent = entityWorld.getComponent(movementEntity, DistanceLimitComponent.class);
                    if ((distanceLimitComponent != null) && (totalMovedDistance > distanceLimitComponent.getDistance())) {
                        movedDistance -= (totalMovedDistance - distanceLimitComponent.getDistance());
                    }
                    Vector2f newPosition = position.add(direction.normalize().multLocal(movedDistance));
                    entityWorld.setComponent(entity, new PositionComponent(newPosition));
                    entityWorld.setComponent(movementEntity, new MovedDistanceComponent(totalMovedDistance));
                    updateMovementTriggers(entityWorld, entity, movedDistance);
                }
            }
        }
    }

    private boolean isMovementReadyToProceed(EntityWorld entityWorld, int movementEntity) {
        return entityWorld.hasAllComponents(movementEntity, MovementDirectionComponent.class, MovementSpeedComponent.class)
          && (!entityWorld.hasComponent(movementEntity, MovementTargetReachedComponent.class));
    }

    private void updateMovementTriggers(EntityWorld entityWorld, int entity, float movedDistance) {
        for (int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, MovementTriggerComponent.class)) {
            int sourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
            if (sourceEntity == entity) {
                MovementTriggerMovedDistanceComponent movementTriggerMovedDistanceComponent = entityWorld.getComponent(effectTriggerEntity, MovementTriggerMovedDistanceComponent.class);
                float movedTriggerDistance = ((movementTriggerMovedDistanceComponent != null) ? movementTriggerMovedDistanceComponent.getDistance() : 0);
                movedTriggerDistance += movedDistance;

                float activateTriggerDistance = entityWorld.getComponent(effectTriggerEntity, MovementTriggerComponent.class).getDistance();
                if (movedTriggerDistance < activateTriggerDistance) {
                    entityWorld.setComponent(effectTriggerEntity, new MovementTriggerMovedDistanceComponent(movedTriggerDistance));
                } else {
                    entityWorld.removeComponent(effectTriggerEntity, MovementTriggerMovedDistanceComponent.class);
                    if (!entityWorld.hasComponent(effectTriggerEntity, RemainingCooldownComponent.class)) {
                        EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, -1);
                    }
                }
            }
        }
    }

    public static boolean canMove(EntityWorld entityWorld, int entity) {
        return (CastSpellSystem.isAbleToPerformAction(entityWorld, entity) && (!entityWorld.hasComponent(entity, IsBindedComponent.class)));
    }

    public static boolean isMovementUncancelable(EntityWorld entityWorld, int movementEntity) {
        return ((!entityWorld.hasComponent(movementEntity, MovementIsCancelableComponent.class)) || isMovementDisplacement(entityWorld, movementEntity));
    }

    public static boolean isDisplaced(EntityWorld entityWorld, int entity) {
        MovementComponent movementComponent = entityWorld.getComponent(entity, MovementComponent.class);
        if (movementComponent != null) {
            return isMovementDisplacement(entityWorld, movementComponent.getMovementEntity());
        }
        return false;
    }

    private static boolean isMovementDisplacement(EntityWorld entityWorld, int movementEntity) {
        return entityWorld.hasComponent(movementEntity, DisplacementComponent.class);
    }
}
