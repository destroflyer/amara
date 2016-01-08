/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.network.backends;

import com.jme3.network.Message;
import amara.engine.applications.masterserver.server.appstates.*;
import amara.engine.applications.masterserver.server.network.messages.*;
import amara.engine.network.*;
import amara.game.players.ConnectedPlayers;

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
            int lobbyPlayerID = connectedPlayers.getPlayer(messageResponse.getClientID()).getID();
            lobbiesAppState.addLobbyPlayer(lobbyPlayerID, message.getPlayerID());
        }
    }
}
