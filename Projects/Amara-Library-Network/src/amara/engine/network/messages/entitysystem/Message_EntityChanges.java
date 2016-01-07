/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network.messages.entitysystem;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_EntityChanges extends AbstractMessage{
    
    public Message_EntityChanges(){
        
    }
    
    public Message_EntityChanges(byte[] data){
        this.data = data;
    }
    private byte[] data;

    public byte[] getData(){
        return data;
    }
}
