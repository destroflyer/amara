package amara.libraries.applications.display.appstates;

import amara.core.Queue;
import amara.core.input.*;
import amara.core.input.events.*;
import amara.libraries.applications.display.DisplayApplication;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.*;
import com.jme3.input.controls.*;
import com.jme3.math.Vector2f;

public class EventManagerAppState extends BaseDisplayAppState<DisplayApplication> implements ActionListener {

    private static final String ACTION_NAME_PREFIX_KEY_PRESSED = "key_pressed_";
    private Queue<Event> eventQueue = new Queue<>();
    private boolean isModifierControlLeft;
    private boolean isModifierControlRight;
    private boolean isModifierShiftLeft;
    private boolean isModifierShiftRight;

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);
        mainApplication.getInputManager().addMapping("mouse_click_left", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        mainApplication.getInputManager().addMapping("mouse_click_middle", new MouseButtonTrigger(MouseInput.BUTTON_MIDDLE));
        mainApplication.getInputManager().addMapping("mouse_click_right", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        mainApplication.getInputManager().addListener(this, "mouse_click_left", "mouse_click_middle", "mouse_click_right");
        registerKeys(Keys.KEY_CODES);
    }

    private void registerKeys(int[] keyCodes) {
        String[] actionNames = new String[keyCodes.length];
        for (int i = 0; i < keyCodes.length; i++) {
            actionNames[i] = (ACTION_NAME_PREFIX_KEY_PRESSED + keyCodes[i]);
            mainApplication.getInputManager().addMapping(actionNames[i], new KeyTrigger(keyCodes[i]));
        }
        mainApplication.getInputManager().addListener(this, actionNames);
    }

    @Override
    public void cleanup() {
        super.cleanup();
        mainApplication.getInputManager().removeListener(this);
    }

    @Override
    public void update(float lastTimePerFrame) {
        super.update(lastTimePerFrame);
        mainApplication.enqueue(() -> eventQueue.clear());
    }

    @Override
    public void onAction(String actionName, boolean value, float lastTimePerFrame) {
        if (actionName.equals("mouse_click_left") && value) {
            eventQueue.add(new MouseClickEvent(MouseClickEvent.Button.Left, getMousePosition()));
        } else if (actionName.equals("mouse_click_middle") && value) {
            eventQueue.add(new MouseClickEvent(MouseClickEvent.Button.Middle, getMousePosition()));
        } else if (actionName.equals("mouse_click_right") && value) {
            eventQueue.add(new MouseClickEvent(MouseClickEvent.Button.Right, getMousePosition()));
        } else if (actionName.startsWith(ACTION_NAME_PREFIX_KEY_PRESSED)) {
            int keyCode = Integer.parseInt(actionName.substring(ACTION_NAME_PREFIX_KEY_PRESSED.length()));
            updateModifiers(keyCode, value);
            eventQueue.add(new KeyEvent(keyCode, value, (isModifierControlLeft || isModifierControlRight), (isModifierShiftLeft || isModifierShiftRight)));
        }
    }

    private void updateModifiers(int keyCode, boolean value) {
        switch (keyCode) {
            case KeyInput.KEY_LCONTROL:
                isModifierControlLeft = value;
                break;
            case KeyInput.KEY_RCONTROL:
                isModifierControlRight = value;
                break;
            case KeyInput.KEY_LSHIFT:
                isModifierShiftLeft = value;
                break;
            case KeyInput.KEY_RSHIFT:
                isModifierShiftRight = value;
                break;
        }
    }

    private Vector2f getMousePosition() {
        return mainApplication.getInputManager().getCursorPosition();
    }

    public Queue<Event> getEventQueue() {
        return eventQueue;
    }
}
