/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.effects.buffs.areas;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

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
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int buffAreaEntity;

    public int getBuffAreaEntity(){
        return buffAreaEntity;
    }
}
