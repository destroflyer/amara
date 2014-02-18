/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.*;
import com.jme3.input.controls.*;
import com.jme3.math.Vector2f;
import amara.Queue;
import amara.engine.input.*;
import amara.engine.input.events.*;

/**
 *
 * @author Carl
 */
public class EventManagerAppState extends ClientBaseAppState implements ActionListener{

    public EventManagerAppState(){
        
    }
    private static final String ACTION_NAME_PREFIX_KEY_PRESSED = "key_pressed_";
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
        registerKeys(new int[]{KeyInput.KEY_Q, KeyInput.KEY_W, KeyInput.KEY_E, KeyInput.KEY_R, KeyInput.KEY_S});
    }
    
    private void registerKeys(int[] keyCodes){
        String[] actionNames = new String[keyCodes.length];
        for(int i=0;i<keyCodes.length;i++){
            actionNames[i] = (ACTION_NAME_PREFIX_KEY_PRESSED + keyCodes[i]);
            mainApplication.getInputManager().addMapping(actionNames[i], new KeyTrigger(keyCodes[i]));
        }
        mainApplication.getInputManager().addListener(this, actionNames);
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

            @Override
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
        else if(actionName.startsWith(ACTION_NAME_PREFIX_KEY_PRESSED) && value){
            int keyCode = Integer.parseInt(actionName.substring(ACTION_NAME_PREFIX_KEY_PRESSED.length()));
            eventQueue.add(new KeyPressedEvent(keyCode));
        }
    }
    
    private Vector2f getMousePosition(){
        return mainApplication.getInputManager().getCursorPosition();
    }

    public Queue<Event> getEventQueue(){
        return eventQueue;
    }
}
