/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.audio;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class StopAudioComponent{

    public StopAudioComponent(){
        
    }
    
    public StopAudioComponent(int... audioEntities){
        this.audioEntities = audioEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] audioEntities;

    public int[] getAudioEntities(){
        return audioEntities;
    }
}
