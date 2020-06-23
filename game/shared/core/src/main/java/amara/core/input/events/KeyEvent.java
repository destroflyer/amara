/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.core.input.events;

import amara.core.input.Event;

/**
 *
 * @author Carl
 */
public class KeyEvent extends Event {

    public KeyEvent(int keyCode, boolean pressed) {
        this.keyCode = keyCode;
        this.pressed = pressed;
    }
    private int keyCode;
    private boolean pressed;

    public int getKeyCode() {
        return keyCode;
    }

    public boolean isPressed() {
        return pressed;
    }
}
