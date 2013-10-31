package amara.engine.server;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;
import amara.engine.network.MessagesSerializer;
import amara.engine.network.exceptions.*;
import amara.engine.server.appstates.*;

/**
 * @author Carl
 */
public class ServerApplication extends SimpleApplication{

    public static void main(String[] args){
        Logger.getLogger("").setLevel(Level.SEVERE);
        ServerApplication app = new ServerApplication();
        app.start(JmeContext.Type.Headless);
    }

    public ServerApplication(){
        MessagesSerializer.registerClasses();
        settings = new AppSettings(true);
        settings.setFrameRate(60);
        try{
            stateManager.attach(new NetworkServerAppState(33900));
            stateManager.attach(new ReceiveCommandsAppState());
            stateManager.attach(new ServerEntitySystemAppState());
        }catch(ServerCreationException ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }

    @Override
    public void simpleInitApp(){
        
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
