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
public class TargetReachedTriggerComponent{

    public TargetReachedTriggerComponent(){
        
    }

    public TargetReachedTriggerComponent(float maximumDistance){
        this.maximumDistance = maximumDistance;
    }
    private float maximumDistance;

    public float getMaximumDistance(){
        return maximumDistance;
    }
}
