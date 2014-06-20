/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;

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
    private float distance;

    public float getDistance(){
        return distance;
    }
}
