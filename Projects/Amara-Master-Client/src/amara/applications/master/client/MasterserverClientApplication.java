package amara.applications.master.client;

import amara.applications.master.client.appstates.*;
import amara.libraries.applications.headless.applications.HeadlessApplication;
import amara.libraries.applications.headless.appstates.*;
import amara.libraries.network.HostInformation;
import amara.libraries.network.exceptions.*;

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

    public static MasterserverClientApplication getInstance(){
        return instance;
    }
}
