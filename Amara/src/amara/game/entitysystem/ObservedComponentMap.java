/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Philipp
 */
class ObservedComponentMap extends SimpleComponentMap
{
    private ConcurrentHashMap<Class, HashSet<ComponentMapObserver>> componentObserverMap = new ConcurrentHashMap<Class, HashSet<ComponentMapObserver>>();
    private ConcurrentHashMap<EntitySystem, ComponentMapObserver> systemObserverMap = new ConcurrentHashMap<EntitySystem, ComponentMapObserver>();
    
    private ConcurrentHashMap<EntitySystem, ComponentMapObserver> systemGlobalObserverMap = new ConcurrentHashMap<EntitySystem, ComponentMapObserver>();
    
    @Override
    public Object setComponent(int entity, Object component) {
        Object oldComponent = super.setComponent(entity, component);
        if(oldComponent != null)
        {
            for (ComponentMapObserver observer : getObservers(component.getClass())) {
                observer.onComponentChanged(this, entity, component);
            }
            for(ComponentMapObserver observer : systemGlobalObserverMap.values())
            {
                observer.onComponentChanged(this, entity, component);
            }
        }
        else
        {
            for (ComponentMapObserver observer : getObservers(component.getClass())) {
                observer.onComponentAdded(this, entity, component);
            }
            for(ComponentMapObserver observer : systemGlobalObserverMap.values())
            {
                observer.onComponentAdded(this, entity, component);
            }
        }
        return oldComponent;
    }

    @Override
    public Object removeComponent(int entity, Class componentClass) {
        Object oldComponent = super.removeComponent(entity, componentClass);
        if(oldComponent != null)
        {
            for (ComponentMapObserver observer : getObservers(componentClass)) {
                observer.onComponentRemoved(this, entity, oldComponent);
            }
            for(ComponentMapObserver observer : systemGlobalObserverMap.values())
            {
                observer.onComponentRemoved(this, entity, oldComponent);
            }
        }
        return oldComponent;
    }
    
    private HashSet<ComponentMapObserver> getObservers(Class clazz)
    {
        HashSet<ComponentMapObserver> set = componentObserverMap.get(clazz);
        if(set == null)
        {
            set = new HashSet<ComponentMapObserver>();
            componentObserverMap.put(clazz, set);
        }
        return set;
    }
    
    public ComponentMapObserver getObserver(EntitySystem key)
    {
        ComponentMapObserver observer = systemObserverMap.get(key);
        if(observer == null) observer = systemGlobalObserverMap.get(key);
        return observer;
    }
    
    public void createObserver(EntitySystem key, Class... componentClasses)
    {
        ComponentMapObserver observer = new ComponentMapObserver();
        if(componentClasses.length == 0)
        {
            systemGlobalObserverMap.put(key, observer);
        }
        else
        {
            systemObserverMap.put(key, observer);
            for (Class clazz : componentClasses) {
                getObservers(clazz).add(observer);
                for (int entity : getEntitiesWithAny(clazz)) {
                    observer.onComponentAdded(this, entity, getComponent(entity, clazz));
                }
            }
        }
    }
    
    public ComponentMapObserver getOrCreateObserver(EntitySystem key, Class... componentClasses)
    {
        if(getObserver(key) == null) createObserver(key, componentClasses);
        return getObserver(key);
    }
}