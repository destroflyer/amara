package amara.applications.master.client.appstates;

import amara.applications.master.client.network.backends.ReceiveAvailableMapsBackend;
import amara.libraries.applications.headless.applications.HeadlessAppStateManager;
import amara.libraries.applications.headless.applications.HeadlessApplication;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;
import amara.libraries.network.NetworkClient;

public class MapsAppState extends ClientBaseAppState {

    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application) {
        super.initialize(stateManager, application);
        NetworkClient networkClient = getAppState(NetworkClientHeadlessAppState.class).getNetworkClient();
        networkClient.addMessageBackend(new ReceiveAvailableMapsBackend(this));
    }
    private String[] mapNames;

    public void setMapNames(String[] mapNames) {
        this.mapNames = mapNames;
    }

    public String[] getMapNames() {
        return mapNames;
    }
}
