package amara.applications.master.client.network.backends;

import amara.applications.ingame.network.messages.Message_GameCrashed;
import amara.applications.ingame.network.messages.Message_GameOver;
import amara.applications.master.client.appstates.CurrentGameAppState;
import amara.libraries.network.MessageBackend;
import amara.libraries.network.MessageResponse;
import com.jme3.network.Message;

public class GameEndedBackend implements MessageBackend {

    public GameEndedBackend(CurrentGameAppState currentGameAppState) {
        this.currentGameAppState = currentGameAppState;
    }
    private CurrentGameAppState currentGameAppState;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if ((receivedMessage instanceof Message_GameOver)
         || (receivedMessage instanceof Message_GameCrashed)) {
            currentGameAppState.setIsIngame(false);
        }
    }
}
