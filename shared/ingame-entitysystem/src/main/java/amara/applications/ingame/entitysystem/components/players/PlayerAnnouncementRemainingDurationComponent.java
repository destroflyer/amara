/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.players;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class PlayerAnnouncementRemainingDurationComponent {

    public PlayerAnnouncementRemainingDurationComponent(){

    }

    public PlayerAnnouncementRemainingDurationComponent(float remainingDuration){
        this.remainingDuration = remainingDuration;
    }
    @ComponentField(type=ComponentField.Type.TIMER)
    private float remainingDuration;

    public float getRemainingDuration(){
        return remainingDuration;
    }
}
