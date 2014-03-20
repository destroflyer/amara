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
        LoginAppState loginAppState = new LoginAppState(authentificationInformation);
        try{
            stateManager.attach(new NetworkClientHeadlessAppState(hostInformation.getHost(), hostInformation.getPort()));
            stateManager.attach(new PlayerProfilesAppState());
            stateManager.attach(new PlayerStatusesAppState());
        }catch(ServerConnectionException ex){
            System.out.println(ex.getMessage());
            loginAppState.setResult(LoginAppState.LoginResult.NO_CONNECTION_TO_MASTERSERVER);
        }catch(ServerConnectionTimeoutException ex){
            System.out.println(ex.getMessage());
            loginAppState.setResult(LoginAppState.LoginResult.NO_CONNECTION_TO_MASTERSERVER);
        }
        stateManager.attach(loginAppState);
    }
    private HostInformation hostInformation;
    private AuthentificationInformation authentificationInformation;

    public HostInformation getHostInformation(){
        return hostInformation;
    }

    public AuthentificationInformation getAuthentificationInformation(){
        return authentificationInformation;
    }
}
