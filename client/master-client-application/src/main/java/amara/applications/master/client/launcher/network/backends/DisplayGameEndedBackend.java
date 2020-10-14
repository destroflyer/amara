package amara.applications.master.client.launcher.network.backends;

import amara.applications.ingame.network.messages.Message_GameCrashed;
import amara.applications.ingame.network.messages.Message_GameOver;
import amara.libraries.network.MessageBackend;
import amara.libraries.network.MessageResponse;
import com.jme3.network.Message;
import amara.applications.master.client.launcher.panels.PanPlay;

public class DisplayGameEndedBackend implements MessageBackend {

    public DisplayGameEndedBackend(PanPlay panPlay) {
        this.panPlay = panPlay;
    }
    private PanPlay panPlay;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if ((receivedMessage instanceof Message_GameOver)
         || (receivedMessage instanceof Message_GameCrashed)) {
            panPlay.displaySelectGameModePanel();
        }
    }
}
