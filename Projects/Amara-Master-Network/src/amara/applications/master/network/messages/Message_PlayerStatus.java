/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import amara.applications.master.network.messages.objects.PlayerStatus;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_PlayerStatus extends AbstractMessage{
    
    public Message_PlayerStatus(){
        
    }
    
    public Message_PlayerStatus(int playerID, PlayerStatus playerStatus){
        this.playerID = playerID;
        this.playerStatus = playerStatus;
    }
    private int playerID;
    private PlayerStatus playerStatus;

    public int getPlayerID(){
        return playerID;
    }
    
    public PlayerStatus getPlayerStatus(){
        return playerStatus;
    }
}
