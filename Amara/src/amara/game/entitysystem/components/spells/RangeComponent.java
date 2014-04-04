/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.spells;

import com.jme3.network.serializing.Serializable;

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
    private float distance;

    public float getDistance(){
        return distance;
    }
}
