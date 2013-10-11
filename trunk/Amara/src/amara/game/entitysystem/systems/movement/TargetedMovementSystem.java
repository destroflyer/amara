/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.movement;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.PositionComponent;
import amara.game.entitysystem.components.units.movement.TargetedMovementComponent;

/**
 *
 * @author Carl
 */
public class TargetedMovementSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(TargetedMovementComponent.class))
        {
            TargetedMovementComponent targetedMovementComponent = entityWorld.getComponent(entity, TargetedMovementComponent.class);
            PositionComponent targetPositionComponent = entityWorld.getComponent(targetedMovementComponent.getTargetEntityID(), PositionComponent.class);
            if(targetPositionComponent != null){
                Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
                Vector2f targetPosition = targetPositionComponent.getPosition();
                if(!position.equals(targetPosition)){
                    Vector2f distanceToTarget = targetPosition.subtract(position);
                    Vector2f movedDistance = distanceToTarget.normalize().multLocal(targetedMovementComponent.getSpeed()).multLocal(deltaSeconds);
                    if(movedDistance.lengthSquared() < distanceToTarget.lengthSquared()){
                        entityWorld.setComponent(entity, new PositionComponent(position.add(movedDistance)));
                    }
                    else{
                        entityWorld.setComponent(entity, new PositionComponent(targetPosition.clone()));
                    }
                }
            }
            else{
                entityWorld.removeEntity(entity);
            }
        }
    }
}
