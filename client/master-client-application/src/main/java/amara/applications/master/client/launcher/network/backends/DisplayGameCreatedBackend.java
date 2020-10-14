package amara.applications.master.client.launcher.network.backends;

import amara.libraries.network.MessageBackend;
import amara.libraries.network.MessageResponse;
import com.jme3.network.Message;
import amara.applications.master.client.launcher.panels.PanPlay;
import amara.applications.master.network.messages.Message_GameCreated;

public class DisplayGameCreatedBackend implements MessageBackend {

    public DisplayGameCreatedBackend(PanPlay panPlay) {
        this.panPlay = panPlay;
    }
    private PanPlay panPlay;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_GameCreated) {
            panPlay.displayIngamePanel();
        }
    }
}
