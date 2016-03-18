/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.network.backends;

import com.jme3.network.Message;
import amara.applications.ingame.client.appstates.LoadingScreenAppState;
import amara.applications.ingame.network.messages.Message_GameStarted;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class GameStartedBackend implements MessageBackend{

    public GameStartedBackend(LoadingScreenAppState loadingScreenAppState){
        this.loadingScreenAppState = loadingScreenAppState;
    }
    private LoadingScreenAppState loadingScreenAppState;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_GameStarted){
            loadingScreenAppState.onGameStarted();
        }
    }
}
