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
public class NameComponent{

    public NameComponent(){
        
    }
    
    public NameComponent(String name){
        this.name = name;
    }
    private String name;

    public String getName(){
        return name;
    }
}
