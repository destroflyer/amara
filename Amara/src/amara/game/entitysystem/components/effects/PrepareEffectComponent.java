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
public class PrepareEffectComponent{

    public PrepareEffectComponent(){
        
    }
    
    public PrepareEffectComponent(int effectEntity){
        this.effectEntity = effectEntity;
    }
    private int effectEntity;

    public int getEffectEntity(){
        return effectEntity;
    }
}
