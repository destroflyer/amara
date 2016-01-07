/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.general;

import com.jme3.network.serializing.Serializable;
import amara.game.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class RemoveEffectTriggersComponent{

    public RemoveEffectTriggersComponent(){
        
    }

    public RemoveEffectTriggersComponent(int... effectTriggerEntities){
        this.effectTriggerEntities = effectTriggerEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] effectTriggerEntities;

    public int[] getEffectTriggerEntities(){
        return effectTriggerEntities;
    }
}
