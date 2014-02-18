/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import amara.engine.network.messages.Message_PlayerAuthentification;

/**
 *
 * @author Carl
 */
public class PlayerAuthentificationAppState extends ClientBaseAppState{

    public PlayerAuthentificationAppState(int authentificationKey){
        this.authentificationKey = authentificationKey;
    }
    private int authentificationKey;

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        getAppState(NetworkClientAppState.class).getNetworkClient().sendMessage(new Message_PlayerAuthentification(authentificationKey));
    }
}
