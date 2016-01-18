/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.entitysystem;

import java.util.Set;

/**
 *
 * @author Philipp
 */
public interface EntityComponentMapReadonly
{
    public <T> T getComponent(Integer entity, Class<T> componentClass);
    public boolean hasComponent(Integer entity, Class componentClass);
    public boolean hasAllComponents(Integer entity, Class... componentsClasses);
    public boolean hasAnyComponent(Integer entity, Class... componentsClasses);
    public Set<Object> getComponents(Integer entity);
    public Set<Integer> getEntitiesWithAll(Class... componentsClasses);
    public Set<Integer> getEntitiesWithAny(Class... componentsClasses);
    boolean isEmpty();
    boolean hasEntity(Integer entity);
}
