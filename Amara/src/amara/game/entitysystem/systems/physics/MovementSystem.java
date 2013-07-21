/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import com.jme3.math.Vector2f;

/**
 *
 * @author Philipp
 */
public class MovementSystem implements EntitySystem
{

    public void update(EntityWorld entityWorld, float deltaSeconds)
    {
        for(EntityWrapper entity: entityWorld.getWrapped(entityWorld.getCurrent().getEntitiesWithAll(MovementSpeedComponent.class, PositionComponent.class)))
        {
            Vector2f position = entity.getComponent(PositionComponent.class).getPosition();
            position.addLocal(entity.getComponent(MovementSpeedComponent.class).getSpeed().mult(deltaSeconds));
            entity.setComponent(new PositionComponent(position));
        }
    }
    
}
