package amara.engine.applications.ingame.server;

import java.util.concurrent.Callable;
import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import amara.engine.applications.ingame.server.appstates.*;
import amara.engine.applications.masterserver.server.MasterserverServerApplication;
import amara.engine.network.exceptions.*;
import amara.game.games.Game;

/**
 * @author Carl
 */
public class IngameServerApplication extends SimpleApplication{

    public IngameServerApplication(MasterserverServerApplication masterServer, Game game){
        this.masterServer = masterServer;
        this.game = game;
        settings = new AppSettings(true);
        settings.setFrameRate(60);
    }
    private MasterserverServerApplication masterServer;
    private Game game;

    @Override
    public void simpleInitApp(){
        try{
            stateManager.attach(new NetworkServerAppState(game.getPort()));
            stateManager.attach(new ReceiveCommandsAppState());
            stateManager.attach(new ServerEntitySystemAppState());
            stateManager.attach(new ServerInitializedAppState());
        }catch(ServerCreationException ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }
    
    @Override
    public void simpleUpdate(float lastTimePerFrame){
        
    }
    
    public void enqueueTask(final Runnable runnable){
        enqueue(new Callable<Object>(){

            @Override
            public Object call(){
                runnable.run();
                return null;
            }
        });
    }

    public MasterserverServerApplication getMasterServer(){
        return masterServer;
    }

    public Game getGame(){
        return game;
    }
}
