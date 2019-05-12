/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.audio;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class AudioVolumeComponent{

    public AudioVolumeComponent(){
        
    }

    public AudioVolumeComponent(float volume){
        this.volume = volume;
    }
    private float volume;

    public float getVolume(){
        return volume;
    }
}
