/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.visuals.animations;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class PassedLoopTimeComponent{

    public PassedLoopTimeComponent(){
        
    }

    public PassedLoopTimeComponent(float passedTime){
        this.passedTime = passedTime;
    }
    private float passedTime;

    public float getPassedTime(){
        return passedTime;
    }
}
