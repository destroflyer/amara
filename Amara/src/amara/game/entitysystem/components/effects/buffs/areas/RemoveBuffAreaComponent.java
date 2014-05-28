/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.buffs.areas;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class RemoveBuffAreaComponent{

    public RemoveBuffAreaComponent(){
        
    }
    
    public RemoveBuffAreaComponent(int buffAreaEntity){
        this.buffAreaEntity = buffAreaEntity;
    }
    private int buffAreaEntity;

    public int getBuffAreaEntity(){
        return buffAreaEntity;
    }
}
