/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.crowdcontrol;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

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
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int knockupEntity;

    public int getKnockupEntity(){
        return knockupEntity;
    }
}
