/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class WalkStepDistanceComponent{

    public WalkStepDistanceComponent(){
        
    }

    public WalkStepDistanceComponent(float distance){
        this.distance = distance;
    }
    @ComponentField(type=ComponentField.Type.DISTANCE)
    private float distance;

    public float getDistance(){
        return distance;
    }
}
