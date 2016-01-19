/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.game;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class GameSpeedComponent{

    public GameSpeedComponent(){
        
    }

    public GameSpeedComponent(float speed){
        this.speed = speed;
    }
    private float speed;

    public float getSpeed(){
        return speed;
    }
}
