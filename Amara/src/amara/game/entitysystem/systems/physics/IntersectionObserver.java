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
    
    private Set<Pair<Integer>> entries = new HashSet<Pair<Integer>>();
    private Set<Pair<Integer>> repeaters = new HashSet<Pair<Integer>>();
    private Set<Pair<Integer>> leavers = new HashSet<Pair<Integer>>();
    private IntersectionTracker<Pair<Integer>> tracker = new IntersectionTracker<Pair<Integer>>();
    
    public void updateHitboxes(EntityWorld entityWorld)
    {
        transformUpdater.updateTransforms(entityWorld);
        hitboxUpdater.updateHitboxes(entityWorld);
    }
    
    public void updateTrackers(EntityWorld entityWorld)
    {
        updateHitboxes(entityWorld);
        IntersectionFilter filter = new IntersectionFilter(entityWorld);

        tracker.next(hitboxUpdater.getFilteredIntersections(filter));
        entries = null;
        repeaters = null;
        leavers = null;
    }

    public Set<Pair<Integer>> getEntries(EntityWorld entityWorld)
    {
        if(entries == null) entries = tracker.getEntries();
        return entries;
    }

    public Set<Pair<Integer>> getRepeaters(EntityWorld entityWorld)
    {
        if(repeaters == null) repeaters = tracker.getRepeaters();
        return repeaters;
    }

    public Set<Pair<Integer>> getLeavers(EntityWorld entityWorld)
    {
        if(leavers == null)
        {
            leavers = tracker.getLeavers();
        
            //This would remove all entities from leavers
            //which dont have an active hitbox
//            HashSet<Pair<Integer>> tmp = new HashSet<Pair<Integer>>();
//            for (Pair<Integer> pair : leavers)
//            {
//                if(!(entityWorld.hasAllComponents(pair.getA(), HitboxComponent.class, HitboxActiveComponent.class) && entityWorld.hasAllComponents(pair.getB(), HitboxComponent.class, HitboxActiveComponent.class)))
//                {
//                    tmp.add(pair);
//                }
//            }
//            for (Pair<Integer> pair : tmp)
//            {
//                leavers.remove(pair);
//            }
        }
        return leavers;
    }
}
