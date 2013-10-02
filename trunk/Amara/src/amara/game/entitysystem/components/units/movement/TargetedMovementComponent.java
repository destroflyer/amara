/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units.movement;

/**
 *
 * @author Carl
 */
public class TargetedMovementComponent{

    public TargetedMovementComponent(int targetEntityID, float speed){
        this.targetEntityID = targetEntityID;
        this.speed = speed;
    }
    private int targetEntityID;
    private float speed;

    public int getTargetEntityID(){
        return targetEntityID;
    }

    public float getSpeed(){
        return speed;
    }
}
