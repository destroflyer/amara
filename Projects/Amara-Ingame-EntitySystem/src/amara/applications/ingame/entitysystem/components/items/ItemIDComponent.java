/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.items;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class ItemIDComponent{

    public ItemIDComponent(){
        
    }
    
    public ItemIDComponent(String id){
        this.id = id;
    }
    private String id;

    public String getID(){
        return id;
    }
}
