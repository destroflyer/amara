package amara.engine.applications.masterserver.server;

import amara.engine.applications.HeadlessApplication;
import amara.engine.applications.ingame.server.appstates.*;
import amara.engine.applications.masterserver.server.appstates.*;
import amara.engine.applications.masterserver.server.network.PortProvider;
import amara.engine.network.exceptions.*;

/**
 * @author Carl
 */
public class MasterserverServerApplication extends HeadlessApplication{

    public MasterserverServerApplication(int port){
        this.port = port;
        try{
            stateManager.attach(new DatabaseAppState());
            stateManager.attach(new NetworkServerAppState(port));
            stateManager.attach(new PlayersAppState());
            stateManager.attach(new GamesAppState(new PortProvider(port + 1)));
        }catch(ServerCreationException ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }
    private int port;
}
