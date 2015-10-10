/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.movement;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.movements.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class CheckDistanceLimitMovementsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(Integer entity : entityWorld.getEntitiesWithAll(MovementComponent.class)){
            int movementEntity = entityWorld.getComponent(entity, MovementComponent.class).getMovementEntity();
            DistanceLimitComponent distanceLimitComponent = entityWorld.getComponent(movementEntity, DistanceLimitComponent.class);
            if(distanceLimitComponent != null){
                MovedDistanceComponent movedDistanceComponent = entityWorld.getComponent(movementEntity, MovedDistanceComponent.class);
                if((movedDistanceComponent != null) && (movedDistanceComponent.getDistance() >= distanceLimitComponent.getDistance())){
                    entityWorld.setComponent(movementEntity, new MovementTargetReachedComponent());
                }
            }
        }
    }
}
