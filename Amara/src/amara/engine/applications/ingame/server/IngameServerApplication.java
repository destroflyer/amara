package amara.engine.applications.ingame.server;

import amara.engine.applications.HeadlessApplication;
import amara.engine.applications.ingame.server.appstates.*;
import amara.engine.applications.masterserver.server.MasterserverServerApplication;
import amara.engine.applications.masterserver.server.appstates.GamesAppState;
import amara.engine.network.exceptions.*;
import amara.game.games.Game;

/**
 * @author Carl
 */
public class IngameServerApplication extends HeadlessApplication{

    public IngameServerApplication(MasterserverServerApplication masterServer, Game game) throws ServerCreationException{
        this.masterServer = masterServer;
        this.game = game;
        stateManager.attach(new NetworkServerAppState(game.getPort()));
        stateManager.attach(new PongAppState());
        stateManager.attach(new ReceiveCommandsAppState());
        stateManager.attach(new ServerEntitySystemAppState());
        stateManager.attach(new ServerChatAppState());
        stateManager.attach(new ServerInitializedAppState());
    }
    private MasterserverServerApplication masterServer;
    private Game game;

    @Override
    public void update(float lastTimePerFrame){
        try{
            super.update(lastTimePerFrame);
        }catch(Exception ex){
            GamesAppState gamesAppState = masterServer.getStateManager().getState(GamesAppState.class);
            gamesAppState.onGameCrashed(this, ex);
        }
    }

    public MasterserverServerApplication getMasterServer(){
        return masterServer;
    }

    public Game getGame(){
        return game;
    }
}
