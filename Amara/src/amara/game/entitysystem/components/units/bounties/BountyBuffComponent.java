/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units.bounties;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class BountyBuffComponent{

    public BountyBuffComponent(){
        
    }
    
    public BountyBuffComponent(int buffEntity, float duration){
        this.buffEntity = buffEntity;
        this.duration = duration;
    }
    private int buffEntity;
    private float duration;

    public int getBuffEntity(){
        return buffEntity;
    }

    public float getDuration(){
        return duration;
    }
}
