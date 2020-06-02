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
public class Message_Login extends AbstractMessage{
    
    public Message_Login(){
        
    }
    
    public Message_Login(String authToken){
        this.authToken = authToken;
    }
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }
}
