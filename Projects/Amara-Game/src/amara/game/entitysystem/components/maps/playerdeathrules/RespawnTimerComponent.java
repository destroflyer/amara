/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.maps.playerdeathrules;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class RespawnTimerComponent{

    public RespawnTimerComponent(){
        
    }

    public RespawnTimerComponent(float initialDuration, float deltaDurationPerTime){
        this.initialDuration = initialDuration;
        this.deltaDurationPerTime = deltaDurationPerTime;
    }
    @ComponentField(type=ComponentField.Type.TIMER)
    private float initialDuration;
    @ComponentField(type=ComponentField.Type.TIMER)
    private float deltaDurationPerTime;

    public float getInitialDuration(){
        return initialDuration;
    }

    public float getDeltaDurationPerTime(){
        return deltaDurationPerTime;
    }
}
