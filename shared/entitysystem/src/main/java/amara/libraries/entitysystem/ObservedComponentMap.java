package amara.libraries.entitysystem;

import java.util.*;

/**
 *
 * @author Philipp
 */
class ObservedComponentMap extends SimpleComponentMap {

    private Map<Class, HashSet<ComponentMapObserver>> componentObserverMap = new HashMap<Class, HashSet<ComponentMapObserver>>();
    private Map<Object, ComponentMapObserver> systemObserverMap = new HashMap<Object, ComponentMapObserver>();
    private Map<Object, ComponentMapObserver> systemGlobalObserverMap = new HashMap<Object, ComponentMapObserver>();

    @Override
    public Object setComponent(Integer entity, Object component) {
        Class componentClass = component.getClass();
        for (ComponentMapObserver observer : getObservers(componentClass)) {
            observer.markAsChanged(entity, componentClass);
        }
        for (ComponentMapObserver observer : systemGlobalObserverMap.values()) {
            observer.markAsChanged(entity, componentClass);
        }
        return super.setComponent(entity, component);
    }

    @Override
    public Object removeComponent(Integer entity, Class componentClass) {
        for (ComponentMapObserver observer : getObservers(componentClass)) {
            observer.markAsChanged(entity, componentClass);
        }
        for (ComponentMapObserver observer : systemGlobalObserverMap.values()) {
            observer.markAsChanged(entity, componentClass);
        }
        return super.removeComponent(entity, componentClass);
    }

    private HashSet<ComponentMapObserver> getObservers(Class clazz) {
        HashSet<ComponentMapObserver> set = componentObserverMap.get(clazz);
        if (set == null) {
            set = new HashSet<ComponentMapObserver>();
            componentObserverMap.put(clazz, set);
        }
        return set;
    }

    public ComponentMapObserver getObserver(Object key) {
        ComponentMapObserver observer = systemObserverMap.get(key);
        if (observer == null) {
            observer = systemGlobalObserverMap.get(key);
        }
        return observer;
    }
    
    public ComponentMapObserver requestObserver(Object key, Class... componentClasses) {
        return requestObserver(key, DefaultComponentEqualityDefinition.SINGLETON, componentClasses);
    }

    public ComponentMapObserver requestObserver(Object key, ComponentEqualityDefinition componentEquality, Class... componentClasses) {
        if (getObserver(key) == null) {
            createObserver(key, componentEquality, componentClasses);
        }
        ComponentMapObserver observer = getObserver(key);
        observer.refresh();
        return observer;
    }

    private void createObserver(Object key, ComponentEqualityDefinition componentEquality, Class... componentClasses) {
        ComponentMapObserver observer = new ComponentMapObserver(this, componentEquality);
        if (componentClasses.length == 0) {
            systemGlobalObserverMap.put(key, observer);
            for (Class clazz : getComponentMaps().keySet()) {
                for (Integer entity : getComponentMaps().get(clazz).keySet()) {
                    observer.markAsNew(entity, clazz);
                }
            }
        } else {
            systemObserverMap.put(key, observer);
            for (Class clazz : componentClasses) {
                getObservers(clazz).add(observer);
                for (Integer entity : getEntitiesWithAny(clazz)) {
                    observer.markAsNew(entity, clazz);
                }
            }
        }
    }
}