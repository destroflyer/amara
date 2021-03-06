/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.entitysystem;

import java.util.*;
import amara.libraries.physics.intersection.IntersectionTracker;

/**
 *
 * @author Philipp
 */
public class EntityObserver
{
    private IntersectionTracker<Integer> tracker = new IntersectionTracker<Integer>();
    private EntityWorld world;

    EntityObserver(EntityWorld world) {
        this.world = world;
    }
    
    public Set<Integer> NewEntities()
    {
        return tracker.getEntries();
    }
    public Set<Integer> RemovedEntities()
    {
        return tracker.getLeavers();
    }
    
    public void reset()
    {
        next(world.getEntitiesWithAll());
    }
    
    private void next(List<Integer> next)
    {
        HashSet<Integer> set = new HashSet<Integer>(next);
        next(set);
    }
    
    public void next(Set<Integer> next)
    {
        tracker.next(next);
    }
}
