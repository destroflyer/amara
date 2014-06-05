/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.audio;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class AudioSuccessorComponent{

    public AudioSuccessorComponent(){
        
    }

    public AudioSuccessorComponent(int audioEntity, float delay){
        this.audioEntity = audioEntity;
        this.delay = delay;
    }
    private int audioEntity;
    private float delay;

    public int getAudioEntity(){
        return audioEntity;
    }

    public float getDelay(){
        return delay;
    }
}
