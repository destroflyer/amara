/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.client.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import amara.engine.applications.ingame.client.appstates.NetworkClientAppState;
import amara.engine.applications.masterserver.client.network.backends.LoginResultBackend;
import amara.engine.applications.masterserver.server.protocol.AuthentificationInformation;
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
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        NetworkClient networkClient = getAppState(NetworkClientAppState.class).getNetworkClient();
        networkClient.addMessageBackend(new LoginResultBackend(mainApplication));
        networkClient.sendMessage(new Message_Login(authentificationInformation));
    }
}
