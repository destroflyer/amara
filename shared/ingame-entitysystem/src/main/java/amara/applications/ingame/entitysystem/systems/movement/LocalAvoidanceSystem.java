package amara.applications.ingame.entitysystem.systems.movement;

import com.jme3.math.Vector2f;
import amara.applications.ingame.entitysystem.components.movements.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.systems.physics.intersectionHelper.IntersectionFilter;
import amara.libraries.entitysystem.*;

public class LocalAvoidanceSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(MovementComponent.class, IntersectionPushesComponent.class)){
            int movementEntity = entityWorld.getComponent(entity, MovementComponent.class).getMovementEntity();
            if(entityWorld.hasAllComponents(movementEntity, MovementLocalAvoidanceComponent.class, MovementDirectionComponent.class)){
                Vector2f direction = entityWorld.getComponent(movementEntity, MovementDirectionComponent.class).getDirection();
                Vector2f correctedDirection = correctMovementDirection(entityWorld, entity, direction, deltaSeconds);
                if(correctedDirection.lengthSquared() > 0){
                    entityWorld.setComponent(movementEntity, new MovementDirectionComponent(correctedDirection));
                }
            }
        }
    }
    
    private Vector2f correctMovementDirection(EntityWorld entityWorld, int entity, Vector2f movementDirection, float deltaSeconds){
        float hitboxRadius = TargetedMovementSystem.getHitboxRadius(entityWorld, entity);
        Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
        int movementEntity = entityWorld.getComponent(entity, MovementComponent.class).getMovementEntity();
        Vector2f movementDirectionNormalized = movementDirection.normalize();
        float movementSpeed = entityWorld.getComponent(movementEntity, MovementSpeedComponent.class).getSpeed();
        Vector2f resultingVector = new Vector2f();
        // Factor adjusted by testing, is adaptable if need arises
        float minimumDistance = (0.75f * movementSpeed);
        float squaredMinimumDistance = (minimumDistance * minimumDistance);
        for(int targetEntity : entityWorld.getEntitiesWithAll(PositionComponent.class, HitboxComponent.class, HitboxActiveComponent.class, IntersectionPushedComponent.class)){
            if((targetEntity != entity) && IntersectionFilter.areCollisionGroupsMatching(entityWorld, entity, targetEntity)){
                Vector2f targetPosition = entityWorld.getComponent(targetEntity, PositionComponent.class).getPosition();
                Vector2f distance = targetPosition.subtract(position);
                Vector2f distanceNormalized = distance.normalize();
                float distanceLengthSquared = distance.lengthSquared();
                if(distanceLengthSquared <= squaredMinimumDistance){
                    // Scalar product is positive if angle is acute, i.e. if the target is "in the way" of the moving entity
                    float angle = movementDirectionNormalized.dot(distanceNormalized);
                    if(angle > 0){
                        float targetHitboxRadius = TargetedMovementSystem.getHitboxRadius(entityWorld, targetEntity);
                        // Factor adjusted by testing, is adaptable if need arises
                        float scale = (0.5f * ((minimumDistance - distance.length() + targetHitboxRadius + hitboxRadius) / minimumDistance));
                        Vector2f orthogonalAvoidance = new Vector2f(-1 * distanceNormalized.getY(), distanceNormalized.getX()).multLocal(scale);
                        Vector2f avoidanceVector = movementDirectionNormalized.add(orthogonalAvoidance);
                        resultingVector.addLocal(avoidanceVector);
                    }
                }
            }
        }
        resultingVector.normalizeLocal();
        return resultingVector;
    }
}
