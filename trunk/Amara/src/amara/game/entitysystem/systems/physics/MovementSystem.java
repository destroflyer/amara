/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.units.crowdcontrol.*;
import com.jme3.math.Vector2f;

/**
 *
 * @author Philipp
 */
public class MovementSystem implements EntitySystem
{
    private final static Class[] antiMovementComponentClasses = new Class[]{IsStunnedComponent.class, IsBindedComponent.class};
    
    public void update(EntityWorld entityWorld, float deltaSeconds)
    {
        for(EntityWrapper entity: entityWorld.getWrapped(entityWorld.getCurrent().getEntitiesWithAll(MovementTargetComponent.class, PositionComponent.class)))
        {
            PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
            Vector2f moveDirection = entity.getComponent(MovementTargetComponent.class).getTargetPosition().subtract(positionComponent.getPosition()).normalizeLocal().multLocal(2.5f);
            entity.setComponent(new MovementSpeedComponent(moveDirection));
        }
        
        for(EntityWrapper entity: entityWorld.getWrapped(entityWorld.getCurrent().getEntitiesWithAll(MovementSpeedComponent.class, PositionComponent.class)))
        {
            if(entityWorld.getCurrent().hasAnyComponent(entity.getId(), antiMovementComponentClasses))
            {
                continue;
            }
            
            Vector2f position = entity.getComponent(PositionComponent.class).getPosition();
            Vector2f movement = entity.getComponent(MovementSpeedComponent.class).getSpeed().mult(deltaSeconds);
            
            Vector2f newPosition = position.add(movement);
            
            MovementTargetComponent targetComponent = entity.getComponent(MovementTargetComponent.class);
            if(targetComponent != null)
            {
                Vector2f target = targetComponent.getTargetPosition();
                if(movement.distanceSquared(0, 0) >= target.distanceSquared(position))
                {
                    newPosition = target;
                    entity.removeComponent(MovementSpeedComponent.class);
                    entity.removeComponent(MovementTargetComponent.class);
                }
            }
            entity.setComponent(new PositionComponent(newPosition));
        }
    }
    
}
