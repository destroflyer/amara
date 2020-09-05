/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.appstates;

import amara.applications.ingame.maps.MMOMaps;
import amara.applications.master.server.games.MMOGame;
import amara.applications.master.server.network.backends.JoinMMOMapsBackend;
import amara.libraries.applications.headless.applications.HeadlessAppStateManager;
import amara.libraries.applications.headless.applications.HeadlessApplication;
import amara.libraries.applications.headless.appstates.NetworkServerAppState;
import amara.libraries.network.NetworkServer;

/**
 *
 * @author Carl
 */
public class MMOAppState extends ServerBaseAppState {

    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application) {
        super.initialize(stateManager, application);
        PlayersAppState playersAppState = getAppState(PlayersAppState.class);
        GamesAppState gamesAppState = getAppState(GamesAppState.class);

        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        networkServer.addMessageBackend(new JoinMMOMapsBackend(gamesAppState.getRunningGames(), playersAppState.getConnectedPlayers()));

        for (String mmoMapName : MMOMaps.MAP_NAMES) {
            gamesAppState.startGame(new MMOGame(mmoMapName));
        }
    }
}
