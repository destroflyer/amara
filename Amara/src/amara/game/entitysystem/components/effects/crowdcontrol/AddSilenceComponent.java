/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.crowdcontrol;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class AddSilenceComponent{

    public AddSilenceComponent(){
        
    }
    
    public AddSilenceComponent(float duration){
        this.duration = duration;
    }
    private float duration;

    public float getDuration(){
        return duration;
    }
}