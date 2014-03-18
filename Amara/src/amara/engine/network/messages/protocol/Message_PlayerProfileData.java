/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network.messages.protocol;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import amara.launcher.client.protocol.PlayerProfileData;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_PlayerProfileData extends AbstractMessage{
    
    public Message_PlayerProfileData(){
        
    }
    
    public Message_PlayerProfileData(String login, PlayerProfileData playerProfileData){
        this.login = login;
        this.playerProfileData = playerProfileData;
    }
    private String login;
    private PlayerProfileData playerProfileData;

    public String getLogin(){
        return login;
    }

    public PlayerProfileData getPlayerProfileData(){
        return playerProfileData;
    }
}
