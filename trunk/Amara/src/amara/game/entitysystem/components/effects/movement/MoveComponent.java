/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.movement;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class MoveComponent{

    public MoveComponent(){
        
    }
    
    public MoveComponent(int movementEntity){
        this.movementEntity = movementEntity;
    }
    private int movementEntity;

    public int getMovementEntity(){
        return movementEntity;
    }
}
