/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.intersectionHelper;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.systems.physics.intersection.*;
import java.util.*;

/**
 *
 * @author Philipp
 */
public class HitboxUpdater
{
    private SweepAndPrune sap = new SweepAndPrune();
    private HashMap<Integer, Hitbox> hitboxMap = new HashMap<Integer, Hitbox>();
    
    public HashSet<Pair<Integer>> getFilteredIntersections(Filter<Hitbox> filter)
    {
        return entitiesFromHitboxes(sap.getAllPairs(filter));
    }
    
    private HashSet<Pair<Integer>> entitiesFromHitboxes(Set<Pair<Hitbox>> hitboxes)
    {
        HashSet<Pair<Integer>> set = new HashSet<Pair<Integer>>(hitboxes.size());
        for(Pair<Hitbox> pair: hitboxes)
        {
            set.add(new Pair<Integer>(pair.getA().getId(), pair.getB().getId()));
        }
        return set;
    }
    
    public void updateHitboxes(EntityWorld entityWorld)
    {
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, HitboxComponent.class, HitboxActiveComponent.class);
        if(observer.isEmpty()) return;
        for(int entity: observer.getRemoved().getEntitiesWithAny(HitboxComponent.class, HitboxActiveComponent.class))
        {
            remove(entity);
        }
        for(EntityWrapper entity: entityWorld.getWrapped(observer.getChanged().getEntitiesWithAny(HitboxComponent.class, HitboxActiveComponent.class)))
        {
            remove(entity.getId());
            checkedAdd(entity);
        }
        for(EntityWrapper entity: entityWorld.getWrapped(observer.getNew().getEntitiesWithAny(HitboxComponent.class, HitboxActiveComponent.class)))
        {
            checkedAdd(entity);
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
    private void checkedAdd(EntityWrapper entity)
    {
        if(entity.hasAllComponents(HitboxComponent.class, HitboxActiveComponent.class)) add(entity.getId(), new Hitbox(entity));
    }
}
