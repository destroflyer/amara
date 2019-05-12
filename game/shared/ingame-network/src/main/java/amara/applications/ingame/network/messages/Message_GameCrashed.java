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
public class Message_GameCrashed extends AbstractMessage{
    
    public Message_GameCrashed(){
        
    }
    
    public Message_GameCrashed(String errorMessage){
        this.errorMessage = errorMessage;
    }
    private String errorMessage;

    public String getErrorMessage(){
        return errorMessage;
    }
}
