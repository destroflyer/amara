/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network.messages.protocol;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import amara.engine.applications.masterserver.server.protocol.PlayerProfileData;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_PlayerProfileData extends AbstractMessage{
    
    public Message_PlayerProfileData(){
        
    }
    
    public Message_PlayerProfileData(int playerID, String login, PlayerProfileData playerProfileData){
        this.playerID = playerID;
        this.login = login;
        this.playerProfileData = playerProfileData;
    }
    private int playerID;
    private String login;
    private PlayerProfileData playerProfileData;

    public int getPlayerID(){
        return playerID;
    }

    public String getLogin(){
        return login;
    }

    public PlayerProfileData getPlayerProfileData(){
        return playerProfileData;
    }
}
