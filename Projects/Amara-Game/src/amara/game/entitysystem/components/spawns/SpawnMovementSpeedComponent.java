/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.spawns;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class SpawnMovementSpeedComponent{

    public SpawnMovementSpeedComponent(){
        
    }
    
    public SpawnMovementSpeedComponent(float speed){
        this.speed = speed;
    }
    @ComponentField(type=ComponentField.Type.DISTANCE)
    private float speed;

    public float getSpeed(){
        return speed;
    }
}