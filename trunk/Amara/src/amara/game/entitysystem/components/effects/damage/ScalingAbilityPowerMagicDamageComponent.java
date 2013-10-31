/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.damage;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class ScalingAbilityPowerMagicDamageComponent{

    public ScalingAbilityPowerMagicDamageComponent(){
        
    }
    
    public ScalingAbilityPowerMagicDamageComponent(float ratio){
        this.ratio = ratio;
    }
    private float ratio;

    public float getRatio(){
        return ratio;
    }
}
