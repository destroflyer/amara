package amara.applications.ingame.server.network.backends;

import amara.applications.ingame.network.messages.Message_LeaveGame;
import amara.applications.ingame.server.appstates.IngamePlayersAppState;
import amara.libraries.network.MessageBackend;
import amara.libraries.network.MessageResponse;
import amara.libraries.network.messages.Message_ClientDisconnection;
import com.jme3.network.Message;

public class RemoveLeavingClientsBackend implements MessageBackend {

    public RemoveLeavingClientsBackend(IngamePlayersAppState ingamePlayersAppState) {
        this.ingamePlayersAppState = ingamePlayersAppState;
    }
    private IngamePlayersAppState ingamePlayersAppState;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if ((receivedMessage instanceof Message_ClientDisconnection)
         || (receivedMessage instanceof Message_LeaveGame)) {
            ingamePlayersAppState.addRemovedClient(messageResponse.getClientId());
        }
    }
}
