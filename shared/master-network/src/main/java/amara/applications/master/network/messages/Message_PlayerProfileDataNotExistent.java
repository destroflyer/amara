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
public class Message_PlayerProfileDataNotExistent extends AbstractMessage {

    public Message_PlayerProfileDataNotExistent() {
        
    }

    public Message_PlayerProfileDataNotExistent(String login) {
        this.login = login;
    }
    private String login;

    public String getLogin(){
        return login;
    }
}
