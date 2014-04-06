/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.movements;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class DistanceLimitComponent{

    public DistanceLimitComponent(){
        
    }

    public DistanceLimitComponent(float distance){
        this.distance = distance;
    }
    private float distance;

    public float getDistance(){
        return distance;
    }
}
