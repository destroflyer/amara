/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import amara.applications.master.network.messages.objects.Lobby;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_LobbyUpdate extends AbstractMessage{
    
    public Message_LobbyUpdate(){
        
    }
    
    public Message_LobbyUpdate(Lobby lobby){
        this.lobby = lobby;
    }
    private Lobby lobby;

    public Lobby getLobby(){
        return lobby;
    }
}
