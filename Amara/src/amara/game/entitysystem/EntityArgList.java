/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

import java.util.*;

/**
 *
 * @author Philipp
 */
class EntityArgList<T>
{
    private List<Integer> entities = new ArrayList<Integer>();
    private List<T> args = new ArrayList<T>();
    
    public void add(int entity, T arg)
    {
        entities.add(entity);
        args.add(arg);
    }
    
    public int getEntity(int index)
    {
        return entities.get(index);
    }
    
    public T getArg(int index)
    {
        return args.get(index);
    }
    
    public int size()
    {
        return entities.size();
    }
    
    public void clear()
    {
        entities.clear();
        args.clear();
    }
}
