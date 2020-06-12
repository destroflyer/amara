/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.network.backends;

import amara.applications.master.network.messages.Message_SetGameSelectionPlayerData;
import com.jme3.network.Message;
import amara.applications.master.server.appstates.GamesQueueAppState;
import amara.applications.master.server.players.ConnectedPlayers;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class SetGameSelectionsPlayerDataBackend implements MessageBackend{

    public SetGameSelectionsPlayerDataBackend(GamesQueueAppState gamesQueueAppState, ConnectedPlayers connectedPlayers){
        this.gamesQueueAppState = gamesQueueAppState;
        this.connectedPlayers = connectedPlayers;
    }
    private GamesQueueAppState gamesQueueAppState;
    private ConnectedPlayers connectedPlayers;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_SetGameSelectionPlayerData){
            Message_SetGameSelectionPlayerData message = (Message_SetGameSelectionPlayerData) receivedMessage;
            int playerId = connectedPlayers.getPlayer(messageResponse.getClientId()).getID();
            gamesQueueAppState.setGameSelectionPlayerData(playerId, message.getGameSelectionPlayerData());
        }
    }
}
