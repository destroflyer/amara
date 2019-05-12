/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.visuals.animations;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

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
    @ComponentField(type=ComponentField.Type.TIMER)
    private float passedTime;

    public float getPassedTime(){
        return passedTime;
    }
}
