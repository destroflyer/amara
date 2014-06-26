/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class IsCastingComponent{

    public IsCastingComponent(){
        
    }

    public IsCastingComponent(float remainingDuration, boolean isCancelable){
        this.remainingDuration = remainingDuration;
        this.isCancelable = isCancelable;
    }
    private float remainingDuration;
    private boolean isCancelable;

    public float getRemainingDuration(){
        return remainingDuration;
    }

    public boolean isCancelable(){
        return isCancelable;
    }
}
