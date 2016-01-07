/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.buffs.stacks;

import com.jme3.network.serializing.Serializable;
import amara.game.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class StacksRefreshmentComponent{

    public StacksRefreshmentComponent(){
        
    }
    
    public StacksRefreshmentComponent(float duration){
        this.duration = duration;
    }
    @ComponentField(type=ComponentField.Type.TIMER)
    private float duration;

    public float getDuration(){
        return duration;
    }
}
