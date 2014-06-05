/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.audio;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class PlayAudioComponent{

    public PlayAudioComponent(){
        
    }
    
    public PlayAudioComponent(int... audioEntities){
        this.audioEntities = audioEntities;
    }
    private int[] audioEntities;

    public int[] getAudioEntities(){
        return audioEntities;
    }
}
