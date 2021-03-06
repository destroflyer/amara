/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.network.backends;

import com.jme3.network.Message;
import amara.applications.master.network.messages.Message_LeaveLobby;
import amara.applications.master.server.appstates.LobbiesAppState;
import amara.applications.master.server.players.*;
import amara.libraries.network.*;
import amara.libraries.network.messages.Message_ClientDisconnection;

/**
 *
 * @author Carl
 */
public class LeaveLobbiesBackend implements MessageBackend {

    public LeaveLobbiesBackend(LobbiesAppState lobbiesAppState, ConnectedPlayers connectedPlayers) {
        this.lobbiesAppState = lobbiesAppState;
        this.connectedPlayers = connectedPlayers;
    }
    private LobbiesAppState lobbiesAppState;
    private ConnectedPlayers connectedPlayers;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_LeaveLobby) {
            leaveLobby(messageResponse.getClientId());
        } else if (receivedMessage instanceof Message_ClientDisconnection) {
            leaveLobby(messageResponse.getClientId());
        }
    }

    private void leaveLobby(int clientId) {
        Player player = connectedPlayers.getPlayer(clientId);
        if (player != null) {
            lobbiesAppState.leaveLobby(player.getID());
        }
    }
}
