/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.network.backends;

import com.jme3.network.Message;
import amara.applications.master.network.messages.Message_KickLobbyPlayer;
import amara.engine.applications.masterserver.server.appstates.*;
import amara.game.players.ConnectedPlayers;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class KickLobbyPlayersBackend implements MessageBackend{

    public KickLobbyPlayersBackend(LobbiesAppState lobbiesAppState, ConnectedPlayers connectedPlayers){
        this.lobbiesAppState = lobbiesAppState;
        this.connectedPlayers = connectedPlayers;
    }
    private LobbiesAppState lobbiesAppState;
    private ConnectedPlayers connectedPlayers;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_KickLobbyPlayer){
            Message_KickLobbyPlayer message = (Message_KickLobbyPlayer) receivedMessage;
            int ownerID = connectedPlayers.getPlayer(messageResponse.getClientID()).getID();
            lobbiesAppState.kickLobbyPlayer(ownerID, message.getPlayerID());
        }
    }
}
