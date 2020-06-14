package amara.applications.master.server;

import amara.applications.ingame.server.IngameServerApplication;
import amara.applications.ingame.server.interfaces.MasterserverServerApplicationInterface;
import amara.applications.master.server.appstates.*;
import amara.applications.master.server.appstates.DatabaseAppState;
import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.*;
import amara.libraries.network.exceptions.ServerCreationException;

/**
 * @author Carl
 */
public class MasterserverServerApplication extends HeadlessApplication implements MasterserverServerApplicationInterface {

    public MasterserverServerApplication(int port) {
        this.port = port;
        try {
            stateManager.attach(new LogsAppState());
            stateManager.attach(new DatabaseAppState());
            stateManager.attach(new NetworkServerAppState(port));
            stateManager.attach(new DestrostudiosAppState());
            stateManager.attach(new PlayersContentsAppState());
            stateManager.attach(new PlayersAppState());
            stateManager.attach(new LobbiesAppState());
            stateManager.attach(new GamesQueueAppState());
            stateManager.attach(new GamesAppState());
            stateManager.attach(new MMOAppState());
            stateManager.attach(new MasterServerInitializedAppState());
        } catch(ServerCreationException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }
    private int port;

    public int getPort() {
        return port;
    }

    // Interface

    @Override
    public <T extends HeadlessAppState> T getState(Class<T> stateClass) {
        return stateManager.getState(stateClass);
    }

    @Override
    public void onGameCrashed(IngameServerApplication ingameServerApplication, Exception exception) {
        stateManager.getState(GamesAppState.class).onGameCrashed(ingameServerApplication, exception);
    }

    @Override
    public void onGameOver(IngameServerApplication ingameServerApplication) {
        stateManager.getState(GamesAppState.class).onGameOver(ingameServerApplication);
    }
}
