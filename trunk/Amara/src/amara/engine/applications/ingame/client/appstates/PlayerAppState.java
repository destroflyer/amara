/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import amara.engine.applications.ingame.client.gui.ScreenController_HUD;
import amara.engine.applications.ingame.client.systems.gui.*;
import amara.engine.appstates.*;

/**
 *
 * @author Carl
 */
public class PlayerAppState extends BaseDisplayAppState{

    public PlayerAppState(int playerEntity){
        this.playerEntity = playerEntity;
    }
    private int playerEntity;

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        LocalEntitySystemAppState localEntitySystemAppState = getAppState(LocalEntitySystemAppState.class);
        NiftyAppState niftyAppState = getAppState(NiftyAppState.class);
        localEntitySystemAppState.addEntitySystem(new DisplayPlayerSystem(playerEntity, niftyAppState.getScreenController(ScreenController_HUD.class)));
        localEntitySystemAppState.addEntitySystem(new DisplayAttributesSystem(playerEntity, niftyAppState.getScreenController(ScreenController_HUD.class)));
        localEntitySystemAppState.addEntitySystem(new DisplayInventorySystem(playerEntity, niftyAppState.getScreenController(ScreenController_HUD.class)));
    }

    public int getPlayerEntity(){
        return playerEntity;
    }
}
