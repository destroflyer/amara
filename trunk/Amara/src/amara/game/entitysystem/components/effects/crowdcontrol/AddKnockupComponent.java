/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.crowdcontrol;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class AddKnockupComponent{

    public AddKnockupComponent(){
        
    }
    
    public AddKnockupComponent(int knockupEntity){
        this.knockupEntity = knockupEntity;
    }
    private int knockupEntity;

    public int getKnockupEntity(){
        return knockupEntity;
    }
}
