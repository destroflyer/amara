/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.buffs.status;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class ActiveBuffComponent{

    public ActiveBuffComponent(){
        
    }
    
    public ActiveBuffComponent(int targetEntity, int buffEntity){
        this.targetEntity = targetEntity;
        this.buffEntity = buffEntity;
    }
    private int targetEntity;
    private int buffEntity;

    public int getTargetEntity(){
        return targetEntity;
    }

    public int getBuffEntity(){
        return buffEntity;
    }
}
