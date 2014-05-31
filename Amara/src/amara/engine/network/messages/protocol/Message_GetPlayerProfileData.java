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
public class Message_GetPlayerProfileData extends AbstractMessage{
    
    public Message_GetPlayerProfileData(){
        
    }
    
    public Message_GetPlayerProfileData(int playerID, String login, long cachedTimestamp){
        this.playerID = playerID;
        this.login = login;
        this.cachedTimestamp = cachedTimestamp;
    }
    private int playerID;
    private String login;
    private long cachedTimestamp;

    public int getPlayerID(){
        return playerID;
    }

    public String getLogin(){
        return login;
    }

    public long getCachedTimestamp(){
        return cachedTimestamp;
    }
}
