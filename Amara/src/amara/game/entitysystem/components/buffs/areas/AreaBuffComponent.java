/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.buffs.areas;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class AreaBuffComponent{

    public AreaBuffComponent(){
        
    }

    public AreaBuffComponent(int buffEntity){
        this.buffEntity = buffEntity;
    }
    private int buffEntity;

    public int getBuffEntity(){
        return buffEntity;
    }
}
