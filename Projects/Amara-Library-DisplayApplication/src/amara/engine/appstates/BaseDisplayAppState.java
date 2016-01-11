/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import amara.engine.applications.DisplayApplication;

/**
 *
 * @author Carl
 */
public class BaseDisplayAppState<E extends DisplayApplication> extends AbstractAppState{

    public BaseDisplayAppState(){
        
    }
    protected E mainApplication;

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        mainApplication = (E) application;
    }
    
    protected <T extends AppState> T getAppState(Class<T> appStateClass){
        return mainApplication.getStateManager().getState(appStateClass);
    }
}