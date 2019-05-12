/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.appstates;

import amara.applications.master.server.network.backends.ReceiveLogoutsBackend;
import amara.applications.master.server.players.ConnectedPlayers;
import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.NetworkServerAppState;
import amara.libraries.network.NetworkServer;

/**
 *
 * @author Carl
 */
public class MasterServerInitializedAppState extends ServerBaseAppState{
    
    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        ConnectedPlayers connectedPlayers = getAppState(PlayersAppState.class).getConnectedPlayers();
        //Needs to be added after the other backends that want to react to player logouts using the ConnectedPlayers link
        networkServer.addMessageBackend(new ReceiveLogoutsBackend(connectedPlayers));
        System.out.println("Masterserver initialized.");
    }
}
