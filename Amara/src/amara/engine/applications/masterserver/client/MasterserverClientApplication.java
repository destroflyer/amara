package amara.engine.applications.masterserver.client;

import amara.engine.applications.HeadlessApplication;
import amara.engine.applications.masterserver.client.appstates.*;
import amara.engine.applications.masterserver.server.protocol.AuthentificationInformation;
import amara.engine.appstates.NetworkClientHeadlessAppState;
import amara.engine.network.*;
import amara.engine.network.exceptions.*;

/**
 * @author Carl
 */
public class MasterserverClientApplication extends HeadlessApplication{

    public MasterserverClientApplication(HostInformation hostInformation, AuthentificationInformation authentificationInformation){
        this.hostInformation = hostInformation;
        this.authentificationInformation = authentificationInformation;
        try{
            stateManager.attach(new NetworkClientHeadlessAppState(hostInformation.getHost(), hostInformation.getPort()));
        }catch(ServerConnectionException ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }catch(ServerConnectionTimeoutException ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        stateManager.attach(new LoginAppState(authentificationInformation));
    }
    private HostInformation hostInformation;
    private AuthentificationInformation authentificationInformation;

    public HostInformation getHostInformation(){
        return hostInformation;
    }
}
