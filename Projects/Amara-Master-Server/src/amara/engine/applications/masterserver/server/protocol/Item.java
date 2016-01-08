/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.protocol;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class Item{

    public Item(){
        
    }
    
    public Item(int id, String name, String title){
        this.id = id;
        this.name = name;
        this.title = title;
    }
    private int id;
    private String name;
    private String title;

    public int getID(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getTitle(){
        return title;
    }
}
