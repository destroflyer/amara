/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

/**
 *
 * @author Philipp
 */
public class ComponentMapObserver {
    private SimpleComponentMap added = new SimpleComponentMap();
    private SimpleComponentMap changed = new SimpleComponentMap();
    private SimpleComponentMap removed = new SimpleComponentMap();
    
    public void reset()
    {
        added.clear();
        changed.clear();
        removed.clear();
    }
    
   public boolean isEmpty()
   {
       return added.isEmpty() && changed.isEmpty() && removed.isEmpty();
   }
    
    void onComponentAdded(ObservedComponentMap entityMap, int entity, Object component)
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

    void onComponentChanged(ObservedComponentMap entityMap, int entity, Object component)
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

    void onComponentRemoved(ObservedComponentMap entityMap, int entity, Object component)
    {
        if(added.removeComponent(entity, component.getClass()) == null)
        {
            changed.removeComponent(entity, component.getClass());
            removed.setComponent(entity, component);
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
