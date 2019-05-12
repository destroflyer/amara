/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.appstates;

import amara.applications.ingame.server.IngameServerApplication;
import amara.libraries.applications.headless.applications.HeadlessAppState;
import amara.libraries.applications.headless.appstates.BaseHeadlessAppState;

/**
 *
 * @author Carl
 */
public class ServerBaseAppState extends BaseHeadlessAppState<IngameServerApplication>{

    public ServerBaseAppState(){
        
    }
    
    protected <T extends HeadlessAppState> T getMasterAppState(Class<T> appStateClass){
        return mainApplication.getMasterServer().getState(appStateClass);
    }
}
