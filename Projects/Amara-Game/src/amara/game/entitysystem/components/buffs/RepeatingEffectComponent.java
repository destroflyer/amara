/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.buffs;

import com.jme3.network.serializing.Serializable;
import amara.game.entitysystem.synchronizing.ComponentField;

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
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int effectEntity;
    @ComponentField(type=ComponentField.Type.TIMER)
    private float interval;

    public int getEffectEntity(){
        return effectEntity;
    }

    public float getInterval(){
        return interval;
    }
}
