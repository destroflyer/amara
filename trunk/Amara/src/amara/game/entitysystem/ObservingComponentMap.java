/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

/**
 *
 * @author Philipp
 */
class ObservingComponentMap extends SimpleComponentMap
{
    private SimpleComponentMap added = new SimpleComponentMap();
    private SimpleComponentMap changed = new SimpleComponentMap();
    private SimpleComponentMap removed = new SimpleComponentMap();
    
    public void clearObservations()
    {
        added.clear();
        changed.clear();
        removed.clear();
    }

    public void onComponentAdded(ObservingComponentMap entityMap, int entity, Object component)
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

    public void onComponentChanged(ObservingComponentMap entityMap, int entity, Object component)
    {
        if(!added.hasComponent(entity, component.getClass()))
        {
            changed.setComponent(entity, component);
        }
    }

    public void onComponentRemoved(ObservingComponentMap entityMap, int entity, Object component)
    {
        if(added.removeComponent(entity, component.getClass()) == null)
        {
            changed.removeComponent(entity, component.getClass());
            removed.setComponent(entity, component);
        }
    }

    public SimpleComponentMap getAdded() {
        return added;
    }

    public SimpleComponentMap getChanged() {
        return changed;
    }

    public SimpleComponentMap getRemoved() {
        return removed;
    }

    @Override
    public Object setComponent(int entity, Object component) {
        Object oldComponent = super.setComponent(entity, component);
        if(oldComponent != null)
        {
            onComponentChanged(this, entity, component);
        }
        else
        {
            onComponentAdded(this, entity, component);
        }
        return oldComponent;
    }

    @Override
    public Object removeComponent(int entity, Class componentClass) {
        Object oldComponent = super.removeComponent(entity, componentClass);
        if(oldComponent != null)
        {
            onComponentRemoved(this, entity, oldComponent);
        }
        return oldComponent;
    }

}