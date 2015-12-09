/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.spawns;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class SpawnMovementRelativeDirectionComponent{

    public SpawnMovementRelativeDirectionComponent(){
        
    }
    
    public SpawnMovementRelativeDirectionComponent(float angle_Degrees){
        this.angle_Degrees = angle_Degrees;
    }
    private float angle_Degrees;

    public float getAngle_Degrees(){
        return angle_Degrees;
    }
    
    public float getAngle_Radian(){
        return (float) Math.toRadians(angle_Degrees);
    }
}