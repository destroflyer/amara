/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class MaximumAggroRangeComponent{

    public MaximumAggroRangeComponent(){
        
    }
    @ComponentField(type=ComponentField.Type.DISTANCE)
    private float range;
    
    public MaximumAggroRangeComponent(float range){
        this.range = range;
    }

    public float getRange(){
        return range;
    }
}
