/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics;

import amara.game.entitysystem.EntityMap;
import amara.game.entitysystem.EntitySystem;
import amara.game.entitysystem.EntityWrapper;
import amara.game.entitysystem.components.physics.*;
import com.jme3.math.Vector2f;

/**
 *
 * @author Philipp
 */
public class MovementSystem implements EntitySystem
{

    public void update(EntityMap entityMap, float deltaSeconds) {
        for(int entity: entityMap.getEntities(MovementSpeedComponent.class, PositionComponent.class))
        {
            EntityWrapper wrapper = new EntityWrapper(entityMap, entity);
            Vector2f newPosition = wrapper.getComponent(PositionComponent.class).getPosition().add(wrapper.getComponent(MovementSpeedComponent.class).getSpeed().mult(deltaSeconds));
            wrapper.setComponent(new PositionComponent(newPosition));
        }
    }
    
}
