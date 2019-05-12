/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import amara.applications.ingame.client.IngameClientApplication;
import amara.applications.ingame.client.network.backends.GameInfoBackend;
import amara.applications.ingame.network.messages.Message_PlayerAuthentification;
import amara.libraries.applications.display.appstates.*;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;
import amara.libraries.network.NetworkClient;

/**
 *
 * @author Carl
 */
public class PlayerAuthentificationAppState extends BaseDisplayAppState<IngameClientApplication>{

    public PlayerAuthentificationAppState(int authentificationKey){
        this.authentificationKey = authentificationKey;
    }
    private int authentificationKey;

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        NetworkClient networkClient = mainApplication.getMasterserverClient().getState(NetworkClientHeadlessAppState.class).getNetworkClient();
        networkClient.addMessageBackend(new GameInfoBackend(mainApplication));
        networkClient.sendMessage(new Message_PlayerAuthentification(authentificationKey));
    }
}
