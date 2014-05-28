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
public class AddBuffAreaComponent{

    public AddBuffAreaComponent(){
        
    }
    
    public AddBuffAreaComponent(int buffAreaEntity){
        this.buffAreaEntity = buffAreaEntity;
    }
    private int buffAreaEntity;

    public int getBuffAreaEntity(){
        return buffAreaEntity;
    }
}
