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
import amara.game.entitysystem.systems.physics.IntersectionObserver;

/**
 *
 * @author Carl
 */
public class TargetedMovementSystem implements EntitySystem{
    
    public TargetedMovementSystem(IntersectionObserver intersectionObserver){
        this.intersectionObserver = intersectionObserver;
    }
    private IntersectionObserver intersectionObserver;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        intersectionObserver.update(entityWorld);
        for(int entity : entityWorld.getEntitiesWithAll(MovementComponent.class)){
            int movementEntity = entityWorld.getComponent(entity, MovementComponent.class).getMovementEntity();
            MovementTargetComponent movementTargetComponent = entityWorld.getComponent(movementEntity, MovementTargetComponent.class);
            if(movementTargetComponent != null){
                if(checkCollision(entityWorld, entity, movementTargetComponent.getTargetEntity())){
                    entityWorld.setComponent(movementEntity, new MovementTargetReachedComponent());
                    continue;
                }
                PositionComponent targetPositionComponent = entityWorld.getComponent(movementTargetComponent.getTargetEntity(), PositionComponent.class);
                if(targetPositionComponent != null){
                    Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
                    Vector2f targetPosition = targetPositionComponent.getPosition();
                    boolean isTargetReached = position.equals(targetPosition);
                    if(!isTargetReached){
                        Vector2f distanceToTarget = targetPosition.subtract(position);
                        MovementTargetSufficientDistanceComponent movementTargetSufficientDistanceComponent = entityWorld.getComponent(movementEntity, MovementTargetSufficientDistanceComponent.class);
                        if(movementTargetSufficientDistanceComponent != null){
                            if(distanceToTarget.length() <= movementTargetSufficientDistanceComponent.getDistance()){
                                isTargetReached = true;
                            }
                        }
                        if(!isTargetReached){
                            float speed = entityWorld.getComponent(movementEntity, MovementSpeedComponent.class).getSpeed();
                            Vector2f movedDistance = distanceToTarget.normalize().multLocal(speed).multLocal(deltaSeconds);
                            if(movedDistance.lengthSquared() >= distanceToTarget.lengthSquared()){
                                entityWorld.setComponent(entity, new PositionComponent(targetPosition.clone()));
                                isTargetReached = true;
                            }
                            else{
                                entityWorld.setComponent(movementEntity, new MovementDirectionComponent(distanceToTarget));
                                entityWorld.setComponent(entity, new DirectionComponent(distanceToTarget));
                            }
                        }
                    }
                    if(isTargetReached){
                        entityWorld.setComponent(movementEntity, new MovementTargetReachedComponent());
                    }
                }
                else{
                    entityWorld.removeEntity(entity);
                }
            }
        }
    }
    
    private boolean checkCollision(EntityWorld entityWorld, int movingEntity, int targetEntity){
        HitboxComponent hitboxComponent1 = entityWorld.getComponent(movingEntity, HitboxComponent.class);
        HitboxComponent hitboxComponent2 = entityWorld.getComponent(targetEntity, HitboxComponent.class);
        return ((hitboxComponent1 != null) && (hitboxComponent2 != null) && hitboxComponent1.getShape().intersects(hitboxComponent2.getShape()));
    }
}
