/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.network.backends;

import com.jme3.network.Message;
import amara.applications.master.network.messages.Message_Logout;
import amara.applications.master.server.players.*;
import amara.libraries.network.*;
import amara.libraries.network.messages.Message_ClientDisconnection;

/**
 *
 * @author Carl
 */
public class ReceiveLogoutsBackend implements MessageBackend {

    public ReceiveLogoutsBackend(ConnectedPlayers connectedPlayers) {
        this.connectedPlayers = connectedPlayers;
    }
    private ConnectedPlayers connectedPlayers;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_Logout) {
            logout(messageResponse.getClientId());
        } else if (receivedMessage instanceof Message_ClientDisconnection) {
            logout(messageResponse.getClientId());
        }
    }

    private void logout(int clientId) {
        Player player = connectedPlayers.getPlayer(clientId);
        if (player != null) {
            connectedPlayers.logout(clientId);
            System.out.println("Logout '" + player.getLogin() + "' (#" + player.getID() + ")");
        }
    }
}
