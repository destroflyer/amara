/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.network.backends;

import com.jme3.network.Message;
import amara.applications.master.client.MasterserverClientApplication;
import amara.applications.master.client.appstates.*;
import amara.applications.master.network.messages.Message_LoginResult;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class LoginResultBackend implements MessageBackend {

    public LoginResultBackend(MasterserverClientApplication mainApplication, LoginAppState loginAppState) {
        this.mainApplication = mainApplication;
        this.loginAppState = loginAppState;
    }
    private MasterserverClientApplication mainApplication;
    private LoginAppState loginAppState;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_LoginResult){
            Message_LoginResult message = (Message_LoginResult) receivedMessage;
            int playerId = message.getPlayerId();
            if (playerId != 0) {
                loginAppState.onLoginSuccessful(playerId);
                mainApplication.getStateManager().attach(new CurrentGameAppState());
            } else {
                loginAppState.onLoginFailed();
            }
        }
    }
}
