/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.units.animations.*;
import amara.game.entitysystem.components.units.crowdcontrol.*;
import amara.game.entitysystem.components.visuals.*;

/**
 *
 * @author Philipp
 */
public class MovementSystem implements EntitySystem
{
    private final static Class[] antiMovementComponentClasses = new Class[]{IsStunnedComponent.class, IsBindedComponent.class};
    
    public void update(EntityWorld entityWorld, float deltaSeconds)
    {
        for(EntityWrapper entity: entityWorld.getWrapped(entityWorld.getEntitiesWithAll(MovementTargetComponent.class, PositionComponent.class)))
        {
            PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
            Vector2f moveDirection = entity.getComponent(MovementTargetComponent.class).getTargetPosition().subtract(positionComponent.getPosition()).normalizeLocal().multLocal(2.5f);
            entity.setComponent(new MovementSpeedComponent(moveDirection));
        }
        
        for(EntityWrapper entity: entityWorld.getWrapped(entityWorld.getEntitiesWithAll(MovementSpeedComponent.class, PositionComponent.class)))
        {
            if(entityWorld.hasAnyComponent(entity.getId(), antiMovementComponentClasses))
            {
                continue;
            }
            
            Vector2f position = entity.getComponent(PositionComponent.class).getPosition();
            Vector2f movement = entity.getComponent(MovementSpeedComponent.class).getSpeed().mult(deltaSeconds);
            
            Vector2f newPosition = position.add(movement);
            
            boolean isTargetReached = false;
            MovementTargetComponent targetComponent = entity.getComponent(MovementTargetComponent.class);
            if(targetComponent != null)
            {
                Vector2f target = targetComponent.getTargetPosition();
                if(movement.distanceSquared(0, 0) >= target.distanceSquared(position))
                {
                    isTargetReached = true;
                    newPosition = target;
                    entity.removeComponent(MovementSpeedComponent.class);
                    entity.removeComponent(MovementTargetComponent.class);
                }
            }
            entity.setComponent(new PositionComponent(newPosition));
            entity.setComponent(new DirectionComponent(movement));
            if(isTargetReached){
                entity.setComponent(new StopPlayingAnimationComponent());
            }
            else{
                WalkAnimationComponent walkAnimationComponent = entity.getComponent(WalkAnimationComponent.class);
                if(walkAnimationComponent != null){
                    entity.setComponent(new AnimationComponent(walkAnimationComponent.getAnimationEntity()));
                }
            }
        }
    }
    
}
