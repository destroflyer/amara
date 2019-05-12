/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

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
    @ComponentField(type=ComponentField.Type.TIMER)
    private float duration;

    public float getDuration(){
        return duration;
    }
}
