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
public class NewComponentChange extends EntityChange{

    public NewComponentChange(){
        
    }
    
    public NewComponentChange(int entityID, Object component){
        super(entityID);
        this.component = component;
    }
    private Object component;

    public Object getComponent(){
        return component;
    }
}
