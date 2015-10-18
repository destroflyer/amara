/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects;

import com.jme3.network.serializing.Serializable;
import amara.game.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class ApplyEffectImpactComponent{

    public ApplyEffectImpactComponent(){
        
    }
    
    public ApplyEffectImpactComponent(int targetEntity){
        this.targetEntity = targetEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int targetEntity;

    public int getTargetEntity(){
        return targetEntity;
    }
}
