/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.players;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class PlayerCharacterComponent{

    public PlayerCharacterComponent(){
        
    }

    public PlayerCharacterComponent(int entity){
        this.entity = entity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int entity;

    public int getEntity(){
        return entity;
    }
}
