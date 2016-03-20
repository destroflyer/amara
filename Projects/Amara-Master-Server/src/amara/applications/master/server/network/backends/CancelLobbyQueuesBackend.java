/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.network.backends;

import com.jme3.network.Message;
import amara.applications.master.network.messages.Message_CancelLobbyQueue;
import amara.applications.master.network.messages.objects.Lobby;
import amara.applications.master.server.appstates.*;
import amara.applications.master.server.players.ConnectedPlayers;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class CancelLobbyQueuesBackend implements MessageBackend{

    public CancelLobbyQueuesBackend(LobbiesAppState lobbiesAppState, GamesQueueAppState gamesQueueAppState, ConnectedPlayers connectedPlayers){
        this.lobbiesAppState = lobbiesAppState;
        this.gamesQueueAppState = gamesQueueAppState;
        this.connectedPlayers = connectedPlayers;
    }
    private LobbiesAppState lobbiesAppState;
    private GamesQueueAppState gamesQueueAppState;
    private ConnectedPlayers connectedPlayers;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_CancelLobbyQueue){
            int playerID = connectedPlayers.getPlayer(messageResponse.getClientID()).getID();
            Lobby lobby = lobbiesAppState.getLobby(playerID);
            if(lobby != null){
                gamesQueueAppState.cancelLobbyQueue(lobby);
            }
        }
    }
}
