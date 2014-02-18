/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import amara.engine.applications.ingame.server.appstates.NetworkServerAppState;
import amara.engine.applications.masterserver.server.network.backends.ReceiveLoginsBackend;
import amara.engine.network.NetworkServer;
import amara.game.players.ConnectedPlayers;

/**
 *
 * @author Carl
 */
public class PlayersAppState extends ServerBaseAppState{

    private ConnectedPlayers connectedPlayers = new ConnectedPlayers();
    
    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        networkServer.addMessageBackend(new ReceiveLoginsBackend(connectedPlayers));
    }

    public ConnectedPlayers getConnectedPlayers(){
        return connectedPlayers;
    }
}
