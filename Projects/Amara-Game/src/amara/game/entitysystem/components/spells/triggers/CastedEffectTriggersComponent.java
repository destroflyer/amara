/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.spells.triggers;

import com.jme3.network.serializing.Serializable;
import amara.game.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class CastedEffectTriggersComponent{

    public CastedEffectTriggersComponent(){
        
    }
    
    public CastedEffectTriggersComponent(int... effectTriggerEntities){
        this.effectTriggerEntities = effectTriggerEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] effectTriggerEntities;

    public int[] getEffectTriggerEntities(){
        return effectTriggerEntities;
    }
}
