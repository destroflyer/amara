package amara.applications.master.client;

import amara.applications.master.client.appstates.*;
import amara.libraries.applications.headless.applications.HeadlessApplication;
import amara.libraries.applications.headless.appstates.*;
import amara.libraries.network.HostInformation;
import amara.libraries.network.exceptions.*;

public class MasterserverClientApplication extends HeadlessApplication {

    public MasterserverClientApplication(HostInformation hostInformation) throws ServerConnectionException, ServerConnectionTimeoutException {
        instance = this;
        stateManager.attach(new NetworkClientHeadlessAppState(hostInformation.getHost(), hostInformation.getPort()));
        stateManager.attach(new PlayerProfilesAppState());
        stateManager.attach(new PlayerStatiAppState());
        stateManager.attach(new CharactersAppState());
        stateManager.attach(new MapsAppState());
        stateManager.attach(new CurrentGameAppState());
        stateManager.attach(new LoginAppState());
    }
    private static MasterserverClientApplication instance;

    public static MasterserverClientApplication getInstance() {
        return instance;
    }
}
