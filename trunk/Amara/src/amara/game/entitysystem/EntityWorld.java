/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Philipp
 */
public class EntityWorld extends ObservedComponentMap
{
    private int nextEntity;
    private ConcurrentHashMap<EntitySystem, EntityObserver> systemEntityObserverMap = new ConcurrentHashMap<EntitySystem, EntityObserver>();

    public int createEntity()
    {
        return nextEntity++;
    }
    
    public void removeEntity(int entity)
    {
        clearComponents(entity);
    }
    
    public boolean hasEntity(int entity)
    {
        return !getComponents(entity).isEmpty();
    }
    
    public EntityWrapper getWrapped(int entity)
    {
        return new EntityWrapper(this, entity);
    }
    
    public List<EntityWrapper> getWrapped(List<Integer> entities)
    {
        ArrayList<EntityWrapper> list = new ArrayList<EntityWrapper>();
        for(int entity: entities)
        {
            list.add(new EntityWrapper(this, entity));
        }
        return list;
    }
    
    public EntityObserver getEntityObserver(EntitySystem key)
    {
        return systemEntityObserverMap.get(key);
    }
    
    public void createEntityObserver(EntitySystem key)
    {
        EntityObserver observer = new EntityObserver(this);
        systemEntityObserverMap.put(key, observer);
        observer.reset();
    }
    
    public EntityObserver getOrCreateEntityObserver(EntitySystem key)
    {
        if(getEntityObserver(key) == null) createEntityObserver(key);
        return getEntityObserver(key);
    }
}
