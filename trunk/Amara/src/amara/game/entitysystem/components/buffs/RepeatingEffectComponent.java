/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.buffs;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class RepeatingEffectComponent{

    public RepeatingEffectComponent(){
        
    }
    
    public RepeatingEffectComponent(int effectEntity, float interval){
        this.effectEntity = effectEntity;
        this.interval = interval;
    }
    private int effectEntity;
    private float interval;

    public int getEffectEntity(){
        return effectEntity;
    }

    public float getInterval(){
        return interval;
    }
}
