/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.network.backends;

import amara.applications.ingame.network.messages.Message_LeaveGame;
import amara.applications.ingame.server.appstates.ServerEntitySystemAppState;
import amara.libraries.network.MessageBackend;
import amara.libraries.network.MessageResponse;
import amara.libraries.network.messages.Message_ClientDisconnection;
import com.jme3.network.Message;

/**
 *
 * @author Carl
 */
public class RemoveLeavingClientsBackend implements MessageBackend {

    public RemoveLeavingClientsBackend(ServerEntitySystemAppState serverEntitySystemAppState) {
        this.serverEntitySystemAppState = serverEntitySystemAppState;
    }
    private ServerEntitySystemAppState serverEntitySystemAppState;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if ((receivedMessage instanceof Message_ClientDisconnection)
         || (receivedMessage instanceof Message_LeaveGame)) {
            serverEntitySystemAppState.removeInitializedClient(messageResponse.getClientId());
        }
    }
}
