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
public class Message_LobbyQueueStatus extends AbstractMessage{
    
    public Message_LobbyQueueStatus(){
        
    }
    
    public Message_LobbyQueueStatus(boolean isQueueing){
        this.isQueueing = isQueueing;
    }
    private boolean isQueueing;

    public boolean isQueueing(){
        return isQueueing;
    }
}
