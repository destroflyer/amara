/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_Pong extends AbstractMessage{
    
    public Message_Pong(){
        
    }
    
    public Message_Pong(long timestamp){
        this.timestamp = timestamp;
    }
    private long timestamp;

    public long getTimestamp(){
        return timestamp;
    }

    @Override
    public boolean isReliable(){
        return false;
    }
}
