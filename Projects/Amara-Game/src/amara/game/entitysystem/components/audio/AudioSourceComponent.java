/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.audio;

import com.jme3.network.serializing.Serializable;
import amara.game.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class AudioSourceComponent{

    public AudioSourceComponent(){
        
    }

    public AudioSourceComponent(int entity){
        this.entity = entity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int entity;

    public int getEntity(){
        return entity;
    }
}
