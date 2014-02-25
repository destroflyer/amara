/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.movement;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.PositionComponent;
import amara.game.entitysystem.components.units.movement.TargetedMovementComponent;
import amara.game.entitysystem.systems.physics.intersection.Pair;
import amara.game.entitysystem.systems.physics.intersectionHelper.IntersectionInformant;

/**
 *
 * @author Carl
 */
public class TargetedMovementSystem implements EntitySystem{
    
    public TargetedMovementSystem(IntersectionInformant intersectionInformant){
        this.intersectionInformant = intersectionInformant;
    }
    private IntersectionInformant intersectionInformant;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(Pair<Integer> pair: intersectionInformant.getEntries()){
            if(entityWorld.hasComponent(pair.getA(), TargetedMovementComponent.class)){
                checkCollidingStop(entityWorld, pair.getA(), pair.getB());
            }
            if(entityWorld.hasComponent(pair.getB(), TargetedMovementComponent.class)){
                checkCollidingStop(entityWorld, pair.getB(), pair.getA());
            }
        }
        for(int entity : entityWorld.getEntitiesWithAll(TargetedMovementComponent.class)){
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
    
    private void checkCollidingStop(EntityWorld entityWorld, int movingEntity, int targetEntity){
        TargetedMovementComponent targetedMovementComponent = entityWorld.getComponent(movingEntity, TargetedMovementComponent.class);
        if(targetedMovementComponent.getTargetEntityID() == targetEntity){
            entityWorld.removeComponent(movingEntity, TargetedMovementComponent.class);
        }
    }
}
