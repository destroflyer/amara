/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class AggroPriorityComponent{

    public AggroPriorityComponent(){
        
    }

    public AggroPriorityComponent(int priority){
        this.priority = priority;
    }
    private int priority;

    public int getPriority(){
        return priority;
    }
}
