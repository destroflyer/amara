/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class ApplyEffectImpactComponent{

    public ApplyEffectImpactComponent(){
        
    }
    
    public ApplyEffectImpactComponent(int targetEntityID){
        this.targetEntityID = targetEntityID;
    }
    private int targetEntityID;

    public int getTargetID(){
        return targetEntityID;
    }
}
