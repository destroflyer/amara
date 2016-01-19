/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.network.backends;

import com.jme3.network.Message;
import amara.applications.ingame.network.messages.Message_GameCrashed;
import amara.libraries.applications.display.DisplayApplication;
import amara.libraries.applications.display.appstates.NetworkClientAppState;
import amara.libraries.applications.windowed.dialogs.ErrorDialog;
import amara.libraries.network.*;

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
