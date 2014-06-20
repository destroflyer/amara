/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.client.network.backends;

import com.jme3.network.Message;
import amara.engine.applications.masterserver.client.MasterserverClientApplication;
import amara.engine.applications.masterserver.client.appstates.*;
import amara.engine.network.*;
import amara.engine.network.messages.protocol.Message_LoginResult;

/**
 *
 * @author Carl
 */
public class LoginResultBackend implements MessageBackend{

    public LoginResultBackend(MasterserverClientApplication mainApplication, LoginAppState loginAppState){
        this.mainApplication = mainApplication;
        this.loginAppState = loginAppState;
    }
    private MasterserverClientApplication mainApplication;
    private LoginAppState loginAppState;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_LoginResult){
            Message_LoginResult message = (Message_LoginResult) receivedMessage;
            int playerID = message.getPlayerID();
            if(playerID != 0){
                loginAppState.onLoginSuccessful(playerID);
                mainApplication.getStateManager().attach(new CurrentGameAppState());
            }
            else{
                loginAppState.onLoginFailed();
            }
        }
    }
}
