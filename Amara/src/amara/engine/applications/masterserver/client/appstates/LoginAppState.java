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
    private AuthentificationInformation authentificationInformation;
    
    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        NetworkClient networkClient = getAppState(NetworkClientHeadlessAppState.class).getNetworkClient();
        networkClient.addMessageBackend(new LoginResultBackend(mainApplication));
        networkClient.sendMessage(new Message_Login(authentificationInformation));
    }
}
