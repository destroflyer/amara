/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.conditions;

import com.jme3.network.serializing.Serializable;
import amara.game.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class HasBuffConditionComponent{

    public HasBuffConditionComponent(){
        
    }

    public HasBuffConditionComponent(int... buffEntities){
        this.buffEntities = buffEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] buffEntities;

    public int[] getBuffEntities(){
        return buffEntities;
    }
}
