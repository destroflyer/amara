/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.intersectionHelper;

import amara.game.entitysystem.EntityWorld;
import amara.game.entitysystem.components.physics.IntersectionFilterComponent;
import intersections.Filter;

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
        IntersectionFilterComponent filterCompA = entityWorld.getCurrent().getComponent(a, IntersectionFilterComponent.class);
        if(filterCompA == null) return true;
        IntersectionFilterComponent filterCompB = entityWorld.getCurrent().getComponent(b, IntersectionFilterComponent.class);
        if(filterCompB == null) return true;
        return (filterCompA.getCollisionGroups() & filterCompB.getCollidesWithGroups()
              | filterCompB.getCollisionGroups() & filterCompA.getCollidesWithGroups()) != 0;
    }

    @Override
    public boolean pass(Hitbox a, Hitbox b)
    {
        return pass(a.getId(), b.getId());
    }
    
}
