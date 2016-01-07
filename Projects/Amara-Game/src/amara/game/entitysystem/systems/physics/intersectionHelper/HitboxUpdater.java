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
    private SweepAndPrune<Hitbox> sap = new SweepAndPrune<Hitbox>();
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
        ComponentMapObserver observer = entityWorld.requestObserver(this, HitboxComponent.class, HitboxActiveComponent.class);
        for(int entity: observer.getRemoved().getEntitiesWithAll())
        {
            remove(entity);
        }
        for(EntityWrapper entity: entityWorld.getWrapped(observer.getChanged().getEntitiesWithAll()))
        {
            remove(entity.getId());
            checkedAdd(entity);
        }
        for(EntityWrapper entity: entityWorld.getWrapped(observer.getNew().getEntitiesWithAll()))
        {
            checkedAdd(entity);
        }
    }
    
    private void remove(int entity)
    {
        sap.remove(hitboxMap.remove(entity));
    }
    
    private void add(int entity, Hitbox hitbox)
    {
        if(hitboxMap.containsKey(entity)) return;
        sap.add(hitbox);
        hitboxMap.put(entity, hitbox);
    }
    private void checkedAdd(EntityWrapper entity)
    {
        if(entity.hasAllComponents(HitboxComponent.class, HitboxActiveComponent.class) && !hitboxMap.containsKey(entity.getId())) add(entity.getId(), new Hitbox(entity));
    }
}
