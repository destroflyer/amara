/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.movement;

import java.util.Set;
import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.movements.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.units.*;
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
        Set<Pair<Integer>> collidingEntities = intersectionInformant.getEntries();
        collidingEntities.addAll(intersectionInformant.getRepeaters());
        for(Pair<Integer> pair : collidingEntities){
            if(entityWorld.hasComponent(pair.getA(), MovementComponent.class)){
                checkCollidingStop(entityWorld, pair.getA(), pair.getB());
            }
            if(entityWorld.hasComponent(pair.getB(), MovementComponent.class)){
                checkCollidingStop(entityWorld, pair.getB(), pair.getA());
            }
        }
        for(int entity : entityWorld.getEntitiesWithAll(MovementComponent.class)){
            int movementEntity = entityWorld.getComponent(entity, MovementComponent.class).getMovementEntity();
            MovementTargetComponent movementTargetComponent = entityWorld.getComponent(movementEntity, MovementTargetComponent.class);
            if(movementTargetComponent != null){
                PositionComponent targetPositionComponent = entityWorld.getComponent(movementTargetComponent.getTargetEntity(), PositionComponent.class);
                if(targetPositionComponent != null){
                    Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
                    Vector2f targetPosition = targetPositionComponent.getPosition();
                    boolean isTargetReached = position.equals(targetPosition);
                    if(!isTargetReached){
                        Vector2f distanceToTarget = targetPosition.subtract(position);
                        float speed = entityWorld.getComponent(movementEntity, MovementSpeedComponent.class).getSpeed();
                        Vector2f movedDistance = distanceToTarget.normalize().multLocal(speed).multLocal(deltaSeconds);
                        isTargetReached = (movedDistance.lengthSquared() >= distanceToTarget.lengthSquared());
                        if(!isTargetReached){
                            entityWorld.setComponent(movementEntity, new MovementDirectionComponent(distanceToTarget));
                            entityWorld.setComponent(entity, new DirectionComponent(distanceToTarget));
                        }
                    }
                    if(isTargetReached){
                        entityWorld.setComponent(entity, new PositionComponent(targetPosition.clone()));
                    }
                }
                else{
                    entityWorld.removeEntity(entity);
                }
            }
        }
    }
    
    private void checkCollidingStop(EntityWorld entityWorld, int movingEntity, int targetEntity){
        int movementEntity = entityWorld.getComponent(movingEntity, MovementComponent.class).getMovementEntity();
        MovementTargetComponent movementTargetComponent = entityWorld.getComponent(movementEntity, MovementTargetComponent.class);
        if((movementTargetComponent != null) && (movementTargetComponent.getTargetEntity() == targetEntity)){
            entityWorld.removeComponent(movingEntity, MovementComponent.class);
        }
    }
}
