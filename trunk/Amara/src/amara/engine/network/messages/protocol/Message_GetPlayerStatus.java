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
public class Message_GetPlayerStatus extends AbstractMessage{
    
    public Message_GetPlayerStatus(){
        
    }
    
    public Message_GetPlayerStatus(int playerID){
        this.playerID = playerID;
    }
    private int playerID;

    public int getPlayerID(){
        return playerID;
    }
}
