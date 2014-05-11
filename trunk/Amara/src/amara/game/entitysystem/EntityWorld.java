/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Philipp
 */
public class EntityWorld extends ObservedComponentMap
{
    private int nextEntity;
    private ConcurrentHashMap<Object, EntityObserver> systemEntityObserverMap = new ConcurrentHashMap<Object, EntityObserver>();

    public int createEntity()
    {
        return nextEntity++;
    }
    
    public void removeEntity(int entity)
    {
        clearComponents(entity);
    }
    
    public EntityWrapper getWrapped(int entity)
    {
        return new EntityWrapper(this, entity);
    }
    
    public List<EntityWrapper> getWrapped(Collection<Integer> entities)
    {
        ArrayList<EntityWrapper> list = new ArrayList<EntityWrapper>();
        for(int entity: entities)
        {
            list.add(new EntityWrapper(this, entity));
        }
        return list;
    }
    
    public EntityObserver getEntityObserver(Object key)
    {
        return systemEntityObserverMap.get(key);
    }
    
    public void createEntityObserver(Object key)
    {
        EntityObserver observer = new EntityObserver(this);
        systemEntityObserverMap.put(key, observer);
        observer.reset();
    }
    
    public EntityObserver getOrCreateEntityObserver(Object key)
    {
        if(getEntityObserver(key) == null) createEntityObserver(key);
        return getEntityObserver(key);
    }
}
