/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.movement;

import com.jme3.math.Vector2f;
import amara.applications.ingame.entitysystem.components.movements.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.*;
import amara.applications.ingame.entitysystem.systems.spells.casting.CastSpellSystem;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class MovementSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(MovementComponent.class)){
            if(canMove(entityWorld, entity) || isDisplaced(entityWorld, entity)){
                int movementEntity = entityWorld.getComponent(entity, MovementComponent.class).getMovementEntity();
                if(entityWorld.hasAllComponents(movementEntity, MovementDirectionComponent.class, MovementSpeedComponent.class)){
                    Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
                    Vector2f direction = entityWorld.getComponent(movementEntity, MovementDirectionComponent.class).getDirection();
                    float speed = entityWorld.getComponent(movementEntity, MovementSpeedComponent.class).getSpeed();
                    Vector2f movedDistance = direction.normalize().multLocal(speed * deltaSeconds);
                    entityWorld.setComponent(entity, new PositionComponent(position.add(movedDistance)));
                    float totalMovedDistance = movedDistance.length();
                    MovedDistanceComponent movedDistanceComponent = entityWorld.getComponent(movementEntity, MovedDistanceComponent.class);
                    if(movedDistanceComponent != null){
                        totalMovedDistance += movedDistanceComponent.getDistance();
                    }
                    entityWorld.setComponent(movementEntity, new MovedDistanceComponent(totalMovedDistance));
                }
            }
        }
    }
    
    public static boolean canMove(EntityWorld entityWorld, int entity){
        return (CastSpellSystem.isAbleToPerformAction(entityWorld, entity) && (!entityWorld.hasComponent(entity, IsBindedComponent.class)));
    }
    
    public static boolean hasUncancelableMovement(EntityWorld entityWorld, int entity){
        MovementComponent movementComponent = entityWorld.getComponent(entity, MovementComponent.class);
        if(movementComponent != null){
            return ((!entityWorld.hasComponent(movementComponent.getMovementEntity(), MovementIsCancelableComponent.class)) || isDisplaced(entityWorld, entity));
        }
        return false;
    }
    
    public static boolean isDisplaced(EntityWorld entityWorld, int entity){
        MovementComponent movementComponent = entityWorld.getComponent(entity, MovementComponent.class);
        if(movementComponent != null){
            return entityWorld.hasComponent(movementComponent.getMovementEntity(), DisplacementComponent.class);
        }
        return false;
    }
}
