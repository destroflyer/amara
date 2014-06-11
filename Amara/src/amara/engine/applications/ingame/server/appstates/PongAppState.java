/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.server.appstates;

import amara.engine.applications.*;
import amara.engine.applications.ingame.server.network.backends.*;
import amara.engine.network.NetworkServer;

/**
 *
 * @author Carl
 */
public class PongAppState extends ServerBaseAppState{

    public PongAppState(){
        
    }
    
    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        networkServer.addMessageBackend(new ReceivePingsBackend());
    }
}
