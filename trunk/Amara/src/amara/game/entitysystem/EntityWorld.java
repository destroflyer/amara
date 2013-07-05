/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class EntityWorld
{
    private int nextEntity;
    private ArrayList<EntitySystem> systems = new ArrayList<EntitySystem>();
    private ObservingComponentMap entityMap;

    public ArrayList<EntitySystem> getSystems()
    {
        return systems;
    }
    
    public void update(float deltaSeconds)
    {
        for(EntitySystem system: systems)
        {
            system.update(this, deltaSeconds);
        }
        entityMap.clearObservations();
    }
    
    public int createEntity()
    {
        return nextEntity++;
    }
    
    public void removeEntity(int entity)
    {
        entityMap.clearComponents(entity);
    }
    
    public EntityWrapper getWrapped(int entity)
    {
        return new EntityWrapper(entityMap, entity);
    }
    
    public List<EntityWrapper> getWrapped(List<Integer> entities)
    {
        ArrayList<EntityWrapper> list = new ArrayList<EntityWrapper>();
        for(int entity: entities)
        {
            list.add(new EntityWrapper(entityMap, entity));
        }
        return list;
    }
    
    public List<Integer> getEntities(Class... requiredComponentsClasses)
    {
        return entityMap.getEntitiesWithAll(requiredComponentsClasses);
    }
    
    public EntityComponentMap getCurrent()
    {
        return entityMap;
    }
    
    public EntityComponentMapReadonly getNew()
    {
        return entityMap.getAdded();
    }
    
    public EntityComponentMapReadonly getChanged()
    {
        return entityMap.getChanged();
    }
    
    public EntityComponentMapReadonly getRemoved()
    {
        return entityMap.getRemoved();
    }
}
