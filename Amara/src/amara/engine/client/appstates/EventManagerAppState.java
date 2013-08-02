/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector2f;
import amara.Queue;
import amara.engine.input.*;
import amara.engine.input.events.*;

/**
 *
 * @author Carl
 */
public class EventManagerAppState extends BaseAppState implements ActionListener{

    public EventManagerAppState(){
        
    }
    private Queue<Event> eventQueue = new Queue<Event>();
    
    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        mainApplication.getFlyByCamera().setEnabled(false);
        mainApplication.getInputManager().addMapping("mouse_click_left", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        mainApplication.getInputManager().addMapping("mouse_click_middle", new MouseButtonTrigger(MouseInput.BUTTON_MIDDLE));
        mainApplication.getInputManager().addMapping("mouse_click_right", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        mainApplication.getInputManager().addListener(this, new String[]{
            "mouse_click_left","mouse_click_middle","mouse_click_right"
        });
    }

    @Override
    public void cleanup(){
        super.cleanup();
        mainApplication.getInputManager().removeListener(this);
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        mainApplication.enqueueTask(new Runnable(){

            public void run(){
                eventQueue.clear();
            }
        });
    }

    @Override
    public void onAction(String actionName, boolean value, float lastTimePerFrame){
        if(actionName.equals("mouse_click_left") && value){
            eventQueue.add(new MouseClickEvent(MouseClickEvent.Button.Left, getMousePosition()));
        }
        else if(actionName.equals("mouse_click_middle") && value){
            eventQueue.add(new MouseClickEvent(MouseClickEvent.Button.Middle, getMousePosition()));
        }
        else if(actionName.equals("mouse_click_right") && value){
            eventQueue.add(new MouseClickEvent(MouseClickEvent.Button.Right, getMousePosition()));
        }
    }
    
    private Vector2f getMousePosition(){
        return mainApplication.getInputManager().getCursorPosition();
    }

    public Queue<Event> getEventQueue(){
        return eventQueue;
    }
}
