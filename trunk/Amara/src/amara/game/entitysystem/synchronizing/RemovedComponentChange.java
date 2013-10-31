/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.synchronizing;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class RemovedComponentChange extends EntityChange{

    public RemovedComponentChange(){
        
    }
    
    public RemovedComponentChange(int entityID, String componentClassName){
        super(entityID);
        this.componentClassName = componentClassName;
    }
    private String componentClassName;

    public String getComponentClassName(){
        return componentClassName;
    }
}
