/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units.crowdcontrol;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class IsKnockupedImmuneComponent{

    public IsKnockupedImmuneComponent(){
        
    }
    
    public IsKnockupedImmuneComponent(float remainingDuration){
        this.remainingDuration = remainingDuration;
    }
    private float remainingDuration;
    
    public float getRemainingDuration(){
        return remainingDuration;
    }
}
