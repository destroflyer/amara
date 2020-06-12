/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.network.backends;

import amara.applications.ingame.server.appstates.ServerEntitySystemAppState;
import com.jme3.network.Message;
import amara.applications.ingame.network.messages.*;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class AddNewClientsBackend implements MessageBackend {

    public AddNewClientsBackend(ServerEntitySystemAppState serverEntitySystemAppState) {
        this.serverEntitySystemAppState = serverEntitySystemAppState;
    }
    private ServerEntitySystemAppState serverEntitySystemAppState;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_ClientInitialized) {
            serverEntitySystemAppState.addNewClient(messageResponse.getClientId());
        }
    }
}
