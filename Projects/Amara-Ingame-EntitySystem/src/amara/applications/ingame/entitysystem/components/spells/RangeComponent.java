/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.spells;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class RangeComponent{

    public RangeComponent(){
        
    }
    
    public RangeComponent(float distange){
        this.distance = distange;
    }
    @ComponentField(type=ComponentField.Type.DISTANCE)
    private float distance;

    public float getDistance(){
        return distance;
    }
}
