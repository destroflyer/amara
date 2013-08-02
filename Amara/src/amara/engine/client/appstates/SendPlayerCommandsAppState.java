/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.appstates;

import java.util.Iterator;
import com.jme3.math.Vector2f;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import amara.Queue;
import amara.engine.client.commands.*;
import amara.engine.input.*;
import amara.engine.input.events.*;
import amara.game.entitysystem.components.selection.IsSelectableComponent;

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
        EntitySystemAppState entitySystemAppState = getAppState(EntitySystemAppState.class);
        Queue<Event> eventQueue = getAppState(EventManagerAppState.class).getEventQueue();
        Iterator<Event> eventsIterator = eventQueue.getIterator();
        while(eventsIterator.hasNext()){
            Event event = eventsIterator.next();
            if(event instanceof MouseClickEvent){
                MouseClickEvent mouseClickEvent = (MouseClickEvent) event;
                switch(mouseClickEvent.getButton()){
                    case Left:
                        boolean wasEntitySelected = false;
                        CollisionResults entitiesColissionResults = mainApplication.getRayCastingResults_Cursor(entitySystemAppState.getEntitiesNode());
                        for(int i=0;i<entitiesColissionResults.size();i++){
                            CollisionResult collision = entitiesColissionResults.getCollision(i);
                            int entity = entitySystemAppState.getEntity(collision.getGeometry());
                            if(entity != -1){
                                sendCommand(new SelectionCommand(entity));
                                wasEntitySelected = true;
                                break;
                            }
                        }
                        if(!wasEntitySelected){
                            sendCommand(new DeselectionCommand());
                        }
                        break;
                    
                    case Right:
                        MapAppState mapAppState = getAppState(MapAppState.class);
                        CollisionResult groundCollision = mainApplication.getRayCastingResults_Cursor(mapAppState.getMapTerrain().getTerrain()).getClosestCollision();
                        Vector2f targetLocation = new Vector2f(groundCollision.getContactPoint().getX(), groundCollision.getContactPoint().getZ());
                        sendCommand(new MoveCommand(targetLocation));
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
}
