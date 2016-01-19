/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class StacksReachedTriggerComponent{

    public StacksReachedTriggerComponent(){
        
    }

    public StacksReachedTriggerComponent(int stacks){
        this.stacks = stacks;
    }
    @ComponentField(type=ComponentField.Type.STACKS)
    private int stacks;

    public int getStacks(){
        return stacks;
    }
}
