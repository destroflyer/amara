/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.appstates;

import amara.engine.applications.*;

/**
 *
 * @author Carl
 */
public class BaseHeadlessAppState<T extends HeadlessApplication> extends HeadlessAppState{

    public BaseHeadlessAppState(){
        
    }
    protected T mainApplication;

    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        mainApplication = (T) application;
    }
    
    protected <T extends HeadlessAppState> T getAppState(Class<T> appStateClass){
        return mainApplication.getStateManager().getState(appStateClass);
    }
}
