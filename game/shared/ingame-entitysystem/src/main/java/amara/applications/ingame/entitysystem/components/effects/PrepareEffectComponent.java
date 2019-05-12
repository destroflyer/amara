/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.effects;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

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
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int effectEntity;

    public int getEffectEntity(){
        return effectEntity;
    }
}
