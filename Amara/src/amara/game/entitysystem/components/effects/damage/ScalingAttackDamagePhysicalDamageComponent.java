/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.damage;

import com.jme3.network.serializing.Serializable;
import amara.game.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class ScalingAttackDamagePhysicalDamageComponent{

    public ScalingAttackDamagePhysicalDamageComponent(){
        
    }
    
    public ScalingAttackDamagePhysicalDamageComponent(float ratio){
        this.ratio = ratio;
    }
    @ComponentField(type=ComponentField.Type.ATTRIBUTE)
    private float ratio;

    public float getRatio(){
        return ratio;
    }
}
