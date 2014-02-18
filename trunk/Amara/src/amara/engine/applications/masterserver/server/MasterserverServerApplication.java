package amara.engine.applications.masterserver.server;

import java.util.concurrent.Callable;
import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import amara.engine.applications.ingame.server.appstates.*;
import amara.engine.applications.masterserver.server.appstates.*;
import amara.engine.applications.masterserver.server.network.PortProvider;
import amara.engine.network.exceptions.*;

/**
 * @author Carl
 */
public class MasterserverServerApplication extends SimpleApplication{

    public MasterserverServerApplication(int port){
        this.port = port;
        settings = new AppSettings(true);
        settings.setFrameRate(60);
    }
    private int port;

    @Override
    public void simpleInitApp(){
        try{
            stateManager.attach(new NetworkServerAppState(port));
            stateManager.attach(new PlayersAppState());
            stateManager.attach(new GamesAppState(new PortProvider(port + 1)));
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
}
