/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.appstates;

import amara.engine.applications.*;
import amara.engine.applications.ingame.server.appstates.NetworkServerAppState;
import amara.engine.applications.masterserver.server.network.backends.*;
import amara.engine.network.NetworkServer;
import amara.game.players.ConnectedPlayers;

/**
 *
 * @author Carl
 */
public class PlayersAppState extends ServerBaseAppState{

    private ConnectedPlayers connectedPlayers = new ConnectedPlayers();
    
    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        DatabaseAppState databaseAppState = getAppState(DatabaseAppState.class);
        networkServer.addMessageBackend(new ReceiveLoginsBackend(databaseAppState, connectedPlayers));
        networkServer.addMessageBackend(new SendPlayerProfileDataBackend(databaseAppState));
        networkServer.addMessageBackend(new EditUserMetaBackend(databaseAppState, connectedPlayers));
    }

    public ConnectedPlayers getConnectedPlayers(){
        return connectedPlayers;
    }
}
