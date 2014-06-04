/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.client.appstates;

import amara.engine.applications.*;
import amara.engine.applications.masterserver.client.network.backends.*;
import amara.engine.appstates.NetworkClientHeadlessAppState;
import amara.engine.network.NetworkClient;

/**
 *
 * @author Carl
 */
public class LoginAppState extends ClientBaseAppState{

    public LoginAppState(){
        
    }
    public enum LoginResult{
        PENDING,
        FAILED,
        SUCCESSFUL
    }
    private LoginResult result = LoginResult.PENDING;
    
    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        NetworkClient networkClient = getAppState(NetworkClientHeadlessAppState.class).getNetworkClient();
        networkClient.addMessageBackend(new LoginResultBackend(mainApplication, this));
    }

    public void setResult(LoginResult result){
        this.result = result;
    }

    public LoginResult getResult(){
        return result;
    }
}
