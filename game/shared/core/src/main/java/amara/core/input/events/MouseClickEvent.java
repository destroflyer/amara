/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.core.input.events;

import com.jme3.math.Vector2f;
import amara.core.input.Event;

/**
 *
 * @author Carl
 */
public class MouseClickEvent extends Event{

    public MouseClickEvent(Button button, Vector2f position){
        this.button = button;
        this.position = position;
    }
    public enum Button{
        Left,
        Middle,
        Right
    }
    private Button button;
    private Vector2f position;

    public Button getButton(){
        return button;
    }

    public Vector2f getPosition(){
        return position;
    }
}
