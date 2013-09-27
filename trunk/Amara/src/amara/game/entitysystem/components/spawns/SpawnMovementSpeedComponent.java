/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.spawns;

/**
 *
 * @author Carl
 */
public class SpawnMovementSpeedComponent{
    
    public SpawnMovementSpeedComponent(float speed){
        this.speed = speed;
    }
    private float speed;

    public float getSpeed(){
        return speed;
    }
}