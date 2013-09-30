/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.appstates;

import java.util.Iterator;
import java.util.List;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.math.Vector2f;
import amara.Queue;
import amara.engine.client.commands.*;
import amara.engine.client.commands.casting.*;
import amara.engine.input.*;
import amara.engine.input.events.*;
import amara.game.entitysystem.EntityWrapper;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.selection.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class SendPlayerCommandsAppState extends BaseAppState{

    public SendPlayerCommandsAppState(){
        
    }
    public static Queue<Command> TEST_COMMAND_QUEUE = new Queue<Command>();

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
                    case Left:
                        int entityToSelect = getCursorHoveredEntity();
                        if(entityToSelect != -1){
                            sendCommand(new SelectionCommand(entityToSelect));
                        }
                        else{
                            sendCommand(new DeselectionCommand());
                        }
                        break;
                    
                    case Right:
                        int entityToAttack = getCursorHoveredEntity();
                        if(entityToAttack == -1){
                            Vector2f groundLocation = getCursorHoveredGroundLocation();
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
                }
            }
        }
        mainApplication.enqueueTask(new Runnable(){

            public void run(){
                TEST_COMMAND_QUEUE.clear();
            }
        });
    }
    
    private void sendCommand(Command command){
        TEST_COMMAND_QUEUE.add(command);
    }
    
    private void castSpell(int spellIndex){
        EntitySystemAppState entitySystemAppState = getAppState(EntitySystemAppState.class);
        int cursorHoveredEntity = getCursorHoveredEntity();
        Vector2f groundLocation = getCursorHoveredGroundLocation();
        Iterator<EntityWrapper> selectedEntitiesIterator = getSelectedEntities().iterator();
        while(selectedEntitiesIterator.hasNext()){
            EntityWrapper selectedEntity = selectedEntitiesIterator.next();
            int[] spells = selectedEntity.getComponent(SpellsComponent.class).getSpellsEntitiesIDs();
            if(spellIndex < spells.length){
                int spellEntity = spells[spellIndex];
                CastTypeComponent.CastType castType = entitySystemAppState.getEntityWorld().getCurrent().getComponent(spellEntity, CastTypeComponent.class).getCastType();
                switch(castType){
                    case SINGLE_TARGET:
                        if(cursorHoveredEntity != -1){
                            sendCommand(new CastSingleTargetSpellCommand(selectedEntity.getId(), spellIndex, cursorHoveredEntity));
                        }
                        break;

                    case SKILLSHOT:
                        if(groundLocation != null){
                            Vector2f direction = groundLocation.subtract(selectedEntity.getComponent(PositionComponent.class).getPosition());
                            sendCommand(new CastLinearSkillshotSpellCommand(selectedEntity.getId(), spellIndex, direction));
                        }
                        break;
                }
            }
        }
    }
    
    private int getCursorHoveredEntity(){
        EntitySystemAppState entitySystemAppState = getAppState(EntitySystemAppState.class);
        CollisionResults entitiesColissionResults = mainApplication.getRayCastingResults_Cursor(entitySystemAppState.getEntitiesNode());
        for(int i=0;i<entitiesColissionResults.size();i++){
            CollisionResult collision = entitiesColissionResults.getCollision(i);
            int entity = entitySystemAppState.getEntity(collision.getGeometry());
            if(entity != -1){
                return entity;
            }
        }
        return -1;
    }
    
    private Vector2f getCursorHoveredGroundLocation(){
        MapAppState mapAppState = getAppState(MapAppState.class);
        CollisionResult groundCollision = mainApplication.getRayCastingResults_Cursor(mapAppState.getMapTerrain().getTerrain()).getClosestCollision();
        if(groundCollision != null){
            return new Vector2f(groundCollision.getContactPoint().getX(), groundCollision.getContactPoint().getZ());
        }
        return null;
    }
    
    private List<EntityWrapper> getSelectedEntities(){
        EntitySystemAppState entitySystemAppState = getAppState(EntitySystemAppState.class);
        return entitySystemAppState.getEntityWorld().getWrapped(entitySystemAppState.getEntityWorld().getCurrent().getEntitiesWithAll(IsSelectedComponent.class));
    }
}
