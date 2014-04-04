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
public class MovementComponent{

    public MovementComponent(){
        
    }

    public MovementComponent(int movementEntity){
        this.movementEntity = movementEntity;
    }
    private int movementEntity;

    public int getMovementEntity(){
        return movementEntity;
    }
}
