/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import amara.applications.ingame.network.messages.Message_ClientInitialized;
import amara.engine.applications.ingame.client.IngameClientApplication;
import amara.libraries.applications.display.appstates.*;

/**
 *
 * @author Carl
 */
public class ClientInitializedAppState extends BaseDisplayAppState<IngameClientApplication>{

    public ClientInitializedAppState(){
        
    }

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        getAppState(NetworkClientAppState.class).getNetworkClient().sendMessage(new Message_ClientInitialized());
    }
}
