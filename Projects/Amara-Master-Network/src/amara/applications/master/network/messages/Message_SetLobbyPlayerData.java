/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import amara.applications.master.network.messages.objects.LobbyPlayerData;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_SetLobbyPlayerData extends AbstractMessage{
    
    public Message_SetLobbyPlayerData(){
        
    }
    
    public Message_SetLobbyPlayerData(LobbyPlayerData lobbyPlayerData){
        this.lobbyPlayerData = lobbyPlayerData;
    }
    private LobbyPlayerData lobbyPlayerData;

    public LobbyPlayerData getLobbyPlayerData(){
        return lobbyPlayerData;
    }
}
