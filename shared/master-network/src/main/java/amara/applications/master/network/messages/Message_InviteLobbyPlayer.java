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
public class Message_InviteLobbyPlayer extends AbstractMessage{
    
    public Message_InviteLobbyPlayer(){
        
    }
    
    public Message_InviteLobbyPlayer(int playerID){
        this.playerID = playerID;
    }
    private int playerID;

    public int getPlayerID(){
        return playerID;
    }
}
