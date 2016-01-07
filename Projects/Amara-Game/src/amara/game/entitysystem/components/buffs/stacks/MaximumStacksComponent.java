/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.buffs.stacks;

import com.jme3.network.serializing.Serializable;
import amara.game.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class MaximumStacksComponent{

    public MaximumStacksComponent(){
        
    }
    
    public MaximumStacksComponent(int stacks){
        this.stacks = stacks;
    }
    @ComponentField(type=ComponentField.Type.STACKS)
    private int stacks;

    public int getStacks(){
        return stacks;
    }
}
