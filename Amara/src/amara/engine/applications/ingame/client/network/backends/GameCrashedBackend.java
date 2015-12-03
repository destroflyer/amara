/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.network.backends;

import com.jme3.network.Message;
import amara.engine.appstates.*;
import amara.engine.applications.DisplayApplication;
import amara.engine.network.*;
import amara.engine.network.messages.Message_GameCrashed;
import amara.launcher.client.ErrorDialog;

/**
 *
 * @author Carl
 */
public class GameCrashedBackend implements MessageBackend{

    public GameCrashedBackend(DisplayApplication mainApplication){
        this.mainApplication = mainApplication;
    }
    private DisplayApplication mainApplication;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_GameCrashed){
            final Message_GameCrashed message = (Message_GameCrashed) receivedMessage;
            mainApplication.enqueueTask(new Runnable(){

                @Override
                public void run(){
                    mainApplication.getStateManager().getState(NetworkClientAppState.class).getNetworkClient().disconnect();
                    mainApplication.stop();
                    ErrorDialog.show(message.getErrorMessage());
                }
            });
        }
    }
}
