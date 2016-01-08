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
public class SetLobbiesPlayerDataBackend implements MessageBackend{

    public SetLobbiesPlayerDataBackend(LobbiesAppState lobbiesAppState, ConnectedPlayers connectedPlayers){
        this.lobbiesAppState = lobbiesAppState;
        this.connectedPlayers = connectedPlayers;
    }
    private LobbiesAppState lobbiesAppState;
    private ConnectedPlayers connectedPlayers;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_SetLobbyPlayerData){
            Message_SetLobbyPlayerData message = (Message_SetLobbyPlayerData) receivedMessage;
            int playerID = connectedPlayers.getPlayer(messageResponse.getClientID()).getID();
            lobbiesAppState.setPlayerData(playerID, message.getLobbyPlayerData());
        }
    }
}
