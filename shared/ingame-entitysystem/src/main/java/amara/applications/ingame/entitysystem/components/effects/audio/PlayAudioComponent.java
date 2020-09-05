/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.effects.audio;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class PlayAudioComponent {

    public PlayAudioComponent() {
        
    }

    public PlayAudioComponent(boolean clone, int... audioEntities) {
        this.clone = clone;
        this.audioEntities = audioEntities;
    }
    private boolean clone;
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] audioEntities;

    public boolean isClone() {
        return clone;
    }

    public int[] getAudioEntities() {
        return audioEntities;
    }
}
