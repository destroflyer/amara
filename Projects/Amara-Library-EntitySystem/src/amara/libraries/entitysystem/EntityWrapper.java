/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.entitysystem;

/**
 *
 * @author Philipp
 */
public class EntityWrapper
{
    private EntityComponentMap entityMap;
    private Integer entity;

    EntityWrapper(EntityComponentMap entityMap, Integer entity)
    {
        this.entityMap = entityMap;
        this.entity = entity;
    }

    public Integer getId()
    {
        return entity;
    }
    
    public <T> T getComponent(Class<T> componentClass)
    {
        return entityMap.getComponent(entity, componentClass);
    }
    
    public void setComponent(Object component)
    {
        entityMap.setComponent(entity, component);
    }
    
    public void removeComponent(Class componentClass)
    {
        entityMap.removeComponent(entity, componentClass);
    }
    
    public void clearComponents()
    {
        entityMap.clearComponents(entity);
    }
    
    public boolean hasAnyComponent(Class ... components)
    {
        return entityMap.hasAnyComponent(entity, components);
    }
    
    public boolean hasAllComponents(Class ... components)
    {
        return entityMap.hasAllComponents(entity, components);
    }
    
    public boolean hasComponent(Class componentClass)
    {
        return entityMap.hasComponent(entity, componentClass);
    }

    @Override
    public String toString()
    {
        return "[Entity id=" + entity + "]";
    }

    @Override
    public int hashCode()
    {
        return entityMap.hashCode() ^ entity;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj == null || obj.getClass() != EntityWrapper.class) return false;
        EntityWrapper wrapper = (EntityWrapper)obj;
        return wrapper.entityMap.equals(entityMap) && wrapper.entity == entity;
    }
    
}