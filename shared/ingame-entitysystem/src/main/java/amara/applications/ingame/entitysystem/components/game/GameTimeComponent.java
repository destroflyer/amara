/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.game;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

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
    @ComponentField(type=ComponentField.Type.TIMER)
    private float time;

    public float getTime(){
        return time;
    }
}
