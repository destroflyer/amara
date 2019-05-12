/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.network.backends;

import com.jme3.network.Message;
import amara.applications.master.network.messages.Message_AcceptGameSelection;
import amara.applications.master.server.appstates.*;
import amara.applications.master.server.players.ConnectedPlayers;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class AcceptGameSelectionsBackend implements MessageBackend{

    public AcceptGameSelectionsBackend(GamesQueueAppState gamesQueueAppState, ConnectedPlayers connectedPlayers){
        this.gamesQueueAppState = gamesQueueAppState;
        this.connectedPlayers = connectedPlayers;
    }
    private GamesQueueAppState gamesQueueAppState;
    private ConnectedPlayers connectedPlayers;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_AcceptGameSelection){
            Message_AcceptGameSelection message = (Message_AcceptGameSelection) receivedMessage;
            int playerID = connectedPlayers.getPlayer(messageResponse.getClientID()).getID();
            gamesQueueAppState.acceptGameSelection(playerID, message.isAccepted());
        }
    }
}
