/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics;

import amara.game.entitysystem.systems.physics.intersectionHelper.IntersectionInformant;
import amara.game.entitysystem.systems.physics.intersectionHelper.Hitbox;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import intersections.IntersectionTracker;
import intersections.Pair;
import intersections.SweepAndPrune;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import shapes.Shape;
import shapes.Transform;

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
    private IntersectionTracker tracker = new IntersectionTracker();
    
    public void update(EntityWorld entityWorld, float deltaSeconds)
    {
        updateHitboxes(entityWorld);
        
        tracker.next(sap.getAllPairs());
        entries = EntitiesFromHitboxes(tracker.getEntries());
        repeaters = EntitiesFromHitboxes(tracker.getRepeaters());
        leavers = EntitiesFromHitboxes(tracker.getLeavers());
    }
    
    private Set<Pair<Integer>> EntitiesFromHitboxes(Set<Pair<Hitbox>> hitboxes)
    {
        HashSet<Pair<Integer>> set = new HashSet<Pair<Integer>>(hitboxes.size());
        for(Pair<Hitbox> pair: hitboxes)
        {
            set.add(new Pair<Integer>(pair.getA().getId(), pair.getB().getId()));
        }
        return Collections.unmodifiableSet(set);
    }
    
    private void updateHitboxes(EntityWorld entityWorld)
    {
        for(int entity: entityWorld.getRemoved().getEntitiesWithAny(HitboxComponent.class))
        {
            remove(entity);
        }
        for(EntityWrapper entity: entityWorld.getWrapped(entityWorld.getChanged().getEntitiesWithAny(HitboxComponent.class)))
        {
            remove(entity.getId());
            add(entity);
        }
        for(EntityWrapper entity: entityWorld.getWrapped(entityWorld.getNew().getEntitiesWithAny(HitboxComponent.class)))
        {
            add(entity);
        }
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
