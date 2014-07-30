/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import amara.engine.applications.ingame.client.gui.ScreenController_HUD;
import amara.engine.applications.ingame.client.systems.camera.*;
import amara.engine.applications.ingame.client.systems.filters.*;
import amara.engine.applications.ingame.client.systems.gui.*;
import amara.engine.appstates.*;
import amara.game.entitysystem.systems.physics.intersectionHelper.PolyMapManager;
import amara.game.maps.Map;

/**
 *
 * @author Carl
 */
public class PlayerAppState extends BaseDisplayAppState implements ActionListener{

    public PlayerAppState(int playerEntity){
        this.playerEntity = playerEntity;
    }
    private int playerEntity;
    private LockedCameraSystem lockedCameraSystem;

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        mainApplication.getInputManager().addMapping("lock_camera", new KeyTrigger(KeyInput.KEY_Z));
        mainApplication.getInputManager().addListener(this, new String[]{
            "lock_camera"
        });
        LocalEntitySystemAppState localEntitySystemAppState = getAppState(LocalEntitySystemAppState.class);
        lockedCameraSystem = new LockedCameraSystem(playerEntity, getAppState(IngameCameraAppState.class));
        localEntitySystemAppState.addEntitySystem(lockedCameraSystem);
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
        localEntitySystemAppState.addEntitySystem(new DisplayItemsCooldownsSystem(playerEntity, screenController_HUD));
    }

    @Override
    public void onAction(String actionName, boolean value, float lastTimePerFrame){
        if(actionName.equals("lock_camera") && value){
            //lockedCameraSystem.setEnabled(!lockedCameraSystem.isEnabled());
        }
    }

    public int getPlayerEntity(){
        return playerEntity;
    }
}
