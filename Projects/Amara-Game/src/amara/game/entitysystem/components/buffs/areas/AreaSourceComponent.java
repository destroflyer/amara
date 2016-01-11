/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.buffs.areas;

import com.jme3.network.serializing.Serializable;
import amara.game.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class AreaSourceComponent{

    public AreaSourceComponent(){
        
    }

    public AreaSourceComponent(int sourceEntity){
        this.sourceEntity = sourceEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int sourceEntity;

    public int getSourceEntity(){
        return sourceEntity;
    }
}