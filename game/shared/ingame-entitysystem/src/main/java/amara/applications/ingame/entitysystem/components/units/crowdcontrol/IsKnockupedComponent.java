/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.units.crowdcontrol;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

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
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int knockupEntity;
    @ComponentField(type=ComponentField.Type.TIMER)
    private float remainingDuration;

    public int getKnockupEntity(){
        return knockupEntity;
    }
    
    public float getRemainingDuration(){
        return remainingDuration;
    }
}
