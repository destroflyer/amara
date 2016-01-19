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
public class IsBindedComponent{

    public IsBindedComponent(){
        
    }
    
    public IsBindedComponent(float remainingDuration){
        this.remainingDuration = remainingDuration;
    }
    @ComponentField(type=ComponentField.Type.TIMER)
    private float remainingDuration;

    public float getRemainingDuration(){
        return remainingDuration;
    }
}
