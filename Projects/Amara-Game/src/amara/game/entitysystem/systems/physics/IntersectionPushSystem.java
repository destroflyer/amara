/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.systems.physics.intersectionHelper.IntersectionInformant;
import amara.libraries.physics.intersection.*;
import amara.libraries.physics.shapes.Vector2D;

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
        IntersectionTracker<Pair<Integer>> tracker = info.getTracker(entityWorld, this);
        for(Pair<Integer> pair: tracker.getEntries())
        {
            if(entityWorld.hasAllComponents(pair.getA(), IntersectionPushComponent.class, PositionComponent.class))
            {
                if(entityWorld.hasAllComponents(pair.getB(), IntersectionPushComponent.class, PositionComponent.class))
                {
                    applyPushes(entityWorld, pair);
                }
            }
        }
        for(Pair<Integer> pair: tracker.getRepeaters())
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
        Vector2D vec = entityA.getComponent(HitboxComponent.class).getShape().getIntersectionResolver(entityB.getComponent(HitboxComponent.class).getShape());
        vec = vec.mult(0.5);
        if(!vec.withinEpsilon()) {
            float deltaX = (float)vec.getX();
            float deltaY = (float)vec.getY();

            Vector2f oldPosition = entityA.getComponent(PositionComponent.class).getPosition();
            Vector2f newPosition = new Vector2f(oldPosition.x + deltaX, oldPosition.y + deltaY);
            entityA.setComponent(new PositionComponent(newPosition));

            oldPosition = entityB.getComponent(PositionComponent.class).getPosition();
            newPosition = new Vector2f(oldPosition.x - deltaX, oldPosition.y - deltaY);
            entityB.setComponent(new PositionComponent(newPosition));
        }
    }
}
