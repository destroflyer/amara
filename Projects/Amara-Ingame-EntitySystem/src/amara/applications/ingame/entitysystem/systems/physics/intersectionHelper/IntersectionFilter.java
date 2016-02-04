/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.physics.intersectionHelper;

import amara.applications.ingame.entitysystem.components.physics.CollisionGroupComponent;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.physics.intersection.Filter;

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

    @Override
    public boolean pass(Hitbox a, Hitbox b)
    {
        return (areCollisionGroupsMatching(entityWorld, a.getId(), b.getId()) && a.getShape().intersects(b.getShape()));
    }

    public static boolean areCollisionGroupsMatching(EntityWorld entityWorld, int entity1, int entity2)
    {
        CollisionGroupComponent filterCompA = entityWorld.getComponent(entity1, CollisionGroupComponent.class);
        if(filterCompA == null) return true;
        CollisionGroupComponent filterCompB = entityWorld.getComponent(entity2, CollisionGroupComponent.class);
        if(filterCompB == null) return true;
        return ((filterCompA.getCollisionGroups() & filterCompB.getCollidesWithGroups())
              | (filterCompB.getCollisionGroups() & filterCompA.getCollidesWithGroups())) != 0;
    }
}
