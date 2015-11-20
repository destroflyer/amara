/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.appstates;

import java.util.Iterator;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector2f;
import amara.Queue;
import amara.engine.applications.ingame.client.commands.*;
import amara.engine.applications.ingame.client.commands.casting.*;
import amara.engine.applications.ingame.client.gui.*;
import amara.engine.applications.ingame.client.systems.gui.DisplaySpellsImagesSystem;
import amara.engine.appstates.*;
import amara.engine.network.NetworkClient;
import amara.engine.input.*;
import amara.engine.input.events.*;
import amara.engine.network.messages.Message_Command;
import amara.engine.settings.Settings;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.items.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.components.shop.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.systems.shop.ShopUtil;

/**
 *
 * @author Carl
 */
public class SendPlayerCommandsAppState extends BaseDisplayAppState{

    public SendPlayerCommandsAppState(){
        
    }
    private ScreenController_HUD screenController_HUD;
    private ScreenController_Menu screenController_Menu;

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        screenController_HUD = getAppState(NiftyAppState.class).getScreenController(ScreenController_HUD.class);
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
                            int selectedEntity = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class).getEntity();
                            if(ShopUtil.canUseShop(entityWorld, selectedEntity, hoveredEntity)){
                                screenController_HUD.setShopVisible(true);
                            }
                        }
                    }
                }
                else if(mouseButtonIndex == Settings.getInteger("controls_navigation_walk_attack")){
                    if(hoveredEntity != -1){
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
                        else if(keyCode == Settings.getInteger("controls_interface_shop")){
                            screenController_HUD.toggleShopVisible();
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
        int selectedEntity = entityWorld.getComponent(playerAppState.getPlayerEntity(), SelectedUnitComponent.class).getEntity();
        int[] spells = entityWorld.getComponent(selectedEntity, SpellsComponent.class).getSpellsEntities();
        if((spellIndex < spells.length) && (spells[spellIndex] != -1)){
            int spellEntity = spells[spellIndex];
            SpellUpgradesComponent spellUpgradesComponent = entityWorld.getComponent(spellEntity, SpellUpgradesComponent.class);
            if(spellUpgradesComponent != null){
                int[] upgradedSpellEntities = spellUpgradesComponent.getSpellsEntities();
                for(int i=0;i<upgradedSpellEntities.length;i++){
                    screenController_HUD.setSpellUpgradeImage(i, DisplaySpellsImagesSystem.getSpellImagePath(entityWorld, upgradedSpellEntities[i]));
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
        PlayerAppState playerAppState = getAppState(PlayerAppState.class);
        int selectedEntity = entityWorld.getComponent(playerAppState.getPlayerEntity(), SelectedUnitComponent.class).getEntity();
        int spellEntity = getSpellEntity(entityWorld, selectedEntity, spellIndex);
        if(spellEntity != -1){
            Vector2f groundLocation = getAppState(MapAppState.class).getCursorHoveredGroundLocation();
            CastTypeComponent.CastType castType = entityWorld.getComponent(spellEntity, CastTypeComponent.class).getCastType();
            switch(castType){
                case SELFCAST:
                    sendCommand(new CastSelfcastSpellCommand(spellIndex));
                    break;

                case SINGLE_TARGET:
                    if(playerAppState.getCursorHoveredEntity() != -1){
                        sendCommand(new CastSingleTargetSpellCommand(spellIndex, playerAppState.getCursorHoveredEntity()));
                    }
                    break;

                case LINEAR_SKILLSHOT:
                    if(groundLocation != null){
                        Vector2f casterPosition = entityWorld.getComponent(selectedEntity, PositionComponent.class).getPosition();
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
    
    private void showReaction(String reaction){
        sendCommand(new ShowReactionCommand(reaction));
    }
    
    public void sendCommand(Command command){
        NetworkClient networkClient = getAppState(NetworkClientAppState.class).getNetworkClient();
        networkClient.sendMessage(new Message_Command(command));
    }
    
    public static int getSpellEntity(EntityWorld entityWorld, int casterEntity, SpellIndex spellIndex){
        switch(spellIndex.getSpellSet()){
            case SPELLS:
                SpellsComponent spellsComponent = entityWorld.getComponent(casterEntity, SpellsComponent.class);
                if(spellsComponent != null){
                    int[] spells = spellsComponent.getSpellsEntities();
                    if(spellIndex.getIndex() < spells.length){
                        return spells[spellIndex.getIndex()];
                    }
                }
                break;
            
            case ITEMS:
                InventoryComponent inventoryComponent = entityWorld.getComponent(casterEntity, InventoryComponent.class);
                if(inventoryComponent != null){
                    int[] items = inventoryComponent.getItemEntities();
                    if(spellIndex.getIndex() < items.length){
                        ItemActiveComponent itemActiveComponent = entityWorld.getComponent(items[spellIndex.getIndex()], ItemActiveComponent.class);
                        if(itemActiveComponent != null){
                            return itemActiveComponent.getSpellEntity();
                        }
                    }
                }
                break;
        }
        return -1;
    }
}
