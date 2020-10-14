package amara.applications.master.client.network.backends;

import amara.applications.master.client.appstates.CurrentGameAppState;
import amara.applications.master.client.appstates.LoginAppState;
import amara.libraries.network.MessageBackend;
import amara.libraries.network.MessageResponse;
import com.jme3.network.Message;
import amara.applications.master.network.messages.Message_LoginResult;

public class LoginResultBackend implements MessageBackend {

    public LoginResultBackend(LoginAppState loginAppState, CurrentGameAppState currentGameAppState) {
        this.loginAppState = loginAppState;
        this.currentGameAppState = currentGameAppState;
    }
    private LoginAppState loginAppState;
    private CurrentGameAppState currentGameAppState;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_LoginResult){
            Message_LoginResult message = (Message_LoginResult) receivedMessage;
            int playerId = message.getPlayerId();
            if (playerId != 0) {
                loginAppState.onLoginSuccessful(playerId);
                currentGameAppState.setIsIngame(message.isIngame());
            } else {
                loginAppState.onLoginFailed();
            }
        }
    }
}
