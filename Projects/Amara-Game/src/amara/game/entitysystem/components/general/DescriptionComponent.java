/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.general;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class DescriptionComponent{

    public DescriptionComponent(){
        
    }
    
    public DescriptionComponent(String description){
        this.description = description;
    }
    private String description;

    public String getDescription(){
        return description;
    }
}
