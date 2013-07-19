/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import intersections.IntersectionTracker;
import intersections.Pair;
import intersections.SweepAndPrune;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import shapes.Transform;

/**
 *
 * @author Philipp
 */
public class IntersectionSystem implements EntitySystem, IntersectionInformant
{
    private SweepAndPrune sap = new SweepAndPrune();
    private ConcurrentHashMap<Integer, Hitbox> hitboxMap = new ConcurrentHashMap<Integer, Hitbox>();
    
    HashSet<Integer> updateNeeded = new HashSet<Integer>();
    
    private Set<Pair<Hitbox>> entries;
    private Set<Pair<Hitbox>> repeaters;
    private Set<Pair<Hitbox>> leavers;
    private IntersectionTracker tracker = new IntersectionTracker();
    
    public void update(EntityWorld entityWorld, float deltaSeconds)
    {
        updateNeeded.clear();
        updateHitboxes(entityWorld);
        updateTransforms(entityWorld);
        
        tracker.next(sap.getAllPairs());
        entries = Collections.unmodifiableSet(tracker.getEntries());
        repeaters = Collections.unmodifiableSet(tracker.getRepeaters());
        leavers = Collections.unmodifiableSet(tracker.getLeavers());
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
    
    private void updateTransforms(EntityWorld entityWorld)
    {
        for(int entity: entityWorld.getRemoved().getEntitiesWithAny(PositionComponent.class, DirectionComponent.class, ScaleComponent.class))
        {
            updateNeeded.add(entity);
        }
        for(int entity: entityWorld.getChanged().getEntitiesWithAny(PositionComponent.class, DirectionComponent.class, ScaleComponent.class))
        {
            updateNeeded.add(entity);
        }
        for(int entity: entityWorld.getNew().getEntitiesWithAny(PositionComponent.class, DirectionComponent.class, ScaleComponent.class))
        {
            updateNeeded.add(entity);
        }
        for(int entity: updateNeeded)
        {
            updateTransforms(entityWorld.getWrapped(entity));
        }
    }
    
    private void updateTransforms(EntityWrapper entity)
    {
        PositionComponent pos = entity.getComponent(PositionComponent.class);
        DirectionComponent dir = entity.getComponent(DirectionComponent.class);
        ScaleComponent scale = entity.getComponent(ScaleComponent.class);
        Transform transform = hitboxMap.get(entity.getId()).getShape().getTransform();
        if(pos != null)
        {
            transform.setPosition(pos.getPosition().x, pos.getPosition().y);
        }
        else
        {
            transform.setPosition(0, 0);
        }
        if(dir != null)
        {
            transform.setRadian(dir.getRadian());
        }
        else
        {
            transform.setRadian(0);
        }
        if(scale != null)
        {
            transform.setScale(scale.getScale());
        }
        else
        {
            transform.setScale(0);
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
        updateNeeded.add(entity);
    }
    private void add(EntityWrapper entity)
    {
        add(entity.getId(), new Hitbox(entity.getId(), entity.getComponent(HitboxComponent.class).getShape()));
    }
    
    public Set<Pair<Hitbox>> getEntries()
    {
        return repeaters;
    }
    
    public Set<Pair<Hitbox>> getRepeaters()
    {
        return repeaters;
    }
    
    public Set<Pair<Hitbox>> getLeavers()
    {
        return repeaters;
    }
}
