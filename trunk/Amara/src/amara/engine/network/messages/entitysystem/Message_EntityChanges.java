/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network.messages.entitysystem;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import amara.game.entitysystem.synchronizing.EntityChange;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_EntityChanges extends AbstractMessage{
    
    public Message_EntityChanges(){
        
    }
    
    public Message_EntityChanges(EntityChange[] entityChanges){
        this.entityChanges = entityChanges;
    }
    private EntityChange[] entityChanges;

    public EntityChange[] getEntityChanges(){
        return entityChanges;
    }
}
