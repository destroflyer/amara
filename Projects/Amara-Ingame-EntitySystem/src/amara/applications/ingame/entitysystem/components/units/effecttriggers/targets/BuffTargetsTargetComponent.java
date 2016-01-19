/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.units.effecttriggers.targets;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class BuffTargetsTargetComponent{

    public BuffTargetsTargetComponent(){
        
    }

    public BuffTargetsTargetComponent(int buffEntity){
        this.buffEntity = buffEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int buffEntity;

    public int getBuffEntity(){
        return buffEntity;
    }
}
