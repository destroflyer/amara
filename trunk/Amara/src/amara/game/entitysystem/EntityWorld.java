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
public class EntityWorld extends ObservedComponentMap
{
    private int nextEntity;

    public int createEntity()
    {
        return nextEntity++;
    }
    
    public void removeEntity(int entity)
    {
        clearComponents(entity);
    }
    
    public EntityWrapper getWrapped(int entity)
    {
        return new EntityWrapper(this, entity);
    }
    
    public List<EntityWrapper> getWrapped(List<Integer> entities)
    {
        ArrayList<EntityWrapper> list = new ArrayList<EntityWrapper>();
        for(int entity: entities)
        {
            list.add(new EntityWrapper(this, entity));
        }
        return list;
    }
}
