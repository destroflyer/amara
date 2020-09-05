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
public class Message_AcceptGameSelection extends AbstractMessage{
    
    public Message_AcceptGameSelection(){
        
    }
    
    public Message_AcceptGameSelection(boolean isAccepted){
        this.isAccepted = isAccepted;
    }
    private boolean isAccepted;
    
    public boolean isAccepted(){
        return isAccepted;
    }
}
