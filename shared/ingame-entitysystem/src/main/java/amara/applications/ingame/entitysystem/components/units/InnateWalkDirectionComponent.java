/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.units;

import com.jme3.math.Vector2f;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class InnateWalkDirectionComponent {

    public InnateWalkDirectionComponent() {

    }

    public InnateWalkDirectionComponent(Vector2f direction) {
        this.direction = direction;
    }
    private Vector2f direction;

    public Vector2f getDirection() {
        return direction;
    }
}
