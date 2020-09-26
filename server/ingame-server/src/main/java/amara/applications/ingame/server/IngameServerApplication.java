package amara.applications.ingame.server;

import amara.applications.ingame.server.appstates.*;
import amara.applications.ingame.server.interfaces.MasterserverServerApplicationInterface;
import amara.applications.master.server.games.Game;
import amara.libraries.applications.headless.applications.HeadlessApplication;
import amara.libraries.applications.headless.appstates.SubNetworkServerAppState;
import amara.libraries.network.SubNetworkServer;

public class IngameServerApplication extends HeadlessApplication {

    public IngameServerApplication(MasterserverServerApplicationInterface masterServer, SubNetworkServer subNetworkServer, Game game) {
        this.masterServer = masterServer;
        this.game = game;
        stateManager.attach(new SubNetworkServerAppState(subNetworkServer));
        stateManager.attach(new PongAppState());
        stateManager.attach(new ReceiveCommandsAppState());
        stateManager.attach(new BotsAppState());
        stateManager.attach(new IngamePlayersAppState());
        stateManager.attach(new ServerEntitySystemAppState());
        stateManager.attach(new ServerChatAppState());
        stateManager.attach(new IngameServerInitializedAppState());
    }
    private MasterserverServerApplicationInterface masterServer;
    private Game game;

    @Override
    public void update(float lastTimePerFrame) {
        try {
            super.update(lastTimePerFrame);
        } catch (Exception ex) {
            masterServer.onGameCrashed(this, ex);
        }
    }

    public void onGameOver() {
        masterServer.onGameOver(this);
    }

    public MasterserverServerApplicationInterface getMasterServer() {
        return masterServer;
    }

    public Game getGame() {
        return game;
    }
}
