package amara.libraries.entitysystem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    private final ComponentEqualityDefinition componentEquality;

    ComponentMapObserver(EntityComponentMapReadonly data, ComponentEqualityDefinition componentEquality) {
        this.data = data;
        this.componentEquality = componentEquality;
    }

    public void refresh() {
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
            markAsNew(entity, componentClass);
        } else {
            previousValues.setComponent(entity, componentValue);
        }
    }

    void markAsNew(Integer entity, Class componentClass) {
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
                } else if (componentEquality.areComponentsEqual(oldComponent, newComponent)) {
//                    System.out.println(newComponent.toString() + " change discarded");
                } else {
                    changed.setComponent(entity, newComponent);
                }
            }
        }
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