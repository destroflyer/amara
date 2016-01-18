/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.entitysystem.synchronizing;

import java.util.HashSet;

/**
 *
 * @author Carl
 */
public class ComponentClassSet{
    
    private HashSet<Class> classes = new HashSet<Class>();
    
    public void add(Class... componentClasses){
        for(Class componentClass : componentClasses){
            classes.add(componentClass);
        }
    }
    
    public void remove(Class componentClass){
        classes.remove(componentClass);
    }
    
    public boolean contains(Class componentClass){
        return classes.contains(componentClass);
    }
    
    public int size(){
        return classes.size();
    }
}
