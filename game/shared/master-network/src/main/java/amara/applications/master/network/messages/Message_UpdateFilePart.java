/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_UpdateFilePart extends AbstractMessage{
    
    public Message_UpdateFilePart(){
        
    }
    
    public Message_UpdateFilePart(byte[] data){
        this.data = data;
    }
    private byte[] data;

    public byte[] getData(){
        return data;
    }
}
