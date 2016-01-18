/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.appstates;

import java.util.HashMap;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector2f;
import amara.core.settings.Settings;
import amara.engine.applications.ingame.client.IngameClientApplication;
import amara.engine.applications.ingame.client.gui.*;
import amara.engine.applications.ingame.client.systems.camera.*;
import amara.engine.applications.ingame.client.systems.filters.*;
import amara.engine.applications.ingame.client.systems.gui.*;
import amara.engine.applications.ingame.client.systems.information.PlayerTeamSystem;
import amara.engine.applications.ingame.client.systems.visualisation.*;
import amara.game.entitysystem.components.units.IsHoveredComponent;
import amara.game.maps.Map;
import amara.libraries.applications.display.appstates.*;
import amara.libraries.applications.display.ingame.appstates.*;
import amara.libraries.physics.intersectionHelper.PolyMapManager;

/**
 *
 * @author Carl
 */
public class PlayerAppState extends BaseDisplayAppState<IngameClientApplication> implements ActionListener{

    public PlayerAppState(int playerEntity){
        this.playerEntity = playerEntity;
        playerTeamSystem = new PlayerTeamSystem(playerEntity);
    }
    private int playerEntity;
    private PlayerTeamSystem playerTeamSystem;
    private LockedCameraSystem lockedCameraSystem;
    private FogOfWarSystem fogOfWarSystem;
    private int cursorHoveredEntity = -1;
    private CollisionResults[] tmpEntitiesColisionResults = new CollisionResults[8];
    private HashMap<Integer, Integer> tmpHoveredEntitiesCount = new HashMap<Integer, Integer>();

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        mainApplication.getInputManager().addMapping("lock_camera", new KeyTrigger(KeyInput.KEY_Z));
        mainApplication.getInputManager().addMapping("display_map_sight", new KeyTrigger(KeyInput.KEY_U));
        mainApplication.getInputManager().addListener(this, new String[]{
            "lock_camera","display_map_sight"
        });
        LocalEntitySystemAppState localEntitySystemAppState = getAppState(LocalEntitySystemAppState.class);
        lockedCameraSystem = new LockedCameraSystem(playerEntity, getAppState(IngameCameraAppState.class));
        localEntitySystemAppState.addEntitySystem(lockedCameraSystem);
        PostFilterAppState postFilterAppState = getAppState(PostFilterAppState.class);
        localEntitySystemAppState.addEntitySystem(new PlayerDeathDisplaySystem(playerEntity, postFilterAppState));
        localEntitySystemAppState.addEntitySystem(new ShopAnimationSystem(playerEntity, localEntitySystemAppState.getEntitySceneMap()));
        if(Settings.getFloat("fog_of_war_update_interval") != -1){
            Map map = getAppState(MapAppState.class).getMap();
            PolyMapManager polyMapManager = map.getPhysicsInformation().getPolyMapManager();
            fogOfWarSystem = new FogOfWarSystem(playerTeamSystem, postFilterAppState, polyMapManager);
            localEntitySystemAppState.addEntitySystem(fogOfWarSystem);
        }
        ScreenController_HUD screenController_HUD = getAppState(NiftyAppState.class).getScreenController(ScreenController_HUD.class);
        ScreenController_Shop screenController_Shop = getAppState(NiftyAppState.class).getScreenController(ScreenController_Shop.class);
        localEntitySystemAppState.addEntitySystem(new DisplayPlayerSystem(playerEntity, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayLevelSystem(playerEntity, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayExperienceSystem(playerEntity, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayAttributesSystem(playerEntity, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayInventorySystem(playerEntity, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayPassivesImagesSystem(playerEntity, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplaySpellsImagesSystem(playerEntity, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplaySpellsCooldownsSystem(playerEntity, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayItemsCooldownsSystem(playerEntity, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new UpdateSpellInformationsSystem(playerEntity, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new UpdateUpgradeSpellsPanelSystem(playerEntity, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayGoldSystem(playerEntity, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayDeathRecapSystem(playerEntity, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new UpdateRecipeCostsSystem(playerEntity, screenController_Shop));
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        updateCursorHoveredEntity();
    }
    
    private void updateCursorHoveredEntity(){
        LocalEntitySystemAppState localEntitySystemAppState = getAppState(LocalEntitySystemAppState.class);
        Vector2f cursorPosition = mainApplication.getInputManager().getCursorPosition();
        int tmpCursorHoveredEntity = cursorHoveredEntity;
        cursorHoveredEntity = getCollisionResultEntity(mainApplication.getRayCastingResults_Screen(localEntitySystemAppState.getEntitiesNode(), cursorPosition));
        if(cursorHoveredEntity == -1){
            float alternativeRange = 17;
            Vector2f alternativePosition = new Vector2f();
            int i = 0;
            for(int x=-1;x<2;x++){
                for(int y=-1;y<2;y++){
                    if((x != 0) || (y != 0)){
                        alternativePosition.set(cursorPosition.getX() + (x * alternativeRange), cursorPosition.getY() + (y * alternativeRange));
                        tmpEntitiesColisionResults[i] = mainApplication.getRayCastingResults_Screen(localEntitySystemAppState.getEntitiesNode(), alternativePosition);
                        i++;
                    }
                }
            }
            cursorHoveredEntity = getCollisionResultEntity(tmpEntitiesColisionResults);
        }
        if(cursorHoveredEntity != tmpCursorHoveredEntity){
            if(tmpCursorHoveredEntity != -1){
                localEntitySystemAppState.getEntityWorld().removeComponent(tmpCursorHoveredEntity, IsHoveredComponent.class);
            }
            if(cursorHoveredEntity != -1){
                localEntitySystemAppState.getEntityWorld().setComponent(cursorHoveredEntity, new IsHoveredComponent());
            }
        }
    }
    
    private int getCollisionResultEntity(CollisionResults... entitiesColisionResults){
        LocalEntitySystemAppState localEntitySystemAppState = getAppState(LocalEntitySystemAppState.class);
        tmpHoveredEntitiesCount.clear();
        int resultEntity = -1;
        //float minimumDistance = Float.MAX_VALUE;
        int maximumCount = 0;
        for(CollisionResults collisionResults : entitiesColisionResults){
            for(CollisionResult collision : collisionResults){
                int entity = localEntitySystemAppState.getEntity(collision.getGeometry());
                /*if((entity != -1) && (collision.getDistance() < minimumDistance)){
                    resultEntity = entity;
                    minimumDistance = collision.getDistance();
                    break;
                }*/
                if(entity != -1){
                    Integer count = tmpHoveredEntitiesCount.get(entity);
                    if(count == null){
                        count = 0;
                    }
                    count++;
                    tmpHoveredEntitiesCount.put(entity, count);
                    if(count > maximumCount){
                        resultEntity = entity;
                    }
                    break;
                }
            }
        }
        return resultEntity;
    }

    @Override
    public void onAction(String actionName, boolean value, float lastTimePerFrame){
        if(actionName.equals("lock_camera") && value){
            lockedCameraSystem.setEnabled(!lockedCameraSystem.isEnabled());
        }
        else if(actionName.equals("display_map_sight") && value){
            if(fogOfWarSystem != null){
                fogOfWarSystem.setDisplayMapSight(!fogOfWarSystem.isDisplayMapSight());
            }
        }
    }

    public int getPlayerEntity(){
        return playerEntity;
    }

    public PlayerTeamSystem getPlayerTeamSystem(){
        return playerTeamSystem;
    }

    public LockedCameraSystem getLockedCameraSystem(){
        return lockedCameraSystem;
    }

    public FogOfWarSystem getFogOfWarSystem(){
        return fogOfWarSystem;
    }

    public int getCursorHoveredEntity(){
        return cursorHoveredEntity;
    }
}
