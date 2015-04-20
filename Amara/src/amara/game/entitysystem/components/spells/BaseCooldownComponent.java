/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.spells;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class BaseCooldownComponent{

    public BaseCooldownComponent(){
        
    }
    
    public BaseCooldownComponent(float duration){
        this.duration = duration;
    }    
    private float duration;

    public float getDuration(){
        return duration;
    }
}
