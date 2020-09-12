/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.movement;

import com.jme3.math.Vector2f;
import amara.applications.ingame.entitysystem.components.movements.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.*;
import amara.applications.ingame.entitysystem.systems.spells.casting.CastSpellSystem;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
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
                }
            }
        }
    }

    private boolean isMovementReadyToProceed(EntityWorld entityWorld, int movementEntity) {
        return entityWorld.hasAllComponents(movementEntity, MovementDirectionComponent.class, MovementSpeedComponent.class)
          && (!entityWorld.hasComponent(movementEntity, MovementTargetReachedComponent.class));
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

    private static boolean isMovementDisplacement(EntityWorld entityWorld, int movementEtity) {
        return entityWorld.hasComponent(movementEtity, DisplacementComponent.class);
    }
}
