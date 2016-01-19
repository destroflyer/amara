package amara.engine.applications.masterserver.server;

import amara.engine.applications.ingame.server.IngameServerApplication;
import amara.engine.applications.ingame.server.interfaces.MasterserverServerApplicationInterface;
import amara.engine.applications.masterserver.server.appstates.*;
import amara.engine.applications.masterserver.server.network.PortProvider;
import amara.engine.network.exceptions.*;
import amara.game.games.Game;
import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.*;

/**
 * @author Carl
 */
public class MasterserverServerApplication extends HeadlessApplication implements MasterserverServerApplicationInterface{

    public MasterserverServerApplication(int port){
        this.port = port;
        try{
            stateManager.attach(new LogsAppState());
            stateManager.attach(new DatabaseAppState());
            stateManager.attach(new NetworkServerAppState(port));
            stateManager.attach(new UpdatesAppState());
            stateManager.attach(new PlayersContentsAppState());
            stateManager.attach(new PlayersAppState());
            stateManager.attach(new LobbiesAppState());
            stateManager.attach(new GamesAppState(new PortProvider(port + 1)));
            stateManager.attach(new MasterServerInitializedAppState());
        }catch(ServerCreationException ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }
    private int port;

    public int getPort(){
        return port;
    }
    
    //Interface
    @Override
    public <T extends HeadlessAppState> T getState(Class<T> stateClass){
        return stateManager.getState(stateClass);
    }

    @Override
    public void onGameServerInitialized(Game game){
        stateManager.getState(GamesAppState.class).onGameServerInitialized(game);
    }

    @Override
    public void onGameCrashed(IngameServerApplication ingameServerApplication, Exception exception){
        stateManager.getState(GamesAppState.class).onGameCrashed(ingameServerApplication, exception);
    }

    @Override
    public void onGameOver(IngameServerApplication ingameServerApplication){
        stateManager.getState(GamesAppState.class).onGameOver(ingameServerApplication);
    }
}
