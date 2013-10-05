/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.PositionComponent;

/**
 *
 * @author Philipp
 */
public class StrangeSystem implements EntitySystem
{

    public void update(EntityWorld entityWorld, float deltaSeconds)
    {
        for (EntityWrapper entity : entityWorld.getWrapped(entityWorld.getCurrent().getEntitiesWithAny(PositionComponent.class)))
        {
            entity.setComponent(entity.getComponent(PositionComponent.class));
        }
    }
}
