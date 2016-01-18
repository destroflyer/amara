/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.client.appstates;

import amara.engine.applications.masterserver.client.network.backends.*;
import amara.engine.network.NetworkClient;
import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;

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
    private int playerID;
    
    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        NetworkClient networkClient = getAppState(NetworkClientHeadlessAppState.class).getNetworkClient();
        networkClient.addMessageBackend(new LoginResultBackend(mainApplication, this));
    }
    
    public void onLoginPending(){
        result = LoginResult.PENDING;
    }

    public void onLoginSuccessful(int playerID){
        this.playerID = playerID;
        result = LoginResult.SUCCESSFUL;
    }

    public void onLoginFailed(){
        result = LoginResult.FAILED;
    }

    public LoginResult getResult(){
        return result;
    }

    public int getPlayerID(){
        return playerID;
    }
}
