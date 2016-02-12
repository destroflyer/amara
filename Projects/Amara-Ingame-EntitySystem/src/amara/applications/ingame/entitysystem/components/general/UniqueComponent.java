/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.general;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class UniqueComponent{

    public UniqueComponent(){
        
    }
    
    public UniqueComponent(String id){
        this.id = id;
    }
    private String id;

    public String getID(){
        return id;
    }
}
