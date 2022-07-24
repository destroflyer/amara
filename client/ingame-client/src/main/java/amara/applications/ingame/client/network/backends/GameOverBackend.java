package amara.applications.ingame.client.network.backends;

import amara.applications.ingame.client.IngameClientApplication;
import com.jme3.network.Message;
import amara.applications.ingame.client.appstates.*;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.network.messages.Message_GameOver;
import amara.libraries.applications.display.appstates.*;
import amara.libraries.applications.display.ingame.appstates.IngameCameraAppState;
import amara.libraries.network.*;

public class GameOverBackend implements MessageBackend {

    public GameOverBackend(IngameClientApplication ingameClientApplication) {
        this.ingameClientApplication = ingameClientApplication;
    }
    private IngameClientApplication ingameClientApplication;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_GameOver) {
            ingameClientApplication.enqueue(() -> {
                ingameClientApplication.getStateManager().detach(ingameClientApplication.getStateManager().getState(PingAppState.class));
                ingameClientApplication.getStateManager().detach(ingameClientApplication.getStateManager().getState(SendPlayerCommandsAppState.class));
                ingameClientApplication.getStateManager().detach(ingameClientApplication.getStateManager().getState(LocalEntitySystemAppState.class));
                ingameClientApplication.getStateManager().detach(ingameClientApplication.getStateManager().getState(SynchronizeEntityWorldAppState.class));
                ingameClientApplication.getStateManager().detach(ingameClientApplication.getStateManager().getState(PlayerAppState.class));
                ingameClientApplication.getStateManager().getState(IngameCameraAppState.class).setEnabled(false);
                NiftyAppState niftyAppState = ingameClientApplication.getStateManager().getState(NiftyAppState.class);
                niftyAppState.goToScreen(ScreenController_HUD.class, "gameOver");
                ingameClientApplication.getStateManager().getState(AudioAppState.class).createAudioNode("Audio/sounds/victory.ogg").play();
            });
        }
    }
}
