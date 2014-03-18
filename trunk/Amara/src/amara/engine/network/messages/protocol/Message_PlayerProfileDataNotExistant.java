/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network.messages.protocol;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_PlayerProfileDataNotExistant extends AbstractMessage{
    
    public Message_PlayerProfileDataNotExistant(){
        
    }
    
    public Message_PlayerProfileDataNotExistant(String login){
        this.login = login;
    }
    private String login;

    public String getLogin(){
        return login;
    }
}
