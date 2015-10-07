/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Philipp
 */
public class ComponentMapObserver {

    private final EntityComponentMapReadonly data;
    private final SimpleComponentMap added = new SimpleComponentMap();
    private final SimpleComponentMap changed = new SimpleComponentMap();
    private final SimpleComponentMap removed = new SimpleComponentMap();
    private final HashMap<Class, HashSet<Integer>> oldEmpty = new HashMap<Class, HashSet<Integer>>();
    private final SimpleComponentMap oldValues = new SimpleComponentMap();
    private boolean cached = false;

    public ComponentMapObserver(EntityComponentMapReadonly data) {
        this.data = data;
    }

    public void reset() {
        //TODO 3 lines for debugging, only needed for the warning serr
        added.clear();
        changed.clear();
        removed.clear();
        
        oldValues.clear();
        for (HashSet<Integer> hashSet : oldEmpty.values()) {
            hashSet.clear();
        }
        cached = false;
    }

    public boolean isEmpty() {
        updateCache();
        return added.isEmpty() && changed.isEmpty() && removed.isEmpty();
    }

    public void preChange(Integer entity, Class componentClass) {
        if (hasAlreadyChanged(componentClass, entity)) {
            return;
        }
        Object oldComponent = data.getComponent(entity, componentClass);
        setOldComponent(entity, componentClass, oldComponent);
    }

    private void setOldComponent(Integer entity, Class componentClass, Object componentValue) {
        if (componentValue == null) {
            setOldEmpty(entity, componentClass);
        } else {
            cached = false;
            oldValues.setComponent(entity, componentValue);
        }
    }

    public void setOldEmpty(Integer entity, Class componentClass) {
        cached = false;
        HashSet<Integer> entities = createGetEmptyMap(componentClass);
        entities.add(entity);
    }
    
    int i = 0;
    private void updateCache() {
        if (cached) {
            return;
        }
        
        if(!(added.isEmpty() && changed.isEmpty() && removed.isEmpty())) {
            System.err.println("ComponentMapObserverWarning! " + ++i);
            new Exception().printStackTrace();
        }
        
        added.clear();
        changed.clear();
        removed.clear();
        cached = true;
        for (Map.Entry<Class, HashSet<Integer>> entry : oldEmpty.entrySet()) {
            for (Integer entity : entry.getValue()) {
                Class componentClass = entry.getKey();
                Object newComponent = data.getComponent(entity, componentClass);
                if (newComponent != null) {
                    added.setComponent(entity, newComponent);
                }
            }
        }
        
        for (Map.Entry<Class, ConcurrentHashMap<Integer, Object>> classEntity : oldValues.getComponentMaps().entrySet()) {
            for (Map.Entry<Integer, Object> entityValue : classEntity.getValue().entrySet()) {
                Integer entity = entityValue.getKey();
                Object oldComponent = entityValue.getValue();
                Class componentClass = classEntity.getKey();
                Object newComponent = data.getComponent(entity, componentClass);
                if (newComponent == null) {
                    removed.setComponent(entity, oldComponent);
                } else if (componentsEqual(oldComponent, newComponent)) {
//                    System.out.println(newComponent.toString() + " change discarded");
                } else {
                    changed.setComponent(entity, newComponent);
                }
            }
        }
    }

    private boolean componentsEqual(Object componentA, Object componentB) {
        if (componentA.equals(componentB)) {
            return true;
        }
        Class componentClass = componentA.getClass();
        if (componentB.getClass() != componentClass) {
            return false;
        }
        for (Field field : componentClass.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if(!equals(field.get(componentA), field.get(componentB))) {
                    return false;
                }
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(ComponentMapObserver.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } catch (IllegalAccessException ex) {
                Logger.getLogger(ComponentMapObserver.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        if (componentClass.getSuperclass() != Object.class) {
            System.err.println("components with superclasses not supported by ComponentMapObserver.componentsEqual");
        }
        return true;
    }
    
    private boolean equals(Object a, Object b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a.getClass().isArray() && b.getClass().isArray()) {

            int length = Array.getLength(a);
            if (length > 0 && !a.getClass().getComponentType().equals(b.getClass().getComponentType())) {
                return false;
            }
            if (Array.getLength(b) != length) {
                return false;
            }
            for (int i = 0; i < length; i++) {
                if (!equals(Array.get(a, i), Array.get(b, i))) {
                    return false;
                }
            }
            return true;
        }
        return a.equals(b);
    }

    public EntityComponentMapReadonly getNew() {
        updateCache();
        return added;
    }

    public EntityComponentMapReadonly getChanged() {
        updateCache();
        return changed;
    }

    public EntityComponentMapReadonly getRemoved() {
        updateCache();
        return removed;
    }

    private boolean hasAlreadyChanged(Class componentClass, Integer entity) {
        return oldValues.hasComponent(entity, componentClass) ||  createGetEmptyMap(componentClass).contains(entity);
    }

    private HashSet<Integer> createGetEmptyMap(Class componentClass) {
        HashSet<Integer> entities = oldEmpty.get(componentClass);
        if(entities == null) {
            entities = new HashSet<Integer>();
            oldEmpty.put(componentClass, entities);
        }
        return entities;
    }
}
