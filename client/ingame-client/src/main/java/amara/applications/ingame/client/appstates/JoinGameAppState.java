package amara.applications.ingame.client.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import amara.applications.ingame.client.IngameClientApplication;
import amara.applications.ingame.client.network.backends.ReceiveGameInfoBackend;
import amara.applications.ingame.network.messages.Message_JoinGame;
import amara.libraries.applications.display.appstates.*;

public class JoinGameAppState extends BaseDisplayAppState<IngameClientApplication> {

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);
        IngameNetworkAppState ingameNetworkAppState = getAppState(IngameNetworkAppState.class);
        ingameNetworkAppState.addMessageBackend(new ReceiveGameInfoBackend(mainApplication));
        ingameNetworkAppState.sendMessage(new Message_JoinGame());
    }
}
