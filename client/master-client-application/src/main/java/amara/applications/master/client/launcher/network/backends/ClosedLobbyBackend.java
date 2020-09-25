package amara.applications.master.client.launcher.network.backends;

import com.jme3.network.Message;
import amara.applications.master.client.launcher.panels.PanPlay;
import amara.applications.master.network.messages.Message_LobbyClosed;
import amara.libraries.network.*;

public class ClosedLobbyBackend implements MessageBackend {

    public ClosedLobbyBackend(PanPlay panPlay) {
        this.panPlay = panPlay;
    }
    private PanPlay panPlay;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_LobbyClosed) {
            panPlay.displaySelectGameModePanel();
        }
    }
}
