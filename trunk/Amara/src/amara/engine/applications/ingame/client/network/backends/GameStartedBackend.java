/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.network.backends;

import com.jme3.network.Message;
import amara.engine.appstates.*;
import amara.engine.applications.DisplayApplication;
import amara.engine.applications.ingame.client.gui.ScreenController_LoadingScreen;
import amara.engine.network.*;
import amara.engine.network.messages.Message_GameStarted;

/**
 *
 * @author Carl
 */
public class GameStartedBackend implements MessageBackend{

    public GameStartedBackend(DisplayApplication mainApplication){
        this.mainApplication = mainApplication;
    }
    private DisplayApplication mainApplication;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_GameStarted){
            Message_GameStarted message = (Message_GameStarted) receivedMessage;
            NiftyAppState niftyAppState = mainApplication.getStateManager().getState(NiftyAppState.class);
            niftyAppState.goToScreen(ScreenController_LoadingScreen.class, "ingame");
            mainApplication.getStateManager().getState(IngameCameraAppState.class).setEnabled(true);
        }
    }
}
