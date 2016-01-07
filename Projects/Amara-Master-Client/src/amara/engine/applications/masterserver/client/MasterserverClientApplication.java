package amara.engine.applications.masterserver.client;

import amara.engine.applications.HeadlessApplication;
import amara.engine.applications.masterserver.client.appstates.*;
import amara.engine.appstates.NetworkClientHeadlessAppState;
import amara.engine.network.*;
import amara.engine.network.exceptions.*;

/**
 * @author Carl
 */
public class MasterserverClientApplication extends HeadlessApplication{

    public MasterserverClientApplication(HostInformation hostInformation) throws ServerConnectionException, ServerConnectionTimeoutException{
        instance = this;
        this.hostInformation = hostInformation;
        stateManager.attach(new NetworkClientHeadlessAppState(hostInformation.getHost(), hostInformation.getPort()));
        stateManager.attach(new PlayerProfilesAppState());
        stateManager.attach(new PlayerStatusesAppState());
        stateManager.attach(new CharactersAppState());
        stateManager.attach(new ItemsAppState());
        stateManager.attach(new LoginAppState());
    }
    private static MasterserverClientApplication instance;
    private HostInformation hostInformation;

    public HostInformation getHostInformation(){
        return hostInformation;
    }

    public static MasterserverClientApplication getInstance(){
        return instance;
    }
}
