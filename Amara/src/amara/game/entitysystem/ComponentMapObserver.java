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
    
    private final HashMap<Class, HashSet<Integer>> previousNulls = new HashMap<Class, HashSet<Integer>>();
    private final SimpleComponentMap previousValues = new SimpleComponentMap();
    

    ComponentMapObserver(EntityComponentMapReadonly data) {
        this.data = data;
    }

    void refresh() {
        refreshAdded();
        refreshChangedAndRemoved();
        clearPreviousValues();
    }

    private void clearPreviousValues() {
        previousValues.clear();
        for (HashSet<Integer> hashSet : previousNulls.values()) {
            hashSet.clear();
        }
    }

    void markAsChanged(Integer entity, Class componentClass) {
        if (isComponentMarked(componentClass, entity)) {
            return;
        }
        Object previousComponent = data.getComponent(entity, componentClass);
        markWithPreviousValue(entity, componentClass, previousComponent);
    }

    private void markWithPreviousValue(Integer entity, Class componentClass, Object componentValue) {
        if (componentValue == null) {
            markAsNull(entity, componentClass);
        } else {
            previousValues.setComponent(entity, componentValue);
        }
    }

    void markAsNull(Integer entity, Class componentClass) {
        HashSet<Integer> entities = lazyGetPreviousNulls(componentClass);
        entities.add(entity);
    }

    private boolean isComponentMarked(Class componentClass, Integer entity) {
        return previousValues.hasComponent(entity, componentClass) ||  lazyGetPreviousNulls(componentClass).contains(entity);
    }

    private HashSet<Integer> lazyGetPreviousNulls(Class componentClass) {
        HashSet<Integer> entities = previousNulls.get(componentClass);
        if(entities == null) {
            entities = new HashSet<Integer>();
            previousNulls.put(componentClass, entities);
        }
        return entities;
    }
    
    private void refreshAdded() {
        added.clear();
        for (Map.Entry<Class, HashSet<Integer>> entry : previousNulls.entrySet()) {
            for (Integer entity : entry.getValue()) {
                Class componentClass = entry.getKey();
                Object newComponent = data.getComponent(entity, componentClass);
                if (newComponent != null) {
                    added.setComponent(entity, newComponent);
                }
            }
        }
    }
    
    private void refreshChangedAndRemoved() {
        changed.clear();
        removed.clear();
        for (Map.Entry<Class, ConcurrentHashMap<Integer, Object>> classEntity : previousValues.getComponentMaps().entrySet()) {
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
        return added;
    }

    public EntityComponentMapReadonly getChanged() {
        return changed;
    }

    public EntityComponentMapReadonly getRemoved() {
        return removed;
    }
}