package amara.applications.ingame.client.appstates;

import java.util.HashMap;
import java.util.Iterator;

import amara.applications.ingame.client.IngameClientApplication;
import amara.applications.ingame.client.gui.*;
import amara.applications.ingame.client.systems.camera.*;
import amara.applications.ingame.client.systems.filters.*;
import amara.applications.ingame.client.systems.gui.*;
import amara.applications.ingame.client.systems.information.*;
import amara.applications.ingame.client.systems.visualisation.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.master.network.messages.objects.GameSelectionPlayer;
import amara.core.Queue;
import amara.core.input.Event;
import amara.core.input.events.MouseClickEvent;
import amara.libraries.applications.display.appstates.*;
import amara.libraries.applications.display.ingame.appstates.*;
import amara.libraries.entitysystem.EntityWorld;
import amara.core.settings.Settings;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector2f;

public class PlayerAppState extends BaseDisplayAppState<IngameClientApplication> implements ActionListener {

    public PlayerAppState(GameSelectionPlayer[][] teams) {
        this.teams = teams;
        playerTeamSystem = new PlayerTeamSystem(this::getPlayerEntity);
    }
    private GameSelectionPlayer[][] teams;
    private int playerEntity;
    private PlayerTeamSystem playerTeamSystem;
    private OwnTeamVisionSystem ownTeamVisionSystem;
    private SpellIndicatorSystem spellIndicatorSystem;
    private LockedCameraSystem lockedCameraSystem;
    private FogOfWarSystem fogOfWarSystem;
    private int cursorHoveredEntity = -1;
    private int inspectedEntity = -1;
    private CollisionResults[] tmpEntitiesCollisionResults = new CollisionResults[8];
    private HashMap<Integer, Integer> tmpHoveredEntitiesCount = new HashMap<>();

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);
        EntitySceneMap entitySceneMap = getAppState(LocalEntitySystemAppState.class).getEntitySceneMap();
        ownTeamVisionSystem = new OwnTeamVisionSystem(entitySceneMap, playerTeamSystem);
        getAppState(NiftyAppState.class).getScreenController(ScreenController_HUD.class).generateScoreboard(teams);
    }

    public void onInitialWorldLoaded() {
        mainApplication.getInputManager().addMapping("lock_camera", new KeyTrigger(KeyInput.KEY_Z));
        mainApplication.getInputManager().addMapping("change_sight", new KeyTrigger(KeyInput.KEY_U));
        mainApplication.getInputManager().addListener(this, "lock_camera","change_sight");
        LocalEntitySystemAppState localEntitySystemAppState = getAppState(LocalEntitySystemAppState.class);
        localEntitySystemAppState.addEntitySystem(ownTeamVisionSystem);
        spellIndicatorSystem = new SpellIndicatorSystem(this::getPlayerEntity, localEntitySystemAppState.getEntitySceneMap());
        localEntitySystemAppState.addEntitySystem(spellIndicatorSystem);
        IngameCameraAppState ingameCameraAppState = getAppState(IngameCameraAppState.class);
        MapAppState mapAppState = getAppState(MapAppState.class);
        lockedCameraSystem = new LockedCameraSystem(this::getPlayerEntity, ingameCameraAppState);
        localEntitySystemAppState.addEntitySystem(lockedCameraSystem);
        localEntitySystemAppState.addEntitySystem(new MoveCameraToPlayerSystem(this::getPlayerEntity, ingameCameraAppState));
        if (mapAppState.getChaseCamera() != null) {
            localEntitySystemAppState.addEntitySystem(new ChaseCameraSystem(this::getPlayerEntity, mapAppState.getChaseCamera(), localEntitySystemAppState.getEntitySceneMap()));
        }
        PostFilterAppState postFilterAppState = getAppState(PostFilterAppState.class);
        localEntitySystemAppState.addEntitySystem(new PlayerDeathDisplaySystem(this::getPlayerEntity, postFilterAppState));
        localEntitySystemAppState.addEntitySystem(new ShopAnimationSystem(this::getPlayerEntity, localEntitySystemAppState.getEntitySceneMap()));
        if (Settings.getFloat("fog_of_war_update_interval") != -1) {
            fogOfWarSystem = new FogOfWarSystem(playerTeamSystem, postFilterAppState, mapAppState.getMap().getPhysicsInformation());
            localEntitySystemAppState.addEntitySystem(fogOfWarSystem);
        }
        ScreenController_HUD screenController_HUD = getAppState(NiftyAppState.class).getScreenController(ScreenController_HUD.class);
        ScreenController_Shop screenController_Shop = getAppState(NiftyAppState.class).getScreenController(ScreenController_Shop.class);
        localEntitySystemAppState.addEntitySystem(new DisplayGameTimeSystem(this, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayPlayerAnnouncementSystem(this, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayInspectionSystem(this, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayHudNameSystem(this, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayLevelSystem(this, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayExperienceSystem(this, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayAttributesSystem(this, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayBuffsSystem(this, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayBuffStacksSystem(this, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayInventoriesSystem(this, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayBagSystem(this, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayPassivesImagesSystem(this, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplaySpellsImagesSystem(this, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayMapSpellsImagesSystem(this, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayPassivesCooldownsSystem(this, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplaySpellsCooldownsSystem(this, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayItemsCooldownsSystem(this, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayMapSpellsCooldownsSystem(this, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new UpdateSpellInformationsSystem(this, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new UpdateUpgradeSpellsPanelSystem(this, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayGoldSystem(this, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayStatsPlayerScoreSystem(this, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayDeathRecapSystem(this, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayDeathTimerSystem(this, screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayScoreboardPlayersNamesSystem(screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayScoreboardScoresSystem(screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayScoreboardInventoriesSystem(screenController_HUD));
        localEntitySystemAppState.addEntitySystem(new DisplayMinimapSystem(this, screenController_HUD, mapAppState.getMap(), playerTeamSystem, ownTeamVisionSystem, fogOfWarSystem));
        localEntitySystemAppState.addEntitySystem(new DisplayShopItemsSystem(this, screenController_Shop));
        localEntitySystemAppState.addEntitySystem(new UpdateRecipeCostsSystem(this::getPlayerEntity, screenController_Shop));
    }

    @Override
    public void update(float lastTimePerFrame) {
        super.update(lastTimePerFrame);
        updateCursorHoveredEntity();
        updateInspectedEntity();
    }

    private void updateCursorHoveredEntity() {
        LocalEntitySystemAppState localEntitySystemAppState = getAppState(LocalEntitySystemAppState.class);
        Vector2f cursorPosition = mainApplication.getInputManager().getCursorPosition();
        int tmpCursorHoveredEntity = cursorHoveredEntity;
        cursorHoveredEntity = getHoveredCollisionResults(mainApplication.getRayCastingResults_Screen(localEntitySystemAppState.getNodeVisibleToMouse(), cursorPosition));
        if (cursorHoveredEntity == -1) {
            float alternativeRange = 17;
            Vector2f alternativePosition = new Vector2f();
            int i = 0;
            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++){
                    if ((x != 0) || (y != 0)) {
                        alternativePosition.set(cursorPosition.getX() + (x * alternativeRange), cursorPosition.getY() + (y * alternativeRange));
                        tmpEntitiesCollisionResults[i] = mainApplication.getRayCastingResults_Screen(localEntitySystemAppState.getNodeVisibleToMouse(), alternativePosition);
                        i++;
                    }
                }
            }
            cursorHoveredEntity = getHoveredCollisionResults(tmpEntitiesCollisionResults);
        }
        if (cursorHoveredEntity != tmpCursorHoveredEntity) {
            if (tmpCursorHoveredEntity != -1) {
                localEntitySystemAppState.getEntityWorld().removeComponent(tmpCursorHoveredEntity, IsHoveredComponent.class);
            }
            if (cursorHoveredEntity != -1) {
                localEntitySystemAppState.getEntityWorld().setComponent(cursorHoveredEntity, new IsHoveredComponent());
            }
        }
    }

    private int getHoveredCollisionResults(CollisionResults... entitiesCollisionResults) {
        LocalEntitySystemAppState localEntitySystemAppState = getAppState(LocalEntitySystemAppState.class);
        tmpHoveredEntitiesCount.clear();
        int resultEntity = -1;
        int maximumCount = 0;
        for (CollisionResults collisionResults : entitiesCollisionResults) {
            for (CollisionResult collision : collisionResults) {
                int entity = localEntitySystemAppState.getEntity(collision.getGeometry());
                Integer count = tmpHoveredEntitiesCount.get(entity);
                if (count == null) {
                    count = 0;
                }
                count++;
                tmpHoveredEntitiesCount.put(entity, count);
                if (count > maximumCount) {
                    resultEntity = entity;
                }
                break;
            }
        }
        return resultEntity;
    }

    private void updateInspectedEntity() {
        LocalEntitySystemAppState localEntitySystemAppState = getAppState(LocalEntitySystemAppState.class);
        EntityWorld entityWorld = localEntitySystemAppState.getEntityWorld();
        // Inspect hovered entity when selected
        Queue<Event> eventQueue = getAppState(EventManagerAppState.class).getEventQueue();
        Iterator<Event> eventsIterator = eventQueue.getIterator();
        while(eventsIterator.hasNext()) {
            Event event = eventsIterator.next();
            if (event instanceof MouseClickEvent) {
                MouseClickEvent mouseClickEvent = (MouseClickEvent) event;
                int mouseButtonIndex = mouseClickEvent.getButton().ordinal();
                String mapCameraType = getAppState(MapAppState.class).getMap().getCamera().getType();
                if (mouseButtonIndex == Settings.getInteger("controls_" + mapCameraType + "_navigation_select")) {
                    if ((cursorHoveredEntity != -1) && AttachEntityNodesSystem.isInspectable(entityWorld, cursorHoveredEntity)) {
                        inspectedEntity = cursorHoveredEntity;
                    } else {
                        inspectedEntity = -1;
                    }
                }
            }
        }
        // Reset inspection when entity gets removed
        if ((inspectedEntity != -1) && (!entityWorld.hasEntity(inspectedEntity))) {
            inspectedEntity = -1;
        }
    }

    @Override
    public void onAction(String actionName, boolean value, float lastTimePerFrame) {
        if (actionName.equals("lock_camera") && value) {
            lockedCameraSystem.setEnabled(!lockedCameraSystem.isEnabled());
        } else if (actionName.equals("change_sight") && value) {
            if (fogOfWarSystem != null) {
                // Switch between the three possible states
                if (fogOfWarSystem.isEnabled()) {
                    if (fogOfWarSystem.isDisplayAllSight()) {
                        fogOfWarSystem.setEnabled(false);
                    } else {
                        fogOfWarSystem.setDisplayAllSight(true);
                        ownTeamVisionSystem.setEnabled(false);
                    }
                } else {
                    fogOfWarSystem.setDisplayAllSight(false);
                    fogOfWarSystem.setEnabled(true);
                    ownTeamVisionSystem.setEnabled(true);
                }
            }
        }
    }

    public void setPlayerEntity(int playerEntity) {
        this.playerEntity = playerEntity;
    }

    public int getPlayerEntity() {
        return playerEntity;
    }

    public PlayerTeamSystem getPlayerTeamSystem() {
        return playerTeamSystem;
    }

    public OwnTeamVisionSystem getOwnTeamVisionSystem() {
        return ownTeamVisionSystem;
    }

    public SpellIndicatorSystem getSpellIndicatorSystem() {
        return spellIndicatorSystem;
    }

    public LockedCameraSystem getLockedCameraSystem() {
        return lockedCameraSystem;
    }

    public FogOfWarSystem getFogOfWarSystem() {
        return fogOfWarSystem;
    }

    public int getCursorHoveredEntity() {
        return cursorHoveredEntity;
    }

    public int getInspectedEntity() {
        return inspectedEntity;
    }
}
