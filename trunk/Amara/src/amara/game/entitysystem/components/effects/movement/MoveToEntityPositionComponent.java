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
public class MoveToEntityPositionComponent{

    public MoveToEntityPositionComponent(){
        
    }
    
    public MoveToEntityPositionComponent(int targetPositionEntity, float speed){
        this.targetPositionEntity = targetPositionEntity;
        this.speed = speed;
    }
    private int targetPositionEntity;
    private float speed;

    public int getTargetPositionEntity(){
        return targetPositionEntity;
    }

    public float getSpeed(){
        return speed;
    }
}
