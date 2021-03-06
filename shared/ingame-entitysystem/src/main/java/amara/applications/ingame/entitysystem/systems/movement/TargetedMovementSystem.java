/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.movement;

import amara.applications.ingame.entitysystem.components.movements.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.types.*;
import amara.applications.ingame.entitysystem.systems.physics.IntersectionObserver;
import amara.applications.ingame.entitysystem.systems.units.UnitUtil;
import amara.libraries.entitysystem.*;
import amara.libraries.physics.intersectionHelper.PolyMapManager;
import amara.libraries.physics.shapes.*;
import com.jme3.math.Vector2f;

/**
 *
 * @author Carl
 */
public class TargetedMovementSystem implements EntitySystem{
    
    public TargetedMovementSystem(IntersectionObserver intersectionObserver, PolyMapManager polyMapManager){
        this.intersectionObserver = intersectionObserver;
        this.polyMapManager = polyMapManager;
    }
    private IntersectionObserver intersectionObserver;
    private PolyMapManager polyMapManager;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        intersectionObserver.updateHitboxes(entityWorld);
        for (int entity : entityWorld.getEntitiesWithAny(MovementComponent.class)) {
            int movementEntity = entityWorld.getComponent(entity, MovementComponent.class).getMovementEntity();
            MovementTargetComponent movementTargetComponent = entityWorld.getComponent(movementEntity, MovementTargetComponent.class);
            if (movementTargetComponent != null) {
                if (checkCollision(entityWorld, entity, movementTargetComponent.getTargetEntity())) {
                    entityWorld.setComponent(movementEntity, new MovementTargetReachedComponent());
                    continue;
                }
                PositionComponent targetPositionComponent = entityWorld.getComponent(movementTargetComponent.getTargetEntity(), PositionComponent.class);
                if (targetPositionComponent != null) {
                    Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
                    Vector2f targetPosition = targetPositionComponent.getPosition();
                    boolean isTargetReached = position.equals(targetPosition);
                    if (!isTargetReached) {
                        Vector2f distanceToTarget = targetPosition.subtract(position);
                        float distanceToTargetLengthSquared = distanceToTarget.lengthSquared();
                        MovementTargetSufficientDistanceComponent movementTargetSufficientDistanceComponent = entityWorld.getComponent(movementEntity, MovementTargetSufficientDistanceComponent.class);
                        if (movementTargetSufficientDistanceComponent != null){
                            if (distanceToTargetLengthSquared <= (movementTargetSufficientDistanceComponent.getDistance() * movementTargetSufficientDistanceComponent.getDistance())) {
                                isTargetReached = true;
                            }
                        }
                        if (!isTargetReached) {
                            float speed = entityWorld.getComponent(movementEntity, MovementSpeedComponent.class).getSpeed();
                            Vector2f movedDistance = null;
                            if (entityWorld.hasComponent(movementEntity, MovementPathfindingComponent.class)) {
                                Vector2D pathfindingFrom = new Vector2D(position.getX(), position.getY());
                                Vector2D pathfindingTo = new Vector2D(targetPosition.getX(), targetPosition.getY());
                                double hitboxRadius = getHitboxRadius(entityWorld, entity);
                                Vector2D newPosition = polyMapManager.followTriPath(entity, pathfindingFrom, pathfindingTo, speed * deltaSeconds, hitboxRadius);
                                if (polyMapManager.isFollowingTriPath(entity)) {
                                    movedDistance = new Vector2f((float) newPosition.getX(), (float) newPosition.getY()).subtractLocal(position);
                                } else {
                                    entityWorld.setComponent(entity, new PositionComponent(targetPosition.clone()));
                                    isTargetReached = true;
                                }
                            } else {
                                movedDistance = distanceToTarget.normalize().multLocal(speed * deltaSeconds);
                            }
                            if (movedDistance != null) {
                                if (movedDistance.lengthSquared() >= distanceToTargetLengthSquared) {
                                    entityWorld.setComponent(entity, new PositionComponent(targetPosition.clone()));
                                    isTargetReached = true;
                                } else {
                                    entityWorld.setComponent(movementEntity, new MovementDirectionComponent(movedDistance));
                                }
                            }
                        }
                    }
                    if (isTargetReached) {
                        entityWorld.setComponent(movementEntity, new MovementTargetReachedComponent());
                    }
                } else if (entityWorld.hasComponent(entity, IsProjectileComponent.class)) {
                    entityWorld.removeEntity(entity);
                } else {
                    UnitUtil.cancelMovement(entityWorld, entity);
                }
            }
        }
    }

    private boolean checkCollision(EntityWorld entityWorld, int movingEntity, int targetEntity){
        HitboxComponent hitboxComponent1 = entityWorld.getComponent(movingEntity, HitboxComponent.class);
        HitboxComponent hitboxComponent2 = entityWorld.getComponent(targetEntity, HitboxComponent.class);
        return ((hitboxComponent1 != null) && (hitboxComponent2 != null) && hitboxComponent1.getShape().intersects(hitboxComponent2.getShape()));
    }
    
    public static float getHitboxRadius(EntityWorld entityWorld, int entity){
        float hitboxRadius = 0;
        HitboxComponent hitboxComponent = entityWorld.getComponent(entity, HitboxComponent.class);
        if(hitboxComponent != null){
            if(hitboxComponent.getShape() instanceof ConvexShape){
                hitboxRadius = (float) ((ConvexShape) hitboxComponent.getShape()).getBoundCircle().getGlobalRadius();
            }
        }
        return hitboxRadius;
    }
}
