/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.physics;

import com.jme3.math.Vector2f;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class MapIntersectionPushComponent {
    Vector2f push;

    public MapIntersectionPushComponent() {
        
    }
    
    public MapIntersectionPushComponent(Vector2f push) {
        this.push = push;
    }

    public Vector2f getPush() {
        return push;
    }
}
