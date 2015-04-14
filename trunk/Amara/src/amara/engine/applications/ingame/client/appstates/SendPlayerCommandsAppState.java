/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.appstates;

import java.util.Iterator;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.math.Vector2f;
import amara.Queue;
import amara.engine.applications.ingame.client.commands.*;
import amara.engine.applications.ingame.client.commands.casting.*;
import amara.engine.applications.ingame.client.gui.ScreenController_HUD;
import amara.engine.appstates.*;
import amara.engine.network.NetworkClient;
import amara.engine.input.*;
import amara.engine.input.events.*;
import amara.engine.network.messages.Message_Command;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.items.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class SendPlayerCommandsAppState extends BaseDisplayAppState{

    public SendPlayerCommandsAppState(){
        
    }
    private ScreenController_HUD screenController_HUD;

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        screenController_HUD = getAppState(NiftyAppState.class).getScreenController(ScreenController_HUD.class);
        screenController_HUD.setAppStates(this);
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
                switch(mouseClickEvent.getButton()){                    
                    case Right:
                        int entityToAttack = getCursorHoveredEntity();
                        if(entityToAttack != -1){
                            sendCommand(new AutoAttackCommand(entityToAttack));
                        }
                        else{
                            Vector2f groundLocation = getAppState(MapAppState.class).getCursorHoveredGroundLocation();
                            if(groundLocation != null){
                                sendCommand(new MoveCommand(groundLocation));
                                getAppState(IngameFeedbackAppState.class).displayMovementTarget(groundLocation);
                            }
                        }
                        break;
                }
            }
            else if(event instanceof KeyPressedEvent){
                KeyPressedEvent keyPressedEvent = (KeyPressedEvent) event;
                switch(keyPressedEvent.getKeyCode()){
                    case KeyInput.KEY_Q:
                        castSpell(new SpellIndex(SpellIndex.SpellSet.SPELLS, 0));
                        break;

                    case KeyInput.KEY_W:
                        castSpell(new SpellIndex(SpellIndex.SpellSet.SPELLS, 1));
                        break;

                    case KeyInput.KEY_E:
                        castSpell(new SpellIndex(SpellIndex.SpellSet.SPELLS, 2));
                        break;

                    case KeyInput.KEY_R:
                        castSpell(new SpellIndex(SpellIndex.SpellSet.SPELLS, 3));
                        break;

                    case KeyInput.KEY_1:
                        castSpell(new SpellIndex(SpellIndex.SpellSet.ITEMS, 0));
                        break;

                    case KeyInput.KEY_2:
                        castSpell(new SpellIndex(SpellIndex.SpellSet.ITEMS, 1));
                        break;

                    case KeyInput.KEY_3:
                        castSpell(new SpellIndex(SpellIndex.SpellSet.ITEMS, 2));
                        break;

                    case KeyInput.KEY_4:
                        castSpell(new SpellIndex(SpellIndex.SpellSet.ITEMS, 3));
                        break;

                    case KeyInput.KEY_5:
                        castSpell(new SpellIndex(SpellIndex.SpellSet.ITEMS, 4));
                        break;

                    case KeyInput.KEY_6:
                        castSpell(new SpellIndex(SpellIndex.SpellSet.ITEMS, 5));
                        break;

                    case KeyInput.KEY_S:
                        sendCommand(new StopCommand());
                        break;

                    case KeyInput.KEY_P:
                        screenController_HUD.toggleShopVisible();
                        break;
                }
            }
        }
    }
    
    public void sendCommand(Command command){
        NetworkClient networkClient = getAppState(NetworkClientAppState.class).getNetworkClient();
        networkClient.sendMessage(new Message_Command(command));
    }
    
    private void castSpell(SpellIndex spellIndex){
        int playerEntity = getAppState(PlayerAppState.class).getPlayerEntity();
        EntityWorld entityWorld = getAppState(LocalEntitySystemAppState.class).getEntityWorld();
        int cursorHoveredEntity = getCursorHoveredEntity();
        Vector2f groundLocation = getAppState(MapAppState.class).getCursorHoveredGroundLocation();
        int selectedEntity = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class).getEntity();
        int spellEntity = getSpellEntity(entityWorld, selectedEntity, spellIndex);
        if(spellEntity != -1){
            CastTypeComponent.CastType castType = entityWorld.getComponent(spellEntity, CastTypeComponent.class).getCastType();
            switch(castType){
                case SELFCAST:
                    sendCommand(new CastSelfcastSpellCommand(spellIndex));
                    break;

                case SINGLE_TARGET:
                    if(cursorHoveredEntity != -1){
                        sendCommand(new CastSingleTargetSpellCommand(spellIndex, cursorHoveredEntity));
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
    
    private int getCursorHoveredEntity(){
        LocalEntitySystemAppState localEntitySystemAppState = getAppState(LocalEntitySystemAppState.class);
        CollisionResults entitiesColissionResults = mainApplication.getRayCastingResults_Cursor(localEntitySystemAppState.getEntitiesNode());
        for(int i=0;i<entitiesColissionResults.size();i++){
            CollisionResult collision = entitiesColissionResults.getCollision(i);
            int entity = localEntitySystemAppState.getEntity(collision.getGeometry());
            if(entity != -1){
                return entity;
            }
        }
        return -1;
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
