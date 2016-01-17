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
public class KeyPressedEvent extends Event{

    public KeyPressedEvent(int keyCode){
        this.keyCode = keyCode;
    }
    private int keyCode;

    public int getKeyCode(){
        return keyCode;
    }
}
