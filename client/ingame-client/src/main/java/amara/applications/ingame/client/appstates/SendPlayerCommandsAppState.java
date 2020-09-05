/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.appstates;

import java.util.Iterator;

import amara.applications.ingame.client.IngameClientApplication;
import amara.applications.ingame.client.gui.*;
import amara.applications.ingame.client.systems.gui.DisplaySpellsImagesSystem;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.shop.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.systems.commands.ExecutePlayerCommandsSystem;
import amara.applications.ingame.entitysystem.systems.shop.ShopUtil;
import amara.applications.ingame.network.messages.Message_Command;
import amara.applications.ingame.network.messages.objects.commands.*;
import amara.applications.ingame.network.messages.objects.commands.casting.*;
import amara.applications.ingame.shared.maps.MapCamera;
import amara.applications.ingame.shared.maps.cameras.*;
import amara.core.Queue;
import amara.core.input.Event;
import amara.core.input.events.*;
import amara.core.settings.Settings;
import amara.libraries.applications.display.appstates.*;
import amara.libraries.applications.display.ingame.appstates.MapAppState;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;
import amara.libraries.entitysystem.*;
import amara.libraries.network.NetworkClient;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.ChaseCamera;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

/**
 *
 * @author Carl
 */
public class SendPlayerCommandsAppState extends BaseDisplayAppState<IngameClientApplication> {

    private ScreenController_HUD screenController_HUD;
    private ScreenController_Shop screenController_Shop;
    private ScreenController_Menu screenController_Menu;
    private Vector3f targetWalkDirection3f = new Vector3f();
    private Vector2f targetWalkDirection2f = new Vector2f();
    private boolean[] targetWalkDirectionKeys = new boolean[4];
    private float tmpChaseCameraHorizontalRotation;
    private float tmpChaseCameraVerticalRotation;
    private boolean targetWalkDirectionNeedsUpdate;

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);
        screenController_HUD = getAppState(NiftyAppState.class).getScreenController(ScreenController_HUD.class);
        screenController_Shop = getAppState(NiftyAppState.class).getScreenController(ScreenController_Shop.class);
        screenController_Menu = getAppState(NiftyAppState.class).getScreenController(ScreenController_Menu.class);
    }

    @Override
    public void update(float lastTimePerFrame) {
        super.update(lastTimePerFrame);
        MapAppState mapAppState = getAppState(MapAppState.class);
        MapCamera mapCamera = mapAppState.getMap().getCamera();
        String controlsCameraPrefix = "controls_" + mapCamera.getType() + "_";
        // Events
        Queue<Event> eventQueue = getAppState(EventManagerAppState.class).getEventQueue();
        Iterator<Event> eventsIterator = eventQueue.getIterator();
        while (eventsIterator.hasNext()) {
            Event event = eventsIterator.next();
            if (event instanceof MouseClickEvent) {
                MouseClickEvent mouseClickEvent = (MouseClickEvent) event;
                int mouseButtonIndex = mouseClickEvent.getButton().ordinal();
                int hoveredEntity = getAppState(PlayerAppState.class).getCursorHoveredEntity();
                if (mouseButtonIndex == Settings.getInteger(controlsCameraPrefix + "navigation_select")) {
                    if (hoveredEntity != -1) {
                        EntityWorld entityWorld = getAppState(LocalEntitySystemAppState.class).getEntityWorld();
                        if (entityWorld.hasComponent(hoveredEntity, ShopRangeComponent.class)) {
                            int playerEntity = getAppState(PlayerAppState.class).getPlayerEntity();
                            int characterEntity = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
                            if (ShopUtil.canUseShop(entityWorld, characterEntity, hoveredEntity)) {
                                screenController_Shop.setShopVisible(true);
                            }
                        }
                    }
                } else if (mapCamera instanceof MapCamera_TopDown) {
                    if (mouseButtonIndex == Settings.getInteger(controlsCameraPrefix + "navigation_walk_attack")) {
                        if ((hoveredEntity != -1) && (hoveredEntity != getPlayerCharacterEntity())) {
                            sendCommand(new AutoAttackCommand(hoveredEntity));
                        } else {
                            Vector2f groundLocation = mapAppState.getGroundLocation_Cursor();
                            if (groundLocation != null) {
                                sendCommand(new WalkToTargetCommand(groundLocation));
                                getAppState(IngameFeedbackAppState.class).displayMovementTarget(groundLocation);
                            }
                        }
                    }
                } else if (mapCamera instanceof MapCamera_3rdPerson) {
                    if (mouseButtonIndex == Settings.getInteger(controlsCameraPrefix + "navigation_attack")) {
                        if ((hoveredEntity != -1) && (hoveredEntity != getPlayerCharacterEntity())) {
                            sendCommand(new AutoAttackCommand(hoveredEntity));
                        }
                    }
                }
            } else if (event instanceof KeyEvent) {
                KeyEvent keyEvent = (KeyEvent) event;
                if (screenController_Menu.isReadingKeyInput()) {
                    screenController_Menu.readKey(keyEvent.getKeyCode());
                } else {
                    int keyCode = keyEvent.getKeyCode();
                    boolean keyWasNotFound = true;
                    if (keyEvent.isPressed()) {
                        for (int i = 0; i < 4; i++) {
                            if (keyCode == Settings.getInteger(controlsCameraPrefix + "spells_" + i)) {
                                castSpell(new SpellIndex(SpellIndex.SpellSet.SPELLS, i));
                                keyWasNotFound = false;
                                break;
                            }
                        }
                        for (int i = 0; i < 3; i++) {
                            if (keyCode == Settings.getInteger(controlsCameraPrefix + "spells_map_" + i)) {
                                castSpell(new SpellIndex(SpellIndex.SpellSet.MAP, i));
                                keyWasNotFound = false;
                                break;
                            }
                        }
                        if (keyWasNotFound) {
                            for (int i = 0; i < 6; i++) {
                                if (keyCode == Settings.getInteger(controlsCameraPrefix + "items_" + i)) {
                                    castSpell(new SpellIndex(SpellIndex.SpellSet.ITEMS, i));
                                    keyWasNotFound = false;
                                    break;
                                }
                            }
                        }
                        if (keyWasNotFound) {
                            if (keyCode == Settings.getInteger(controlsCameraPrefix + "interface_scoreboard")) {
                                screenController_HUD.toggleScoreboardVisible();
                                keyWasNotFound = false;
                            } else if (keyCode == Settings.getInteger(controlsCameraPrefix + "interface_shop")) {
                                screenController_Shop.toggleShopVisible();
                                keyWasNotFound = false;
                            } else if (keyCode == Settings.getInteger(controlsCameraPrefix + "interface_bag")) {
                                screenController_HUD.togglePlayerBagVisible();
                                keyWasNotFound = false;
                            } else if (keyCode == Settings.getInteger(controlsCameraPrefix + "interface_menu")) {
                                screenController_Menu.toggleMenuVisible();
                                keyWasNotFound = false;
                            } else if (keyCode == Settings.getInteger(controlsCameraPrefix + "reactions_0")) {
                                showReaction("kappa");
                                keyWasNotFound = false;
                            } else if (keyCode == Settings.getInteger(controlsCameraPrefix + "reactions_1")) {
                                showReaction("pogchamp");
                                keyWasNotFound = false;
                            } else if (keyCode == Settings.getInteger(controlsCameraPrefix + "reactions_2")) {
                                showReaction("kreygasm");
                                keyWasNotFound = false;
                            } else if (keyCode == Settings.getInteger(controlsCameraPrefix + "reactions_3")) {
                                showReaction("biblethump");
                                keyWasNotFound = false;
                            }
                        }
                    }
                    if (keyWasNotFound) {
                        if (mapCamera instanceof MapCamera_TopDown) {
                            if (keyEvent.isPressed()) {
                                if (keyCode == Settings.getInteger(controlsCameraPrefix + "navigation_stop")) {
                                    sendCommand(new StopCommand());
                                }
                            }
                        } else if (mapCamera instanceof MapCamera_3rdPerson) {
                            if (keyCode == Settings.getInteger(controlsCameraPrefix + "navigation_forward")) {
                                targetWalkDirectionKeys[0] = keyEvent.isPressed();
                                targetWalkDirectionNeedsUpdate = true;
                            } else if (keyCode == Settings.getInteger(controlsCameraPrefix + "navigation_right")) {
                                targetWalkDirectionKeys[1] = keyEvent.isPressed();
                                targetWalkDirectionNeedsUpdate = true;
                            } else if (keyCode == Settings.getInteger(controlsCameraPrefix + "navigation_backward")) {
                                targetWalkDirectionKeys[2] = keyEvent.isPressed();
                                targetWalkDirectionNeedsUpdate = true;
                            } else if (keyCode == Settings.getInteger(controlsCameraPrefix + "navigation_left")) {
                                targetWalkDirectionKeys[3] = keyEvent.isPressed();
                                targetWalkDirectionNeedsUpdate = true;
                            }
                        }
                    }
                }
            }
        }
        // Camera change
        if (mapCamera instanceof MapCamera_3rdPerson) {
            ChaseCamera chaseCamera = mapAppState.getChaseCamera();
            if ((chaseCamera.getHorizontalRotation() != tmpChaseCameraHorizontalRotation) || (chaseCamera.getVerticalRotation() != tmpChaseCameraVerticalRotation)) {
                targetWalkDirectionNeedsUpdate = true;
            }
            tmpChaseCameraHorizontalRotation = chaseCamera.getHorizontalRotation();
            tmpChaseCameraVerticalRotation = chaseCamera.getVerticalRotation();
        }
        if (targetWalkDirectionNeedsUpdate) {
            updateAndSendTargetWalkDirection();
            targetWalkDirectionNeedsUpdate = false;
        }
    }

    private void updateAndSendTargetWalkDirection() {
        updateTargetWalkDirection();
        sendTargetWalkDirection();
    }

    private void updateTargetWalkDirection() {
        Camera camera = mainApplication.getCamera();
        Vector3f cameraDirection = camera.getDirection();
        Vector3f cameraLeft = camera.getLeft();
        targetWalkDirection3f.set(0, 0, 0);
        if (targetWalkDirectionKeys[0]) {
            targetWalkDirection3f.addLocal(cameraDirection);
        }
        if (targetWalkDirectionKeys[1]) {
            targetWalkDirection3f.addLocal(cameraLeft.negate());
        }
        if (targetWalkDirectionKeys[2]) {
            targetWalkDirection3f.addLocal(cameraDirection.negate());
        }
        if (targetWalkDirectionKeys[3]) {
            targetWalkDirection3f.addLocal(cameraLeft);
        }
        targetWalkDirection3f.setY(0).normalizeLocal();
        targetWalkDirection2f.set(targetWalkDirection3f.getX(), targetWalkDirection3f.getZ());
    }

    private void sendTargetWalkDirection() {
        if (targetWalkDirection2f.lengthSquared() > 0) {
            sendCommand(new WalkInDirectionCommand(targetWalkDirection2f));
        } else {
            sendCommand(new StopWalkInDirectionCommand());
        }
    }

    public void learnOrUpgradeSpell(int spellIndex){
        EntityWorld entityWorld = getAppState(LocalEntitySystemAppState.class).getEntityWorld();
        PlayerAppState playerAppState = getAppState(PlayerAppState.class);
        int characterEntity = entityWorld.getComponent(playerAppState.getPlayerEntity(), PlayerCharacterComponent.class).getEntity();
        int[] spells = entityWorld.getComponent(characterEntity, SpellsComponent.class).getSpellsEntities();
        if((spellIndex < spells.length) && (spells[spellIndex] != -1)){
            int spellEntity = spells[spellIndex];
            SpellUpgradesComponent spellUpgradesComponent = entityWorld.getComponent(spellEntity, SpellUpgradesComponent.class);
            if(spellUpgradesComponent != null){
                int[] upgradedSpellEntities = spellUpgradesComponent.getSpellsEntities();
                for(int i=0;i<upgradedSpellEntities.length;i++){
                    screenController_HUD.setPlayer_SpellUpgradeImage(i, DisplaySpellsImagesSystem.getSpellImageFilePath(entityWorld, upgradedSpellEntities, i));
                }
                screenController_HUD.showPlayer_UpgradeSpell(spellIndex);
            }
        }
        else{
            sendCommand(new LearnSpellCommand(spellIndex));
        }
    }
    
    public void upgradeSpell(int spellIndex, int upgradeIndex){
        sendCommand(new UpgradeSpellCommand(spellIndex, upgradeIndex));
    }
    
    private void castSpell(SpellIndex spellIndex){
        EntityWorld entityWorld = getAppState(LocalEntitySystemAppState.class).getEntityWorld();
        int characterEntity = getPlayerCharacterEntity();
        int spellEntity = ExecutePlayerCommandsSystem.getSpellEntity(entityWorld, characterEntity, spellIndex);
        if(spellEntity != -1){
            Vector2f groundLocation = getAppState(MapAppState.class).getGroundLocation_Cursor();
            CastTypeComponent.CastType castType = entityWorld.getComponent(spellEntity, CastTypeComponent.class).getCastType();
            switch(castType){
                case SELFCAST:
                    sendCommand(new CastSelfcastSpellCommand(spellIndex));
                    break;

                case SINGLE_TARGET:
                    int hoveredEntity = getAppState(PlayerAppState.class).getCursorHoveredEntity();
                    if(hoveredEntity != -1){
                        sendCommand(new CastSingleTargetSpellCommand(spellIndex, hoveredEntity));
                    }
                    break;

                case LINEAR_SKILLSHOT:
                    if(groundLocation != null){
                        Vector2f casterPosition = entityWorld.getComponent(characterEntity, PositionComponent.class).getPosition();
                        Vector2f direction = groundLocation.subtract(casterPosition);
                        sendCommand(new CastLinearSkillshotSpellCommand(spellIndex, direction));
                    }
                    break;

                case POSITIONAL_SKILLSHOT:
                    if(groundLocation != null){
                        sendCommand(new CastPositionalSkillshotSpellCommand(spellIndex, groundLocation));
                    }
                    break;
            }
        }
    }
    
    private int getPlayerCharacterEntity(){
        EntityWorld entityWorld = getAppState(LocalEntitySystemAppState.class).getEntityWorld();
        int playerEntity = getAppState(PlayerAppState.class).getPlayerEntity();
        return entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
    }
    
    private void showReaction(String reaction){
        sendCommand(new ShowReactionCommand(reaction));
    }
    
    public void sendCommand(Command command){
        NetworkClient networkClient = mainApplication.getMasterserverClient().getState(NetworkClientHeadlessAppState.class).getNetworkClient();
        networkClient.sendMessage(new Message_Command(command));
    }
}
