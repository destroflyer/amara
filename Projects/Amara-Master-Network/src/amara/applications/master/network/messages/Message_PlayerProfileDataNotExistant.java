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
public class Message_PlayerProfileDataNotExistant extends AbstractMessage{
    
    public Message_PlayerProfileDataNotExistant(){
        
    }
    
    public Message_PlayerProfileDataNotExistant(int playerID, String login){
        this.playerID = playerID;
        this.login = login;
    }
    private int playerID;
    private String login;

    public int getPlayerID(){
        return playerID;
    }

    public String getLogin(){
        return login;
    }
}
