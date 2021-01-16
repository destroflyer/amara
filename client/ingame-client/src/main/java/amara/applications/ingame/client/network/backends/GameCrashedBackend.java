package amara.applications.ingame.client.network.backends;

import amara.applications.ingame.client.IngameClientApplication;
import amara.applications.ingame.network.messages.Message_GameCrashed;
import amara.libraries.applications.windowed.dialogs.ErrorDialog;
import amara.libraries.network.MessageBackend;
import amara.libraries.network.MessageResponse;
import com.jme3.network.Message;

public class GameCrashedBackend implements MessageBackend {

    public GameCrashedBackend(IngameClientApplication ingameClientApplication) {
        this.ingameClientApplication = ingameClientApplication;
    }
    private IngameClientApplication ingameClientApplication;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_GameCrashed) {
            Message_GameCrashed message = (Message_GameCrashed) receivedMessage;
            ingameClientApplication.enqueueTask(() -> {
                ingameClientApplication.stop();
                new ErrorDialog(message.getErrorMessage());
            });
        }
    }
}
