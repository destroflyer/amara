/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.appstates;

import java.util.Iterator;
import java.util.List;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector2f;
import amara.Queue;
import amara.engine.client.commands.*;
import amara.engine.client.commands.casting.*;
import amara.engine.input.*;
import amara.engine.input.events.*;
import amara.game.entitysystem.EntityWrapper;
import amara.game.entitysystem.components.physics.PositionComponent;
import amara.game.entitysystem.components.selection.IsSelectedComponent;

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
                    
                    case Middle:
                        Vector2f groundLocation = getCursorHoveredGroundLocation();
                        Iterator<EntityWrapper> selectedEntitiesIterator = getSelectedEntities().iterator();
                        while(selectedEntitiesIterator.hasNext()){
                            EntityWrapper selectedEntity = selectedEntitiesIterator.next();
                            Vector2f direction = groundLocation.subtract(selectedEntity.getComponent(PositionComponent.class).getPosition());
                            sendCommand(new CastLinearSkillshotSpellCommand(1, direction));
                        }
                        break;
                    
                    case Right:
                        int entityToAttack = getCursorHoveredEntity();
                        if(entityToAttack != -1){
                            sendCommand(new CastSingleTargetSpellCommand(0, entityToAttack));
                        }
                        else{
                            sendCommand(new MoveCommand(getCursorHoveredGroundLocation()));
                        }
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
        return new Vector2f(groundCollision.getContactPoint().getX(), groundCollision.getContactPoint().getZ());
    }
    
    private List<EntityWrapper> getSelectedEntities(){
        EntitySystemAppState entitySystemAppState = getAppState(EntitySystemAppState.class);
        return entitySystemAppState.getEntityWorld().getWrapped(entitySystemAppState.getEntityWorld().getCurrent().getEntitiesWithAll(IsSelectedComponent.class));
    }
}
