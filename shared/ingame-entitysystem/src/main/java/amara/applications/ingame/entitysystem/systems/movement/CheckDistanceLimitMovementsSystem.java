/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.movement;

import amara.applications.ingame.entitysystem.components.movements.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CheckDistanceLimitMovementsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int entity : entityWorld.getEntitiesWithAny(MovementComponent.class)) {
            int movementEntity = entityWorld.getComponent(entity, MovementComponent.class).getMovementEntity();
            DistanceLimitComponent distanceLimitComponent = entityWorld.getComponent(movementEntity, DistanceLimitComponent.class);
            if (distanceLimitComponent != null) {
                MovedDistanceComponent movedDistanceComponent = entityWorld.getComponent(movementEntity, MovedDistanceComponent.class);
                if ((movedDistanceComponent != null) && (movedDistanceComponent.getDistance() >= distanceLimitComponent.getDistance())) {
                    entityWorld.setComponent(movementEntity, new MovementTargetReachedComponent());
                }
            }
        }
    }
}
