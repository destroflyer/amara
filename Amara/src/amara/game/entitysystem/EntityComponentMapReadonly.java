/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

import java.util.Set;

/**
 *
 * @author Philipp
 */
public interface EntityComponentMapReadonly
{
    public <T> T getComponent(int entity, Class<T> componentClass);
    public boolean hasComponent(int entity, Class componentClass);
    public boolean hasAllComponents(int entity, Class... componentsClasses);
    public boolean hasAnyComponent(int entity, Class... componentsClasses);
    public Set<Object> getComponents(int entity);
    public Set<Integer> getEntitiesWithAll(Class... componentsClasses);
    public Set<Integer> getEntitiesWithAny(Class... componentsClasses);
    boolean isEmpty();
    boolean hasEntity(int entity);
}
