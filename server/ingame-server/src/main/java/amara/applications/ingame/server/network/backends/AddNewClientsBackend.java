package amara.applications.ingame.server.network.backends;

import amara.applications.ingame.network.messages.Message_ClientInitialized;
import amara.applications.ingame.server.appstates.IngamePlayersAppState;
import amara.libraries.network.MessageBackend;
import amara.libraries.network.MessageResponse;
import com.jme3.network.Message;

public class AddNewClientsBackend implements MessageBackend {

    public AddNewClientsBackend(IngamePlayersAppState ingamePlayersAppState) {
        this.ingamePlayersAppState = ingamePlayersAppState;
    }
    private IngamePlayersAppState ingamePlayersAppState;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_ClientInitialized) {
            ingamePlayersAppState.addNewClient(messageResponse.getClientId());
        }
    }
}
