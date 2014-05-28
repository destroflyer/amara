/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.systems.physics.intersection.*;
import amara.game.entitysystem.systems.physics.intersectionHelper.IntersectionInformant;
import amara.game.entitysystem.systems.physics.shapes.*;
import com.jme3.math.Vector2f;

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
        info.updateTrackers(entityWorld);
        for(Pair<Integer> pair: info.getEntries(entityWorld))
        {
            if(entityWorld.hasAllComponents(pair.getA(), IntersectionPushComponent.class, PositionComponent.class))
            {
                if(entityWorld.hasAllComponents(pair.getB(), IntersectionPushComponent.class, PositionComponent.class))
                {
                    applyPushes(entityWorld, pair);
                }
            }
        }
        for(Pair<Integer> pair: info.getRepeaters(entityWorld))
        {
            if(entityWorld.hasAllComponents(pair.getA(), IntersectionPushComponent.class, PositionComponent.class))
            {
                if(entityWorld.hasAllComponents(pair.getB(), IntersectionPushComponent.class, PositionComponent.class))
                {
                    applyPushes(entityWorld, pair);
                }
            }
        }
    }
    
    private void applyPushes(EntityWorld entityWorld, Pair<Integer> pair)
    {
        EntityWrapper entityA = entityWorld.getWrapped(pair.getA());
        EntityWrapper entityB = entityWorld.getWrapped(pair.getB());
        Vector2D vec = entityA.getComponent(HitboxComponent.class).getShape().getResolveVector(entityB.getComponent(HitboxComponent.class).getShape());
        float deltaX = (float)vec.getX() / 2;
        float deltaY = (float)vec.getY() / 2;
        
        Vector2f oldPosition = entityA.getComponent(PositionComponent.class).getPosition();
        Vector2f newPosition = new Vector2f(oldPosition.x + deltaX, oldPosition.y + deltaY);
        entityA.setComponent(new PositionComponent(newPosition));
        
        oldPosition = entityB.getComponent(PositionComponent.class).getPosition();
        newPosition = new Vector2f(oldPosition.x - deltaX, oldPosition.y - deltaY);
        entityB.setComponent(new PositionComponent(newPosition));
    }
}
