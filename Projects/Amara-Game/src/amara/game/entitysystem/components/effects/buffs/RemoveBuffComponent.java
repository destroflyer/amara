/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.buffs;

import com.jme3.network.serializing.Serializable;
import amara.game.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class RemoveBuffComponent{

    public RemoveBuffComponent(){
        
    }
    
    public RemoveBuffComponent(int buffEntity){
        this.buffEntity = buffEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int buffEntity;

    public int getBuffEntity(){
        return buffEntity;
    }
}
