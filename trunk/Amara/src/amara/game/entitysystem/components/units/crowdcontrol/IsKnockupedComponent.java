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
public class IsKnockupedComponent{

    public IsKnockupedComponent(){
        
    }
    
    public IsKnockupedComponent(int knockupEntity, float remainingDuration){
        this.knockupEntity = knockupEntity;
        this.remainingDuration = remainingDuration;
    }
    private int knockupEntity;
    private float remainingDuration;

    public int getKnockupEntity(){
        return knockupEntity;
    }
    
    public float getRemainingDuration(){
        return remainingDuration;
    }
}
