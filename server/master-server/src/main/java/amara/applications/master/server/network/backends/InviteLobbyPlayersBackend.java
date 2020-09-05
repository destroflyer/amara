/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.network.backends;

import com.jme3.network.Message;
import amara.applications.master.network.messages.Message_InviteLobbyPlayer;
import amara.applications.master.server.appstates.LobbiesAppState;
import amara.applications.master.server.players.ConnectedPlayers;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class InviteLobbyPlayersBackend implements MessageBackend{

    public InviteLobbyPlayersBackend(LobbiesAppState lobbiesAppState, ConnectedPlayers connectedPlayers){
        this.lobbiesAppState = lobbiesAppState;
        this.connectedPlayers = connectedPlayers;
    }
    private LobbiesAppState lobbiesAppState;
    private ConnectedPlayers connectedPlayers;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_InviteLobbyPlayer){
            Message_InviteLobbyPlayer message = (Message_InviteLobbyPlayer) receivedMessage;
            int lobbyPlayerID = connectedPlayers.getPlayer(messageResponse.getClientId()).getID();
            lobbiesAppState.addLobbyPlayer(lobbyPlayerID, message.getPlayerID());
        }
    }
}
