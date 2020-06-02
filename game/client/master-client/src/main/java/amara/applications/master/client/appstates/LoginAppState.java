/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.appstates;

import amara.applications.master.client.network.backends.LoginResultBackend;
import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;
import amara.libraries.network.NetworkClient;

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
    private LoginResult result;
    private int playerId;
    
    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        NetworkClient networkClient = getAppState(NetworkClientHeadlessAppState.class).getNetworkClient();
        networkClient.addMessageBackend(new LoginResultBackend(mainApplication, this));
    }
    
    public void onLoginPending(){
        result = LoginResult.PENDING;
    }

    public void onLoginSuccessful(int playerId){
        this.playerId = playerId;
        result = LoginResult.SUCCESSFUL;
    }

    public void onLoginFailed(){
        result = LoginResult.FAILED;
    }

    public LoginResult getResult(){
        return result;
    }

    public int getPlayerId(){
        return playerId;
    }
}
