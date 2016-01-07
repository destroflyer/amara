/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics;

import amara.game.entitysystem.*;
import amara.game.entitysystem.systems.physics.intersection.*;
import amara.game.entitysystem.systems.physics.intersectionHelper.*;
import java.util.*;

/**
 *
 * @author Philipp
 */
public class IntersectionObserver implements IntersectionInformant
{
    private TransformUpdater transformUpdater = new TransformUpdater();
    private HitboxUpdater hitboxUpdater = new HitboxUpdater();
    
    private HashMap<Object, Object> trackers = new HashMap<Object, Object>();
    
    public void updateHitboxes(EntityWorld entityWorld)
    {
        transformUpdater.updateTransforms(entityWorld);
        hitboxUpdater.updateHitboxes(entityWorld);
    }
    
    public IntersectionTracker<Pair<Integer>> getTracker(EntityWorld entityWorld, Object key)
    {
        Object obj = trackers.get(key);
        if(obj == null)
        {
            obj = new IntersectionTracker<Pair<Integer>>();
            trackers.put(key, obj);
        }
        IntersectionTracker<Pair<Integer>> tracker = (IntersectionTracker<Pair<Integer>>)obj;
        updateTracker(entityWorld, tracker);
        return tracker;
    }
    
    private void updateTracker(EntityWorld entityWorld, IntersectionTracker<Pair<Integer>> tracker)
    {
        updateHitboxes(entityWorld);
        IntersectionFilter filter = new IntersectionFilter(entityWorld);
        tracker.next(hitboxUpdater.getFilteredIntersections(filter));
    }
}
