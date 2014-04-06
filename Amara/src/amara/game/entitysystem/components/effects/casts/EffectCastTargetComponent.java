/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.casts;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class EffectCastTargetComponent{

    public EffectCastTargetComponent(){
        
    }
    
    public EffectCastTargetComponent(int targetEntity){
        this.targetEntity = targetEntity;
    }
    private int targetEntity;

    public int getTargetEntity(){
        return targetEntity;
    }
}
