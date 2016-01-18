/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.conditions;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class OrConditionsComponent{

    public OrConditionsComponent(){
        
    }

    public OrConditionsComponent(int... conditionEntities){
        this.conditionEntities = conditionEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] conditionEntities;

    public int[] getConditionEntities(){
        return conditionEntities;
    }
}
