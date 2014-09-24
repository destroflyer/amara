/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.buffs;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class OnBuffRemoveEffectTriggersComponent{

    public OnBuffRemoveEffectTriggersComponent(){
        
    }
    
    public OnBuffRemoveEffectTriggersComponent(int... effectTriggerEntities){
        this.effectTriggerEntities = effectTriggerEntities;
    }
    private int[] effectTriggerEntities;

    public int[] getEffectTriggerEntities(){
        return effectTriggerEntities;
    }
}
