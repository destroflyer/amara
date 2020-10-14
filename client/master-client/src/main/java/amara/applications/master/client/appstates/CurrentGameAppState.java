package amara.applications.master.client.appstates;

import amara.applications.ingame.client.IngameClientApplication;
import amara.applications.ingame.client.interfaces.MasterserverClientInterface;
import amara.applications.master.client.MasterserverClientApplication;
import amara.applications.master.client.MasterserverClientUtil;
import amara.applications.master.client.network.backends.GameCreatedBackend;
import amara.applications.master.client.network.backends.GameEndedBackend;
import amara.applications.master.network.messages.objects.PlayerProfileData;
import amara.libraries.applications.headless.applications.HeadlessAppState;
import amara.libraries.applications.headless.applications.HeadlessAppStateManager;
import amara.libraries.applications.headless.applications.HeadlessApplication;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;
import amara.libraries.network.NetworkClient;

public class CurrentGameAppState extends ClientBaseAppState {

    private boolean isIngame;
    private MasterserverClientInterface masterserverClientInterface = new MasterserverClientInterface() {

        @Override
        public <T extends HeadlessAppState> T getState(Class<T> stateClass) {
            return MasterserverClientApplication.getInstance().getStateManager().getState(stateClass);
        }

        @Override
        public int getPlayerId() {
            return MasterserverClientUtil.getPlayerId();
        }

        @Override
        public PlayerProfileData getPlayerProfile(int playerId) {
            return MasterserverClientUtil.getPlayerProfile(playerId);
        }
    };

    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application) {
        super.initialize(stateManager, application);
        NetworkClient networkClient = getAppState(NetworkClientHeadlessAppState.class).getNetworkClient();
        networkClient.addMessageBackend(new GameCreatedBackend(this));
        networkClient.addMessageBackend(new GameEndedBackend(this));
    }

    public void startIngameClient() {
        System.out.println("Starting ingame client.");
        IngameClientApplication ingameClientApplication = new IngameClientApplication(masterserverClientInterface);
        ingameClientApplication.start();
    }

    public void setIsIngame(boolean isIngame) {
        this.isIngame = isIngame;
    }

    public boolean isIngame() {
        return isIngame;
    }
}
