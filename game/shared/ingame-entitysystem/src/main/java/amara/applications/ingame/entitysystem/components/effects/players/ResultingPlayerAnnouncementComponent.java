/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.effects.players;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class ResultingPlayerAnnouncementComponent {

    private ResultingPlayerAnnouncementComponent(){

    }

    public ResultingPlayerAnnouncementComponent(String text, float remainingDuration){
        this.text = text;
        this.remainingDuration = remainingDuration;
    }
    private String text;
    @ComponentField(type= ComponentField.Type.TIMER)
    private float remainingDuration;

    public String getText(){
        return text;
    }

    public float getRemainingDuration(){
        return remainingDuration;
    }
}
