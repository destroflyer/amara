package amara.applications.master.client.appstates;

import amara.applications.master.client.network.backends.LoginResultBackend;
import amara.libraries.applications.headless.applications.HeadlessAppStateManager;
import amara.libraries.applications.headless.applications.HeadlessApplication;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;
import amara.libraries.network.NetworkClient;

public class LoginAppState extends ClientBaseAppState {

    public enum LoginResult {
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
        CurrentGameAppState currentGameAppState = getAppState(CurrentGameAppState.class);
        networkClient.addMessageBackend(new LoginResultBackend(this, currentGameAppState));
    }

    public void onLoginPending() {
        result = LoginResult.PENDING;
    }

    public void onLoginSuccessful(int playerId) {
        this.playerId = playerId;
        result = LoginResult.SUCCESSFUL;
    }

    public void onLoginFailed() {
        result = LoginResult.FAILED;
    }

    public LoginResult getResult() {
        return result;
    }

    public int getPlayerId() {
        return playerId;
    }
}
