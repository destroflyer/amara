/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units.effecttriggers;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class TriggerConditionsComponent{

    public TriggerConditionsComponent(){
        
    }

    public TriggerConditionsComponent(int... conditionEntities){
        this.conditionEntities = conditionEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] conditionEntities;

    public int[] getConditionEntities(){
        return conditionEntities;
    }
}
