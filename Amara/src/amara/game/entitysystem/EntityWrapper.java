/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

/**
 *
 * @author Philipp
 */
public class EntityWrapper
{
    private EntityMap entityMap;
    private int entity;

    public EntityWrapper(EntityMap entityMap, int entity)
    {
        this.entityMap = entityMap;
        this.entity = entity;
    }

    public int getId()
    {
        return entity;
    }
    
    public <T> T getComponent(Class componentClass)
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
    
    public void clear()
    {
        entityMap.clear(entity);
    }

    @Override
    public String toString()
    {
        return "[Entity id=" + entity + "]";
    }
}