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
public interface EntityComponentMap
{
    public <T> T getComponent(int entity, Class<T> componentClass);
    public boolean hasComponent(int entity, Class componentClass);
    public Object setComponent(int entity, Object component);
    public Object removeComponent(int entity, Class componentClass);
    public List<Object> getComponents(int entity);
    public void clearComponents(int entity);
    public boolean hasAllComponents(int entity, Class... componentsClasses);
    public boolean hasAnyComponent(int entity, Class... componentsClasses);
    public List<Integer> getEntitiesWithAll(Class... componentsClasses);
    public List<Integer> getEntitiesWithAny(Class... componentsClasses);
    public void clear();
}
