/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.appstates;

import java.util.Iterator;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.math.Vector2f;
import amara.Queue;
import amara.engine.applications.ingame.client.commands.*;
import amara.engine.applications.ingame.client.commands.casting.*;
import amara.engine.appstates.*;
import amara.engine.network.NetworkClient;
import amara.engine.input.*;
import amara.engine.input.events.*;
import amara.engine.network.messages.Message_Command;
import amara.game.entitysystem.*;
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
                            }
                        }
                        break;
                }
            }
            else if(event instanceof KeyPressedEvent){
                KeyPressedEvent keyPressedEvent = (KeyPressedEvent) event;
                switch(keyPressedEvent.getKeyCode()){
                    case KeyInput.KEY_Q:
                        castSpell(0);
                        break;
                    
                    case KeyInput.KEY_W:
                        castSpell(1);
                        break;
                    
                    case KeyInput.KEY_E:
                        castSpell(2);
                        break;
                    
                    case KeyInput.KEY_R:
                        castSpell(3);
                        break;
                    
                    case KeyInput.KEY_S:
                        sendCommand(new StopCommand());
                        break;
                }
            }
        }
    }
    
    private void sendCommand(Command command){
        NetworkClient networkClient = getAppState(NetworkClientAppState.class).getNetworkClient();
        networkClient.sendMessage(new Message_Command(command));
    }
    
    private void castSpell(int spellIndex){
        EntityWorld entityWorld = getAppState(LocalEntitySystemAppState.class).getEntityWorld();
        int cursorHoveredEntity = getCursorHoveredEntity();
        Vector2f groundLocation = getAppState(MapAppState.class).getCursorHoveredGroundLocation();
        EntityWrapper selectedEntity = entityWorld.getWrapped(entityWorld.getComponent(getPlayerEntityID(), SelectedUnitComponent.class).getEntityID());
        SpellsComponent spellsComponent = selectedEntity.getComponent(SpellsComponent.class);
        if(spellsComponent != null){
            int[] spells = spellsComponent.getSpellsEntities();
            if(spellIndex < spells.length){
                int spellEntity = spells[spellIndex];
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
                            Vector2f direction = groundLocation.subtract(selectedEntity.getComponent(PositionComponent.class).getPosition());
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
    
    private int getPlayerEntityID(){
        int clientID = getAppState(NetworkClientAppState.class).getNetworkClient().getID();
        EntityWorld entityWorld = getAppState(LocalEntitySystemAppState.class).getEntityWorld();
        for(int entity : entityWorld.getEntitiesWithAll(ClientComponent.class)){
            if(entityWorld.getComponent(entity, ClientComponent.class).getClientID() == clientID){
                return entity;
            }
        }
        return -1;
    }
}
