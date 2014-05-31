/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import amara.engine.applications.masterserver.server.protocol.LobbyData;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_CreateLobby extends AbstractMessage{
    
    public Message_CreateLobby(){
        
    }
    
    public Message_CreateLobby(LobbyData lobbyData){
        this.lobbyData = lobbyData;
    }
    private LobbyData lobbyData;

    public LobbyData getLobbyData(){
        return lobbyData;
    }
}
