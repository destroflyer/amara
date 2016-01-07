/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.server.appstates;

import amara.engine.applications.HeadlessAppStateManager;
import amara.engine.applications.HeadlessApplication;
import amara.engine.applications.ingame.server.network.backends.*;
import amara.engine.appstates.NetworkServerAppState;
import amara.engine.network.NetworkServer;

/**
 *
 * @author Carl
 */
public class ServerChatAppState extends ServerBaseAppState{

    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        networkServer.addMessageBackend(new ReceiveChatMessagesBackend(mainApplication));
    }
}
