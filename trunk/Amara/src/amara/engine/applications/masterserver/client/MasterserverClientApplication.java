package amara.engine.applications.masterserver.client;

import java.util.concurrent.Callable;
import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import amara.engine.applications.ingame.client.appstates.*;
import amara.engine.applications.masterserver.client.appstates.*;
import amara.engine.applications.masterserver.server.protocol.AuthentificationInformation;
import amara.engine.network.*;
import amara.engine.network.exceptions.*;

/**
 * @author Carl
 */
public class MasterserverClientApplication extends SimpleApplication{

    public MasterserverClientApplication(HostInformation hostInformation, AuthentificationInformation authentificationInformation){
        this.hostInformation = hostInformation;
        this.authentificationInformation = authentificationInformation;
        settings = new AppSettings(true);
        settings.setFrameRate(60);
    }
    private HostInformation hostInformation;
    private AuthentificationInformation authentificationInformation;

    @Override
    public void simpleInitApp(){
        try{
            stateManager.attach(new NetworkClientAppState(hostInformation.getHost(), hostInformation.getPort()));
            stateManager.attach(new LoginAppState(authentificationInformation));
        }catch(ServerConnectionException ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }catch(ServerConnectionTimeoutException ex){
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

    public HostInformation getHostInformation(){
        return hostInformation;
    }
}
