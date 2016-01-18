/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.entitysystem.synchronizing;

import java.util.HashMap;

/**
 *
 * @author Carl
 */
public class BitstreamClassManager{
    
    public static BitstreamClassManager getInstance(){
        if(instance == null){
            instance = new BitstreamClassManager();
        }
        return instance;
    }

    private BitstreamClassManager(){
        
    }
    private static BitstreamClassManager instance;
    private HashMap<Class, Integer> ids = new HashMap<Class, Integer>();
    private HashMap<Integer, Class> classes = new HashMap<Integer, Class>();
    private int nextID;
    
    public void register(Class... serializedClasses){
        for(Class serializedClass : serializedClasses){
            register(serializedClass);
        }
    }
    
    public void register(Class serializedClass){
        ids.put(serializedClass, nextID);
        classes.put(nextID, serializedClass);
        nextID++;
    }
    
    public int getID(Class serializedClass){
        Integer id = ids.get(serializedClass);
        if(id == null){
            System.err.println("Bitstream class not registered: " + serializedClass.getName());
        }
        return id;
    }
    
    public Class getClass(int id){
        return classes.get(id);
    }
    
    public int getCount(){
        return nextID;
    }
}
