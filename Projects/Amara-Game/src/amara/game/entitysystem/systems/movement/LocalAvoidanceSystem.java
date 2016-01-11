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
public class LocalAvoidanceSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(MovementComponent.class)){
            int movementEntity = entityWorld.getComponent(entity, MovementComponent.class).getMovementEntity();
            if(entityWorld.hasAllComponents(movementEntity, MovementLocalAvoidanceComponent.class, MovementDirectionComponent.class)){
                Vector2f direction = entityWorld.getComponent(movementEntity, MovementDirectionComponent.class).getDirection();
                Vector2f correctedDirection = correctMovementDirection(entityWorld, entity, direction, deltaSeconds);
                if(correctedDirection.lengthSquared() > 0){
                    entityWorld.setComponent(movementEntity, new MovementDirectionComponent(correctedDirection));
                    entityWorld.setComponent(entity, new DirectionComponent(correctedDirection));
                }
            }
        }
    }
    
    public static Vector2f correctMovementDirection(EntityWorld entityWorld, int entity, Vector2f movementDirection, float deltaSeconds){
        float hitboxRadius = TargetedMovementSystem.getHitboxRadius(entityWorld, entity);
        Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
        int movementEntity = entityWorld.getComponent(entity, MovementComponent.class).getMovementEntity();
        Vector2f movementDirectionNormalized = movementDirection.normalize();
        float movementSpeed = entityWorld.getComponent(movementEntity, MovementSpeedComponent.class).getSpeed();
        float movementSpeedPerFrame = (movementSpeed * deltaSeconds);
        Vector2f movementPerFrame = movementDirectionNormalized.mult(movementSpeedPerFrame);
        Vector2f resultingVector = new Vector2f();
        float minimumDistance = (1.25f * movementSpeed);
        float squaredMinimumDistance = (minimumDistance * minimumDistance);
        for(int targetEntity : entityWorld.getEntitiesWithAll(PositionComponent.class, HitboxComponent.class, HitboxActiveComponent.class)){
            if(targetEntity != entity){
                Vector2f targetPosition = entityWorld.getComponent(targetEntity, PositionComponent.class).getPosition();
                Vector2f distance = targetPosition.subtract(position);
                Vector2f distanceNormalized = distance.normalize();
                float distanceLengthSquared = distance.lengthSquared();
                if(distanceLengthSquared <= squaredMinimumDistance){
                    float angle = movementDirectionNormalized.dot(distanceNormalized);
                    if(angle > 0){
                        float targetHitboxRadius = TargetedMovementSystem.getHitboxRadius(entityWorld, targetEntity);
                        float scale = ((minimumDistance - distance.length() + targetHitboxRadius + hitboxRadius) / minimumDistance);
                        scale *= angle;
                        Vector2f avoidanceVector = distanceNormalized.mult(-1 * movementSpeedPerFrame * scale);
                        avoidanceVector.addLocal(movementPerFrame);
                        resultingVector.addLocal(avoidanceVector);
                    }
                }
            }
        }
        resultingVector.normalizeLocal();
        return resultingVector;
    }
}