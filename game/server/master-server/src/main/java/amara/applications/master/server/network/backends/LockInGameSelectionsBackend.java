/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.network.backends;

import com.jme3.network.Message;
import amara.applications.master.network.messages.Message_LockInGameSelection;
import amara.applications.master.server.appstates.*;
import amara.applications.master.server.players.ConnectedPlayers;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class LockInGameSelectionsBackend implements MessageBackend{

    public LockInGameSelectionsBackend(GamesQueueAppState gamesQueueAppState, ConnectedPlayers connectedPlayers){
        this.gamesQueueAppState = gamesQueueAppState;
        this.connectedPlayers = connectedPlayers;
    }
    private GamesQueueAppState gamesQueueAppState;
    private ConnectedPlayers connectedPlayers;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_LockInGameSelection){
            int playerID = connectedPlayers.getPlayer(messageResponse.getClientID()).getID();
            gamesQueueAppState.lockInGameSelection(playerID);
        }
    }
}
