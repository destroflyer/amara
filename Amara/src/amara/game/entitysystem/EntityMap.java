/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Philipp
 */
public class EntityMap
{
    private ConcurrentHashMap<Class, ConcurrentHashMap<Integer, Object>> componentMaps = new ConcurrentHashMap<Class, ConcurrentHashMap<Integer, Object>>();
    private int nextEntity;
    private ArrayList<EntityMapComponentObserver> observers = new ArrayList<EntityMapComponentObserver>();
    private ArrayList<EntitySystem> systems = new ArrayList<EntitySystem>();
    private ArrayList<Integer> entities = new ArrayList<Integer>();
    private Comparator<Class> mapSizeComparator =
            new Comparator<Class>()
            {
                public int compare(Class o1, Class o2)
                {
                    return getComponentMap(o1).size() - getComponentMap(o2).size();
                }
            };

    public ArrayList<EntityMapComponentObserver> getObservers()
    {
        return observers;
    }

    public ArrayList<EntitySystem> getSystems()
    {
        return systems;
    }
    
    public void update(float deltaSeconds)
    {
        for(EntitySystem system: systems)
        {
            system.update(this, deltaSeconds);
        }
    }
    
    public int createEntity()
    {
        int entity = nextEntity++;
        entities.add(entity);
        return entity;
    }
    
    public void removeEntity(int entity)
    {
        clear(entity);
        entities.remove(entity);
    }
    
    public void clear(int entity)
    {
        for(Object component: getComponents(entity))
        {
            removeComponent(entity, component.getClass());
        }
    }
    
    public List<Integer> getEntities(Class... requiredComponentsClasses)
    {
        if(requiredComponentsClasses.length == 0)
        {
            return (List<Integer>)entities.clone();
        }
        
        ArrayList<Integer> entityList = new ArrayList<Integer>();
        Arrays.sort(requiredComponentsClasses, mapSizeComparator);
        
        for(int entity: getComponentMap(requiredComponentsClasses[0]).keySet())
        {
            if(hasComponents(entity, requiredComponentsClasses))
            {
                entityList.add(entity);
            }
        }
        
        return entityList;
    }
    
    public boolean hasComponents(int entity, Class... requiredComponentsClasses)
    {
        for(Class componentClass: requiredComponentsClasses)
        {
            if(!getComponentMap(componentClass).containsKey(entity))
            {
                return false;
            }
        }
        return true;
    }
    
    public List<Object> getComponents(int entity)
    {
        ArrayList<Object> components = new ArrayList<Object>();
        Object component;
        for(ConcurrentHashMap<Integer, Object> componentMap: componentMaps.values())
        {
            component = componentMap.get(entity);
            if(component != null)
            {
                components.add(component);
            }
        }
        return components;
    }
    
    public <T> T getComponent(int entity, Class<T> componentClass)
    {
        return (T)getComponentMap(componentClass).get(entity);
    }
    
    public void setComponent(int entity, Object component)
    {
        if(getComponentMap(component.getClass()).put(entity, component) != null)
        {
            for(EntityMapComponentObserver observer: observers)
            {
                observer.onComponentChanged(entity, component.getClass());
            }
        }
        else
        {
            for(EntityMapComponentObserver observer: observers)
            {
                observer.onComponentAdded(entity, component.getClass());
            }
        }
    }
    
    public void removeComponent(int entity, Class componentClass)
    {
        if(getComponentMap(componentClass).remove(entity) != null)
        {
            for(EntityMapComponentObserver observer: observers)
            {
                observer.onComponentRemoved(entity, componentClass);
            }
        }
    }
    
    private ConcurrentHashMap<Integer, Object> getComponentMap(Class componentClass)
    {
        ConcurrentHashMap<Integer, Object> componentMap = componentMaps.get(componentClass);
        if(componentMap == null)
        {
            componentMap = new ConcurrentHashMap<Integer, Object>();
            componentMaps.put(componentClass, componentMap);
        }
        return componentMap;
    }
}