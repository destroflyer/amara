/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units.effecttriggers.triggers;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class RepeatingTriggerCounterComponent{

    public RepeatingTriggerCounterComponent(){
        
    }

    public RepeatingTriggerCounterComponent(int counter){
        this.counter = counter;
    }
    private int counter;

    public int getCounter(){
        return counter;
    }
}
