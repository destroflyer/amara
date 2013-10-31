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
public class UnitIntersectionPushComponent {
    Vector2f push;

    public UnitIntersectionPushComponent() {
        
    }
    
    public UnitIntersectionPushComponent(Vector2f push) {
        this.push = push;
    }

    public Vector2f getPush() {
        return push;
    }
}
