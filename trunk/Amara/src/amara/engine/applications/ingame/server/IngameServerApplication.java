package amara.engine.applications.ingame.server;

import amara.engine.applications.HeadlessApplication;
import amara.engine.applications.ingame.server.appstates.*;
import amara.engine.applications.masterserver.server.MasterserverServerApplication;
import amara.engine.network.exceptions.*;
import amara.game.games.Game;

/**
 * @author Carl
 */
public class IngameServerApplication extends HeadlessApplication{

    public IngameServerApplication(MasterserverServerApplication masterServer, Game game){
        this.masterServer = masterServer;
        this.game = game;
        try{
            stateManager.attach(new NetworkServerAppState(game.getPort()));
        }catch(ServerCreationException ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        stateManager.attach(new ReceiveCommandsAppState());
        stateManager.attach(new ServerEntitySystemAppState());
        stateManager.attach(new ServerInitializedAppState());
    }
    private MasterserverServerApplication masterServer;
    private Game game;

    public MasterserverServerApplication getMasterServer(){
        return masterServer;
    }

    public Game getGame(){
        return game;
    }
}
