/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.game;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class GameTimeComponent{

    public GameTimeComponent(){
        
    }

    public GameTimeComponent(float time){
        this.time = time;
    }
    private float time;

    public float getTime(){
        return time;
    }
}
