/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.PositionComponent;
import amara.game.entitysystem.components.physics.PushComponent;
import com.jme3.math.Vector2f;
import intersections.Pair;
import shapes.Vector2D;

/**
 *
 * @author Philipp
 */
public class IntersectionPushSystem implements EntitySystem
{
    private IntersectionInformant info;

    public IntersectionPushSystem(IntersectionInformant info)
    {
        this.info = info;
    }

    public void update(EntityWorld entityWorld, float deltaSeconds)
    {
        for(Pair<Hitbox> pair: info.getEntries())
        {
            if(entityWorld.getCurrent().hasAllComponents(pair.getA().getId(), PushComponent.class, PositionComponent.class))
            {
                if(entityWorld.getCurrent().hasAllComponents(pair.getB().getId(), PushComponent.class, PositionComponent.class))
                {
                    applyPushes(entityWorld, pair);
                }
            }
        }
        for(Pair<Hitbox> pair: info.getRepeaters())
        {
            if(entityWorld.getCurrent().hasAllComponents(pair.getA().getId(), PushComponent.class, PositionComponent.class))
            {
                if(entityWorld.getCurrent().hasAllComponents(pair.getB().getId(), PushComponent.class, PositionComponent.class))
                {
                    applyPushes(entityWorld, pair);
                }
            }
        }
    }
    
    private void applyPushes(EntityWorld entityWorld, Pair<Hitbox> pair)
    {
        Vector2D vec = pair.getA().getShape().getResolveVector(pair.getB().getShape());
        float deltaX = (float)vec.getX() / 2;
        float deltaY = (float)vec.getY() / 2;
        
        EntityWrapper entity = entityWorld.getWrapped(pair.getA().getId());
        Vector2f oldPosition = entity.getComponent(PositionComponent.class).getPosition();
        Vector2f newPosition = new Vector2f(oldPosition.x + deltaX, oldPosition.y + deltaY);
        entity.setComponent(new PositionComponent(newPosition));
        
        entity = entityWorld.getWrapped(pair.getB().getId());
        oldPosition = entity.getComponent(PositionComponent.class).getPosition();
        newPosition = new Vector2f(oldPosition.x - deltaX, oldPosition.y - deltaY);
        entity.setComponent(new PositionComponent(newPosition));
    }
}
