/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.appstates;

import amara.applications.ingame.client.IngameClientApplication;
import amara.applications.ingame.client.interfaces.MasterserverClientInterface;
import amara.applications.master.client.MasterserverClientApplication;
import amara.applications.master.client.MasterserverClientUtil;
import amara.applications.master.client.network.backends.GameCreatedBackend;
import amara.applications.master.network.messages.objects.PlayerProfileData;
import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;
import amara.libraries.network.NetworkClient;

/**
 *
 * @author Carl
 */
public class CurrentGameAppState extends ClientBaseAppState{

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
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        NetworkClient networkClient = getAppState(NetworkClientHeadlessAppState.class).getNetworkClient();
        networkClient.addMessageBackend(new GameCreatedBackend(this));
    }

    public void startIngameClient() {
        IngameClientApplication ingameClientApplication = new IngameClientApplication(masterserverClientInterface);
        ingameClientApplication.start();
    }
}
