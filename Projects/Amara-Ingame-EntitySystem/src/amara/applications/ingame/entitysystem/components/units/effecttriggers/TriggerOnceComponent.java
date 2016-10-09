/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.units.effecttriggers;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class TriggerOnceComponent{

    public TriggerOnceComponent(){
        
    }

    public TriggerOnceComponent(boolean removeEntity){
        this.removeEntity = removeEntity;
    }
    private boolean removeEntity;

    public boolean isRemoveEntity(){
        return removeEntity;
    }
}
