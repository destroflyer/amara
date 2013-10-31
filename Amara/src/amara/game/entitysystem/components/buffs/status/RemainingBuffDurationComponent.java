/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.buffs.status;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class RemainingBuffDurationComponent{

    public RemainingBuffDurationComponent(){
        
    }
    
    public RemainingBuffDurationComponent(float remainingDuration){
        this.remainingDuration = remainingDuration;
    }
    private float remainingDuration;

    public float getRemainingDuration(){
        return remainingDuration;
    }
}
