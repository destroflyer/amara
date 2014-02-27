/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units.movement;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class TargetedMovementComponent{

    public TargetedMovementComponent(){
        
    }
    
    public TargetedMovementComponent(int targetEntity, float speed){
        this.targetEntity = targetEntity;
        this.speed = speed;
    }
    private int targetEntity;
    private float speed;

    public int getTargetEntity(){
        return targetEntity;
    }

    public float getSpeed(){
        return speed;
    }
}
