/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.network.backends;

import com.jme3.network.Message;
import amara.applications.master.network.messages.Message_AddLobbyBot;
import amara.applications.master.server.appstates.LobbiesAppState;
import amara.applications.master.server.players.ConnectedPlayers;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class AddLobbyBotsBackend implements MessageBackend{

    public AddLobbyBotsBackend(LobbiesAppState lobbiesAppState, ConnectedPlayers connectedPlayers){
        this.lobbiesAppState = lobbiesAppState;
        this.connectedPlayers = connectedPlayers;
    }
    private LobbiesAppState lobbiesAppState;
    private ConnectedPlayers connectedPlayers;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_AddLobbyBot){
            Message_AddLobbyBot message = (Message_AddLobbyBot) receivedMessage;
            int lobbyPlayerID = connectedPlayers.getPlayer(messageResponse.getClientID()).getID();
            lobbiesAppState.addLobbyBot(lobbyPlayerID, message.getLobbyPlayer_Bot());
        }
    }
}
