/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.loginscreen.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import amara.libraries.applications.display.DisplayApplication;
import amara.libraries.applications.display.appstates.*;

/**
 *
 * @author Carl
 */
public class NiftyAppState_LoginScreen extends BaseDisplayAppState<DisplayApplication>{

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        NiftyAppState niftyAppState = getAppState(NiftyAppState.class);
        niftyAppState.createNifty("Interface/login_screen.xml");
    }
}