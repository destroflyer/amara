/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;
import amara.game.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Philipp
 */
@Serializable
public class TargetsInAggroRangeComponent{

    public TargetsInAggroRangeComponent(){
        
    }
    
    public TargetsInAggroRangeComponent(int[] targetEntities){
        this.targetEntities = targetEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] targetEntities;

    public int[] getTargetEntities(){
        return targetEntities;
    }
}
