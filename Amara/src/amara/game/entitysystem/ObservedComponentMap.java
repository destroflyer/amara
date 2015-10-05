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
    private ConcurrentHashMap<Object, ComponentMapObserver> systemObserverMap = new ConcurrentHashMap<Object, ComponentMapObserver>();
    
    private ConcurrentHashMap<Object, ComponentMapObserver> systemGlobalObserverMap = new ConcurrentHashMap<Object, ComponentMapObserver>();
    
    @Override
    public Object setComponent(int entity, Object component)
    {
        Class componentClass = component.getClass();
        for (ComponentMapObserver observer : getObservers(componentClass)) {
            observer.preChange(entity, componentClass);
        }
        for (ComponentMapObserver observer : systemGlobalObserverMap.values()) {
            observer.preChange(entity, componentClass);
        }
        Object oldComponent = super.setComponent(entity, component);
//        if(oldComponent != null)
//        {
//            for (ComponentMapObserver observer : getObservers(component.getClass()))
//            {
//                observer.onComponentChanged(this, entity, component);
//            }
//            for(ComponentMapObserver observer : systemGlobalObserverMap.values())
//            {
//                observer.onComponentChanged(this, entity, component);
//            }
//        }
//        else
//        {
//            for (ComponentMapObserver observer : getObservers(component.getClass()))
//            {
//                observer.onComponentAdded(this, entity, component);
//            }
//            for(ComponentMapObserver observer : systemGlobalObserverMap.values())
//            {
//                observer.onComponentAdded(this, entity, component);
//            }
//        }
        return oldComponent;
    }

    @Override
    public Object removeComponent(int entity, Class componentClass) {
        for (ComponentMapObserver observer : getObservers(componentClass)) {
            observer.preChange(entity, componentClass);
        }
        for (ComponentMapObserver observer : systemGlobalObserverMap.values()) {
            observer.preChange(entity, componentClass);
        }
        Object oldComponent = super.removeComponent(entity, componentClass);
//        if(oldComponent != null)
//        {
//            for (ComponentMapObserver observer : getObservers(componentClass)) {
//                observer.onComponentRemoved(this, entity, oldComponent);
//            }
//            for(ComponentMapObserver observer : systemGlobalObserverMap.values())
//            {
//                observer.onComponentRemoved(this, entity, oldComponent);
//            }
//        }
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
    
    public ComponentMapObserver getObserver(Object key)
    {
        ComponentMapObserver observer = systemObserverMap.get(key);
        if(observer == null) {
            observer = systemGlobalObserverMap.get(key);
        }
        return observer;
    }
    
    public void createObserver(Object key, Class... componentClasses)
    {
        ComponentMapObserver observer = new ComponentMapObserver(this);
        if(componentClasses.length == 0)
        {
            systemGlobalObserverMap.put(key, observer);
            for (Class clazz : getComponentMaps().keySet())
            {
                for(int entity: getComponentMaps().get(clazz).keySet())
                {
                    observer.setOldEmpty(entity, clazz);//onComponentAdded(this, entity, getComponent(entity, clazz));
                }
            }
        }
        else
        {
            systemObserverMap.put(key, observer);
            for (Class clazz : componentClasses)
            {
                getObservers(clazz).add(observer);
                for (int entity : getEntitiesWithAny(clazz))
                {
                    observer.setOldEmpty(entity, clazz);//onComponentAdded(this, entity, getComponent(entity, clazz));
                }
            }
        }
    }
    
    public ComponentMapObserver getOrCreateObserver(Object key, Class... componentClasses)
    {
        if(getObserver(key) == null) createObserver(key, componentClasses);
        return getObserver(key);
    }
}