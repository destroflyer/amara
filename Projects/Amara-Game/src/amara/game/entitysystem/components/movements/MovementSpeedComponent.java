/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.movements;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class MovementSpeedComponent{

    public MovementSpeedComponent(){
        
    }

    public MovementSpeedComponent(float speed){
        this.speed = speed;
    }
    @ComponentField(type=ComponentField.Type.DISTANCE)
    private float speed;

    public float getSpeed(){
        return speed;
    }
}
