/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.network.backends;

import com.jme3.network.Message;
import amara.applications.master.network.messages.Message_SetLobbyData;
import amara.engine.applications.masterserver.server.appstates.*;
import amara.engine.network.*;
import amara.game.players.ConnectedPlayers;

/**
 *
 * @author Carl
 */
public class SetLobbiesDataBackend implements MessageBackend{

    public SetLobbiesDataBackend(LobbiesAppState lobbiesAppState, ConnectedPlayers connectedPlayers){
        this.lobbiesAppState = lobbiesAppState;
        this.connectedPlayers = connectedPlayers;
    }
    private LobbiesAppState lobbiesAppState;
    private ConnectedPlayers connectedPlayers;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_SetLobbyData){
            Message_SetLobbyData message = (Message_SetLobbyData) receivedMessage;
            int ownerID = connectedPlayers.getPlayer(messageResponse.getClientID()).getID();
            lobbiesAppState.setLobbyData(ownerID, message.getLobbyData());
        }
    }
}
