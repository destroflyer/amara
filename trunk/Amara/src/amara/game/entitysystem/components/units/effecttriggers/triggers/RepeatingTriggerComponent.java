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
public class RepeatingTriggerComponent{

    public RepeatingTriggerComponent(){
        
    }

    public RepeatingTriggerComponent(float intervalDuration){
        this.intervalDuration = intervalDuration;
    }
    private float intervalDuration;

    public float getIntervalDuration(){
        return intervalDuration;
    }
}
