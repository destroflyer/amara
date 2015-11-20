/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import amara.engine.appstates.*;

/**
 *
 * @author Carl
 */
public class NiftyAppState_IngameClient extends BaseDisplayAppState{

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        NiftyAppState niftyAppState = getAppState(NiftyAppState.class);
        niftyAppState.createNifty("Interface/hud.xml");
        niftyAppState.createNifty("Interface/chat.xml", true);
        niftyAppState.createNifty("Interface/menu.xml");
        niftyAppState.createNifty("Interface/loading_screen.xml");
    }
}
