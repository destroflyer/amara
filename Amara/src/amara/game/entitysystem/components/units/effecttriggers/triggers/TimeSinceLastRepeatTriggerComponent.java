/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units.effecttriggers.triggers;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class TimeSinceLastRepeatTriggerComponent{

    public TimeSinceLastRepeatTriggerComponent(){
        
    }

    public TimeSinceLastRepeatTriggerComponent(float duration){
        this.duration = duration;
    }
    private float duration;

    public float getDuration(){
        return duration;
    }
}
