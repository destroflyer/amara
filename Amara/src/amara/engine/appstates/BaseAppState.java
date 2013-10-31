/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.SimpleApplication;

/**
 *
 * @author Carl
 */
public class BaseAppState<T extends SimpleApplication> extends AbstractAppState{

    public BaseAppState(){
        
    }
    protected T mainApplication;

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        mainApplication = (T) application;
    }
    
    protected <T extends AppState> T getAppState(Class<T> appStateClass){
        return mainApplication.getStateManager().getState(appStateClass);
    }
}
