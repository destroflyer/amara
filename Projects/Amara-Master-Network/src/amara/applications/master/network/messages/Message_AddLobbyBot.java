/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.network.messages;

import amara.applications.master.network.messages.objects.LobbyPlayer_Bot;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_AddLobbyBot extends AbstractMessage{
    
    public Message_AddLobbyBot(){
        
    }
    
    public Message_AddLobbyBot(LobbyPlayer_Bot lobbyPlayer_Bot){
        this.lobbyPlayer_Bot = lobbyPlayer_Bot;
    }
    private LobbyPlayer_Bot lobbyPlayer_Bot;

    public LobbyPlayer_Bot getLobbyPlayer_Bot() {
        return lobbyPlayer_Bot;
    }
}
