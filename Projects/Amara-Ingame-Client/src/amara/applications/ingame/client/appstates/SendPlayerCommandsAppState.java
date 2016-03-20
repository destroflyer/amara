/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.appstates;

import java.util.Iterator;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector2f;
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
import amara.core.Queue;
import amara.core.input.Event;
import amara.core.input.events.*;
import amara.core.settings.Settings;
import amara.libraries.applications.display.appstates.*;
import amara.libraries.applications.display.ingame.appstates.MapAppState;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;
import amara.libraries.entitysystem.*;
import amara.libraries.network.NetworkClient;

/**
 *
 * @author Carl
 */
public class SendPlayerCommandsAppState extends BaseDisplayAppState<IngameClientApplication>{

    public SendPlayerCommandsAppState(){
        
    }
    private ScreenController_HUD screenController_HUD;
    private ScreenController_Shop screenController_Shop;
    private ScreenController_Menu screenController_Menu;

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        screenController_HUD = getAppState(NiftyAppState.class).getScreenController(ScreenController_HUD.class);
        screenController_Shop = getAppState(NiftyAppState.class).getScreenController(ScreenController_Shop.class);
        screenController_Menu = getAppState(NiftyAppState.class).getScreenController(ScreenController_Menu.class);
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        Queue<Event> eventQueue = getAppState(EventManagerAppState.class).getEventQueue();
        Iterator<Event> eventsIterator = eventQueue.getIterator();
        while(eventsIterator.hasNext()){
            Event event = eventsIterator.next();
            if(event instanceof MouseClickEvent){
                MouseClickEvent mouseClickEvent = (MouseClickEvent) event;
                int mouseButtonIndex = mouseClickEvent.getButton().ordinal();
                int hoveredEntity = getAppState(PlayerAppState.class).getCursorHoveredEntity();
                if(mouseButtonIndex == Settings.getInteger("controls_navigation_select")){
                    if(hoveredEntity != -1){
                        EntityWorld entityWorld = getAppState(LocalEntitySystemAppState.class).getEntityWorld();
                        if(entityWorld.hasComponent(hoveredEntity, ShopRangeComponent.class)){
                            int playerEntity = getAppState(PlayerAppState.class).getPlayerEntity();
                            int characterEntity = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
                            if(ShopUtil.canUseShop(entityWorld, characterEntity, hoveredEntity)){
                                screenController_Shop.setShopVisible(true);
                            }
                        }
                    }
                }
                else if(mouseButtonIndex == Settings.getInteger("controls_navigation_walk_attack")){
                    if((hoveredEntity != -1) && (hoveredEntity != getPlayerCharacterEntity())){
                        sendCommand(new AutoAttackCommand(hoveredEntity));
                    }
                    else{
                        Vector2f groundLocation = getAppState(MapAppState.class).getCursorHoveredGroundLocation();
                        if(groundLocation != null){
                            sendCommand(new MoveCommand(groundLocation));
                            getAppState(IngameFeedbackAppState.class).displayMovementTarget(groundLocation);
                        }
                    }
                }
            }
            else if(event instanceof KeyPressedEvent){
                KeyPressedEvent keyPressedEvent = (KeyPressedEvent) event;
                if(screenController_Menu.isReadingKeyInput()){
                    screenController_Menu.readKey(keyPressedEvent.getKeyCode());
                }
                else{
                    int keyCode = keyPressedEvent.getKeyCode();
                    boolean searchKey = true;
                    for(int i=0;i<4;i++){
                        if(keyCode == Settings.getInteger("controls_spells_" + i)){
                            castSpell(new SpellIndex(SpellIndex.SpellSet.SPELLS, i));
                            searchKey = false;
                            break;
                        }
                    }
                    for(int i=0;i<2;i++){
                        if(keyCode == Settings.getInteger("controls_spells_player_" + i)){
                            castSpell(new SpellIndex(SpellIndex.SpellSet.MAP, i));
                            searchKey = false;
                            break;
                        }
                    }
                    if(keyCode == Settings.getInteger("controls_spells_backport")){
                        castSpell(new SpellIndex(SpellIndex.SpellSet.MAP, 2));
                        searchKey = false;
                        break;
                    }
                    if(searchKey){
                        for(int i=0;i<6;i++){
                            if(keyCode == Settings.getInteger("controls_items_" + i)){
                                castSpell(new SpellIndex(SpellIndex.SpellSet.ITEMS, i));
                                searchKey = false;
                                break;
                            }
                        }
                    }
                    if(searchKey){
                        if(keyCode == Settings.getInteger("controls_navigation_stop")){
                            sendCommand(new StopCommand());
                        }
                        else if(keyCode == Settings.getInteger("controls_interface_scoreboard")){
                            screenController_HUD.toggleScoreboardVisible();
                        }
                        else if(keyCode == Settings.getInteger("controls_interface_shop")){
                            screenController_Shop.toggleShopVisible();
                        }
                        else if(keyCode == Settings.getInteger("controls_interface_menu")){
                            screenController_Menu.toggleMenuVisible();
                        }
                        else if(keyCode == Settings.getInteger("controls_reactions_0")){
                            showReaction("kappa");
                        }
                        else if(keyCode == Settings.getInteger("controls_reactions_1")){
                            showReaction("pogchamp");
                        }
                        else if(keyCode == Settings.getInteger("controls_reactions_2")){
                            showReaction("kreygasm");
                        }
                        else if(keyCode == Settings.getInteger("controls_reactions_3")){
                            showReaction("biblethump");
                        }
                    }
                }
            }
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
                    screenController_HUD.setSpellUpgradeImage(i, DisplaySpellsImagesSystem.getSpellImageFilePath(entityWorld, upgradedSpellEntities, i));
                }
                screenController_HUD.showUpgradeSpell(spellIndex);
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
            Vector2f groundLocation = getAppState(MapAppState.class).getCursorHoveredGroundLocation();
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
