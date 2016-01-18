/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units.effecttriggers.triggers;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class RepeatingTriggerComponent{

    public RepeatingTriggerComponent(){
        
    }

    public RepeatingTriggerComponent(float intervalDuration){
        this.intervalDuration = intervalDuration;
    }
    @ComponentField(type=ComponentField.Type.TIMER)
    private float intervalDuration;

    public float getIntervalDuration(){
        return intervalDuration;
    }
}
