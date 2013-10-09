/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

import java.util.List;

/**
 *
 * @author Philipp
 */
class DelayedComponentMap implements DelayedEntityComponentMap
{
    private SimpleComponentMap current = new SimpleComponentMap();
    private SimpleComponentMap added = new SimpleComponentMap();
    private SimpleComponentMap changed = new SimpleComponentMap();
    private SimpleComponentMap removed = new SimpleComponentMap();
    private SimpleComponentMap written = new SimpleComponentMap();
    
    private EntityArgList<Object> setList = new EntityArgList<Object>();
    private EntityArgList<Class> removeList = new EntityArgList<Class>();
    
    private boolean overwriteErrorEnabled = false;//true;

    public void setComponent(int entity, Object component) {
        setList.add(entity, component);
        if(overwriteErrorEnabled)
        {
            if(written.setComponent(entity, component) != null)
            {
                throw new Error(component.getClass().getCanonicalName() + " was overwritten.");
            }
        }
    }

    public void removeComponent(int entity, Class componentClass) {
        removeList.add(entity, componentClass);
        if(overwriteErrorEnabled)
        {
            if(written.removeComponent(entity, componentClass) != null)
            {
                throw new Error(componentClass.getCanonicalName() + " was overwritten.");
            }
        }
    }

    public void clearComponents(int entity) {
        for (Object component : current.getComponents(entity)) {
            removeComponent(entity, component.getClass());
        }
    }

    public <T> T getComponent(int entity, Class<T> componentClass) {
        return current.getComponent(entity, componentClass);
    }

    public List<Object> getComponents(int entity) {
        return current.getComponents(entity);
    }

    public boolean hasComponent(int entity, Class componentClass) {
        return current.hasComponent(entity, componentClass);
    }

    public boolean hasAllComponents(int entity, Class... componentsClasses) {
        return current.hasAllComponents(entity, componentsClasses);
    }

    public boolean hasAnyComponent(int entity, Class... componentsClasses) {
        return current.hasAnyComponent(entity, componentsClasses);
    }

    public List<Integer> getEntitiesWithAll(Class... componentsClasses) {
        return current.getEntitiesWithAll(componentsClasses);
    }

    public List<Integer> getEntitiesWithAny(Class... componentsClasses) {
        return current.getEntitiesWithAny(componentsClasses);
    }
    
    public void applyChanges()
    {
        added.clear();
        changed.clear();
        removed.clear();
        written.clear();
        for (int i = 0; i < setList.size(); i++) {
            applySetComponent(setList.getEntity(i), setList.getArg(i));
        }
        setList.clear();
        for (int i = 0; i < removeList.size(); i++) {
            applyRemoveComponent(removeList.getEntity(i), removeList.getArg(i));
        }
        removeList.clear();
    }
    
    private void applySetComponent(int entity, Object component)
    {
        if(current.setComponent(entity, component) != null)
        {
            onComponentChanged(entity, component);
        }
        else
        {
            onComponentAdded(entity, component);
        }
    }
    
    private void applyRemoveComponent(int entity, Class componentClass)
    {
        Object oldComponent = current.removeComponent(entity, componentClass);
        if(oldComponent != null)
        {
            onComponentRemoved(entity, oldComponent);
        }
    }
    
    private void onComponentAdded(int entity, Object component)
    {
        if(removed.removeComponent(entity, component.getClass()) != null)
        {
            changed.setComponent(entity, component);
        }
        else
        {
            added.setComponent(entity, component);
        }
    }

    private void onComponentChanged(int entity, Object component)
    {
        if(!added.hasComponent(entity, component.getClass()))
        {
            changed.setComponent(entity, component);
        }
        else
        {
            added.setComponent(entity, component);
        }
    }

    private void onComponentRemoved(int entity, Object component)
    {
        if(added.removeComponent(entity, component.getClass()) == null)
        {
            changed.removeComponent(entity, component.getClass());
            removed.setComponent(entity, component);
        }
    }
    
    public EntityComponentMapReadonly getAdded() {
        return added;
    }

    public EntityComponentMapReadonly getChanged() {
        return changed;
    }

    public EntityComponentMapReadonly getRemoved() {
        return removed;
    }

    public List<Integer> getEntitiesWith(Class componentsClass) {
        return current.getEntitiesWith(componentsClass);
    }
}
