/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.intersectionHelper;

import amara.game.entitysystem.EntityWorld;
import amara.game.entitysystem.components.physics.CollisionGroupComponent;
import amara.game.entitysystem.systems.physics.intersection.Filter;

/**
 *
 * @author Philipp
 */
public class IntersectionFilter extends Filter<Hitbox>
{
    private EntityWorld entityWorld;

    public IntersectionFilter(EntityWorld entityWorld)
    {
        this.entityWorld = entityWorld;
    }

    private boolean pass(Integer a, Integer b)
    {
        CollisionGroupComponent filterCompA = entityWorld.getComponent(a, CollisionGroupComponent.class);
        if(filterCompA == null) return true;
        CollisionGroupComponent filterCompB = entityWorld.getComponent(b, CollisionGroupComponent.class);
        if(filterCompB == null) return true;
        return ((filterCompA.getCollisionGroups() & filterCompB.getCollidesWithGroups())
              | (filterCompB.getCollisionGroups() & filterCompA.getCollidesWithGroups())) != 0;
    }

    @Override
    public boolean pass(Hitbox a, Hitbox b)
    {
        return pass(a.getId(), b.getId()) && a.getShape().intersects(b.getShape());
    }
    
}
