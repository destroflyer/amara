/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.buffs;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class BuffStacksComponent{

    public BuffStacksComponent(){
        
    }
    
    public BuffStacksComponent(int stacksEntity){
        this.stacksEntity = stacksEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int stacksEntity;

    public int getStacksEntity(){
        return stacksEntity;
    }
}
