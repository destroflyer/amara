/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.network.backends;

import com.jme3.network.Message;
import amara.applications.ingame.client.appstates.LoadingScreenAppState;
import amara.applications.ingame.network.messages.Message_GameStarted;
import amara.libraries.applications.display.DisplayApplication;
import amara.libraries.network.*;

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
            mainApplication.enqueueTask(new Runnable(){

                @Override
                public void run(){
                    mainApplication.getStateManager().detach(mainApplication.getStateManager().getState(LoadingScreenAppState.class));
                }
            });
        }
    }
}
