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
    
    public Message_PlayerStatus(int playerId, PlayerStatus playerStatus){
        this.playerId = playerId;
        this.playerStatus = playerStatus;
    }
    private int playerId;
    private PlayerStatus playerStatus;

    public int getPlayerId(){
        return playerId;
    }
    
    public PlayerStatus getPlayerStatus(){
        return playerStatus;
    }
}
