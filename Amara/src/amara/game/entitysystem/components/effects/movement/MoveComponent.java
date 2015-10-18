/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.movement;

import com.jme3.network.serializing.Serializable;
import amara.game.entitysystem.synchronizing.ComponentField;

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
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int movementEntity;

    public int getMovementEntity(){
        return movementEntity;
    }
}
