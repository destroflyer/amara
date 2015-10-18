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
public class AudioSuccessorComponent{

    public AudioSuccessorComponent(){
        
    }

    public AudioSuccessorComponent(int audioEntity, float delay){
        this.audioEntity = audioEntity;
        this.delay = delay;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int audioEntity;
    @ComponentField(type=ComponentField.Type.TIMER)
    private float delay;

    public int getAudioEntity(){
        return audioEntity;
    }

    public float getDelay(){
        return delay;
    }
}
