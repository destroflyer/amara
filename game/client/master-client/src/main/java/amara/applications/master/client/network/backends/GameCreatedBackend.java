/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.network.backends;

import amara.applications.master.client.appstates.CurrentGameAppState;
import amara.applications.master.network.messages.Message_GameCreated;
import amara.libraries.network.MessageBackend;
import amara.libraries.network.MessageResponse;
import com.jme3.network.Message;

/**
 *
 * @author Carl
 */
public class GameCreatedBackend implements MessageBackend {

    public GameCreatedBackend(CurrentGameAppState currentGameAppState) {
        this.currentGameAppState = currentGameAppState;
    }
    private CurrentGameAppState currentGameAppState;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_GameCreated) {
            currentGameAppState.startIngameClient();
        }
    }
}
