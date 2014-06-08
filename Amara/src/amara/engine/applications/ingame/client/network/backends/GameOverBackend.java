/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.network.backends;

import com.jme3.network.Message;
import amara.engine.appstates.*;
import amara.engine.applications.DisplayApplication;
import amara.engine.applications.ingame.client.appstates.*;
import amara.engine.applications.ingame.client.gui.ScreenController_HUD;
import amara.engine.network.*;
import amara.engine.network.messages.Message_GameOver;

/**
 *
 * @author Carl
 */
public class GameOverBackend implements MessageBackend{

    public GameOverBackend(DisplayApplication mainApplication){
        this.mainApplication = mainApplication;
    }
    private DisplayApplication mainApplication;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_GameOver){
            Message_GameOver message = (Message_GameOver) receivedMessage;
            mainApplication.enqueueTask(new Runnable(){

                @Override
                public void run(){
                    mainApplication.getStateManager().detach(mainApplication.getStateManager().getState(SendPlayerCommandsAppState.class));
                    mainApplication.getStateManager().detach(mainApplication.getStateManager().getState(LocalEntitySystemAppState.class));
                    mainApplication.getStateManager().getState(IngameCameraAppState.class).setEnabled(false);
                    NiftyAppState niftyAppState = mainApplication.getStateManager().getState(NiftyAppState.class);
                    niftyAppState.goToScreen(ScreenController_HUD.class, "gameOver");
                    mainApplication.getStateManager().getState(AudioAppState.class).createAudioNode("Sounds/sounds/victory.ogg").play();
                    mainApplication.getStateManager().getState(NetworkClientAppState.class).getNetworkClient().disconnect();
                }
            });
        }
    }
}
