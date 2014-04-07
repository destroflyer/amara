/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics;

import amara.game.entitysystem.systems.physics.intersectionHelper.IntersectionInformant;
import amara.game.entitysystem.systems.physics.intersectionHelper.Hitbox;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.systems.physics.intersectionHelper.IntersectionFilter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import amara.game.entitysystem.systems.physics.intersection.*;
import amara.game.entitysystem.systems.physics.shapes.*;

/**
 *
 * @author Philipp
 */
public class IntersectionSystem implements EntitySystem, IntersectionInformant
{
    private SweepAndPrune sap = new SweepAndPrune();
    private ConcurrentHashMap<Integer, Hitbox> hitboxMap = new ConcurrentHashMap<Integer, Hitbox>();
    
    private Set<Pair<Integer>> entries = new HashSet<Pair<Integer>>();
    private Set<Pair<Integer>> repeaters = new HashSet<Pair<Integer>>();
    private Set<Pair<Integer>> leavers = new HashSet<Pair<Integer>>();
    private IntersectionTracker<Pair<Integer>> tracker = new IntersectionTracker<Pair<Integer>>();
    
    public void update(EntityWorld entityWorld, float deltaSeconds)
    {
        updateHitboxes(entityWorld);
        
        IntersectionFilter filter = new IntersectionFilter(entityWorld);
                
        tracker.next(EntitiesFromHitboxes(sap.getAllPairs(filter)));
        entries = tracker.getEntries();
        repeaters = tracker.getRepeaters();
        leavers = tracker.getLeavers();
        
        HashSet<Pair<Integer>> tmp = new HashSet<Pair<Integer>>();
        for (Pair<Integer> pair : leavers)
        {
            if(!(entityWorld.hasComponent(pair.getA(), HitboxComponent.class) && entityWorld.hasComponent(pair.getB(), HitboxComponent.class)))
            {
                tmp.add(pair);
            }
        }
        for (Pair<Integer> pair : tmp)
        {
            leavers.remove(pair);
        }
    }
    
    private HashSet<Pair<Integer>> EntitiesFromHitboxes(Set<Pair<Hitbox>> hitboxes)
    {
        HashSet<Pair<Integer>> set = new HashSet<Pair<Integer>>(hitboxes.size());
        for(Pair<Hitbox> pair: hitboxes)
        {
            set.add(new Pair<Integer>(pair.getA().getId(), pair.getB().getId()));
        }
        return set;
    }
    
    private void updateHitboxes(EntityWorld entityWorld)
    {
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, HitboxComponent.class);
        for(int entity: observer.getRemoved().getEntitiesWithAny(HitboxComponent.class))
        {
            remove(entity);
        }
        for(EntityWrapper entity: entityWorld.getWrapped(observer.getChanged().getEntitiesWithAny(HitboxComponent.class)))
        {
            remove(entity.getId());
            add(entity);
        }
        for(EntityWrapper entity: entityWorld.getWrapped(observer.getNew().getEntitiesWithAny(HitboxComponent.class)))
        {
            add(entity);
        }
        observer.reset();
    }
    
    private void remove(int entity)
    {
        sap.remove(hitboxMap.remove(entity));
    }
    
    private void add(int entity, Hitbox hitbox)
    {
        sap.add(hitbox);
        hitboxMap.put(entity, hitbox);
    }
    private void add(EntityWrapper entity)
    {
        add(entity.getId(), new Hitbox(entity));
    }
    
    public Set<Pair<Integer>> getEntries()
    {
        return entries;
    }
    
    public Set<Pair<Integer>> getRepeaters()
    {
        return repeaters;
    }
    
    public Set<Pair<Integer>> getLeavers()
    {
        return leavers;
    }
}
