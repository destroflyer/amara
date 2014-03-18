/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.client.appstates;

import amara.engine.applications.*;
import amara.engine.applications.masterserver.client.network.backends.LoginResultBackend;
import amara.engine.applications.masterserver.server.protocol.AuthentificationInformation;
import amara.engine.appstates.NetworkClientHeadlessAppState;
import amara.engine.network.NetworkClient;
import amara.engine.network.messages.protocol.Message_Login;

/**
 *
 * @author Carl
 */
public class LoginAppState extends ClientBaseAppState{

    public LoginAppState(AuthentificationInformation authentificationInformation){
        this.authentificationInformation = authentificationInformation;
    }
    public enum LoginResult{
        PENDING,
        NO_CONNECTION_TO_MASTERSERVER,
        FAILED,
        SUCCESSFUL
    }
    private AuthentificationInformation authentificationInformation;
    private LoginResult result = LoginResult.PENDING;
    
    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        if(result != LoginResult.NO_CONNECTION_TO_MASTERSERVER){
            NetworkClient networkClient = getAppState(NetworkClientHeadlessAppState.class).getNetworkClient();
            networkClient.addMessageBackend(new LoginResultBackend(mainApplication, this));
            networkClient.sendMessage(new Message_Login(authentificationInformation));
        }
    }

    public void setResult(LoginResult result){
        this.result = result;
    }

    public LoginResult getResult(){
        return result;
    }
}
