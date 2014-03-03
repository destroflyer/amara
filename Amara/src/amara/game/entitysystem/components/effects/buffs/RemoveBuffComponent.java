/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.buffs;

import com.jme3.network.serializing.Serializable;

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
    private int buffEntity;

    public int getBuffEntity(){
        return buffEntity;
    }
}
