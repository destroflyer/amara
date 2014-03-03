/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units.effecttriggers;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class TriggeredEffectComponent{

    public TriggeredEffectComponent(){
        
    }

    public TriggeredEffectComponent(int sourceEntity, int effectEntity){
        this.sourceEntity = sourceEntity;
        this.effectEntity = effectEntity;
    }
    private int sourceEntity;
    private int effectEntity;

    public int getSourceEntity(){
        return sourceEntity;
    }

    public int getEffectEntity(){
        return effectEntity;
    }
}
