/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.client.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import amara.engine.applications.ingame.client.appstates.NetworkClientAppState;
import amara.engine.applications.masterserver.client.network.backends.GameStartedBackend;
import amara.engine.network.NetworkClient;

/**
 *
 * @author Carl
 */
public class CurrentGameAppState extends ClientBaseAppState{

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        NetworkClient networkClient = getAppState(NetworkClientAppState.class).getNetworkClient();
        networkClient.addMessageBackend(new GameStartedBackend(mainApplication.getHostInformation()));
    }
}
