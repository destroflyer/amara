/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.audio;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class AudioComponent{

    public AudioComponent(){
        
    }

    public AudioComponent(String audioPath){
        this.audioPath = audioPath;
    }
    @ComponentField(type=ComponentField.Type.FILEPATH)
    private String audioPath;

    public String getAudioPath(){
        return audioPath;
    }
}
