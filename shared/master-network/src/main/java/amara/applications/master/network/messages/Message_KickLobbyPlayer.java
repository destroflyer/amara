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
public class Message_KickLobbyPlayer extends AbstractMessage{
    
    public Message_KickLobbyPlayer(){
        
    }
    
    public Message_KickLobbyPlayer(int lobbyPlayerID){
        this.lobbyPlayerID = lobbyPlayerID;
    }
    private int lobbyPlayerID;

    public int getLobbyPlayerID() {
        return lobbyPlayerID;
    }
}
