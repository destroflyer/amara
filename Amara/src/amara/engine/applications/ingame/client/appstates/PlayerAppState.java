/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import amara.engine.applications.ingame.client.gui.ScreenController_HUD;
import amara.engine.applications.ingame.client.systems.filters.*;
import amara.engine.applications.ingame.client.systems.gui.*;
import amara.engine.appstates.*;
import amara.game.entitysystem.systems.physics.intersectionHelper.PolyMapManager;
import amara.game.maps.Map;

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
        PostFilterAppState postFilterAppState = getAppState(PostFilterAppState.class);
        localEntitySystemAppState.addEntitySystem(new PlayerDeathDisplaySystem(playerEntity, postFilterAppState));
        Map map = getAppState(MapAppState.class).getMap();
        PolyMapManager polyMapManager = map.getPhysicsInformation().getPolyMapManager();
        localEntitySystemAppState.addEntitySystem(new PlayerVisionDisplaySystem(playerEntity, postFilterAppState, polyMapManager));
        ScreenController_HUD screenController_HUD = getAppState(NiftyAppState.class).getScreenController(ScreenController_HUD.class);
        localEntitySystemAppState.addEntitySystem(new DisplayPlayerSystem(playerEntity, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayAttributesSystem(playerEntity, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayInventorySystem(playerEntity, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplaySpellsImagesSystem(playerEntity, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplaySpellsCooldownsSystem(playerEntity, screenController_HUD));
    }

    public int getPlayerEntity(){
        return playerEntity;
    }
}
