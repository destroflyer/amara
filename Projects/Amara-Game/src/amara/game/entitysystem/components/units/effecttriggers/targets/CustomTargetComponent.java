/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units.effecttriggers.targets;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class CustomTargetComponent{

    public CustomTargetComponent(){
        
    }

    public CustomTargetComponent(int... targetEntities){
        this.targetEntities = targetEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] targetEntities;

    public int[] getTargetEntities(){
        return targetEntities;
    }
}
