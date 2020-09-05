/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.appstates;

import amara.applications.ingame.server.network.backends.ReceivePingsBackend;
import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.SubNetworkServerAppState;
import amara.libraries.network.SubNetworkServer;

/**
 *
 * @author Carl
 */
public class PongAppState extends ServerBaseAppState {
    
    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application) {
        super.initialize(stateManager, application);
        SubNetworkServer subNetworkServer = getAppState(SubNetworkServerAppState.class).getSubNetworkServer();
        subNetworkServer.addMessageBackend(new ReceivePingsBackend());
    }
}
