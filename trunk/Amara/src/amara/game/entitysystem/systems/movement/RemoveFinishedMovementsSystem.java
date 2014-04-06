/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.movement;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.movements.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class RemoveFinishedMovementsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(MovementComponent.class)){
            int movementEntity = entityWorld.getComponent(entity, MovementComponent.class).getMovementEntity();
            boolean isFinished = false;
            MovementTargetComponent movementTargetComponent = entityWorld.getComponent(movementEntity, MovementTargetComponent.class);
            if(movementTargetComponent != null){
                Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
                Vector2f targetPosition = entityWorld.getComponent(movementTargetComponent.getTargetEntity(), PositionComponent.class).getPosition();
                isFinished |= position.equals(targetPosition);
            }
            DistanceLimitComponent distanceLimitComponent = entityWorld.getComponent(movementEntity, DistanceLimitComponent.class);
            if(distanceLimitComponent != null){
                MovedDistanceComponent movedDistanceComponent = entityWorld.getComponent(movementEntity, MovedDistanceComponent.class);
                if((movedDistanceComponent != null) && (movedDistanceComponent.getDistance() >= distanceLimitComponent.getDistance())){
                    isFinished = true;
                }
            }
            if(isFinished){
                entityWorld.removeComponent(entity, MovementComponent.class);
            }
        }
    }
}
