/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.tools.editors.map.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import amara.engine.appstates.*;
import amara.tools.editors.map.MapEditorApplication;

/**
 *
 * @author Carl
 */
public class NiftyAppState_MapEditor extends BaseDisplayAppState<MapEditorApplication>{

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        NiftyAppState niftyAppState = getAppState(NiftyAppState.class);
        niftyAppState.createNifty("Interface/map_editor.xml");
    }
}
