/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.movement;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.components.movements.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.crowdcontrol.*;
import amara.game.entitysystem.systems.spells.casting.CastSpellSystem;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class MovementSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(MovementComponent.class)){
            if(isDisplaced(entityWorld, entity) || canMove(entityWorld, entity)){
                int movementEntity = entityWorld.getComponent(entity, MovementComponent.class).getMovementEntity();
                if(entityWorld.hasAllComponents(movementEntity, MovementDirectionComponent.class, MovementSpeedComponent.class)){
                    Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
                    Vector2f direction = entityWorld.getComponent(movementEntity, MovementDirectionComponent.class).getDirection();
                    float speed = entityWorld.getComponent(movementEntity, MovementSpeedComponent.class).getSpeed();
                    Vector2f movedDistance = direction.normalize().multLocal(speed).multLocal(deltaSeconds);
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
    
    public static boolean isDisplaced(EntityWorld entityWorld, int entity){
        MovementComponent movementComponent = entityWorld.getComponent(entity, MovementComponent.class);
        if(movementComponent != null){
            return entityWorld.hasComponent(movementComponent.getMovementEntity(), DisplacementComponent.class);
        }
        return false;
    }
}
