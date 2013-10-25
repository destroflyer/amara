/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Philipp
 */
class SimpleComponentMap implements EntityComponentMap, EntityComponentMapReadonly
{
    private ConcurrentHashMap<Class, ConcurrentHashMap<Integer, Object>> componentMaps = new ConcurrentHashMap<Class, ConcurrentHashMap<Integer, Object>>();
    private Comparator<Class> mapSizeComparator = new Comparator<Class>() {
                public int compare(Class o1, Class o2)
                {
                    return getComponentMap(o1).size() - getComponentMap(o2).size();
                }
            };
    
    private ConcurrentHashMap<Integer, Object> getComponentMap(Class componentClass)
    {
        ConcurrentHashMap<Integer, Object> componentMap = componentMaps.get(componentClass);
        if(componentMap == null)
        {
            componentMap = new ConcurrentHashMap<Integer, Object>();
            componentMaps.put(componentClass, componentMap);
        }
        return componentMap;
    }
    
    public <T> T getComponent(int entity, Class<T> componentClass)
    {
        return (T)getComponentMap(componentClass).get(entity);
    }

    public boolean hasComponent(int entity, Class componentClass)
    {
        return getComponentMap(componentClass).get(entity) != null;
    }

    public Object setComponent(int entity, Object component)
    {
        return getComponentMap(component.getClass()).put(entity, component);
    }

    public Object removeComponent(int entity, Class componentClass)
    {
        return getComponentMap(componentClass).remove(entity);
    }

    public List<Object> getComponents(int entity) {
        ArrayList<Object> components = new ArrayList<Object>();
        Object component;
        for(ConcurrentHashMap<Integer, Object> componentMap: componentMaps.values())
        {
            component = componentMap.get(entity);
            if(component != null)
            {
                components.add(component);
            }
        }
        return components;
    }

    public void clearComponents(int entity)
    {
        for(Object component: getComponents(entity))
        {
            removeComponent(entity, component.getClass());
        }
    }

    public boolean hasAllComponents(int entity, Class... componentsClasses)
    {
        for(Class componentClass: componentsClasses)
        {
            if(!getComponentMap(componentClass).containsKey(entity))
            {
                return false;
            }
        }
        return true;
    }

    public boolean hasAnyComponent(int entity, Class... componentsClasses)
    {
        for(Class componentClass: componentsClasses)
        {
            if(getComponentMap(componentClass).containsKey(entity))
            {
                return true;
            }
        }
        return false;
    }

    public List<Integer> getEntitiesWithAll(Class... componentsClasses)
    {
        if(componentsClasses.length == 0)
        {
            HashSet<Integer> entitySet = new HashSet<Integer>();
            for(ConcurrentHashMap<Integer, Object> map: componentMaps.values())
            {
                entitySet.addAll(map.keySet());
            }
            return new ArrayList<Integer>(entitySet);
        }
        
        ArrayList<Integer> entityList = new ArrayList<Integer>();
        Arrays.sort(componentsClasses, mapSizeComparator);
        
        for(int entity: getComponentMap(componentsClasses[0]).keySet())
        {
            if(hasAllComponents(entity, componentsClasses))
            {
                entityList.add(entity);
            }
        }
        return entityList;
    }

    public List<Integer> getEntitiesWithAny(Class... componentsClasses)
    {
        ArrayList<Integer> entityList = new ArrayList<Integer>();
        for(Class componentClass: componentsClasses)
        {
            for(int entity: getComponentMap(componentClass).keySet())
            {
                if(hasAnyComponent(entity, componentsClasses))
                {
                    entityList.add(entity);
                }
            }
        }
        return entityList;
    }

    public void clear()
    {
        for(ConcurrentHashMap<Integer, Object> map: componentMaps.values())
        {
            map.clear();
        }
    }
    
}
