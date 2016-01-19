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
public class MovementComponent{

    public MovementComponent(){
        
    }

    public MovementComponent(int movementEntity){
        this.movementEntity = movementEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int movementEntity;

    public int getMovementEntity(){
        return movementEntity;
    }
}
