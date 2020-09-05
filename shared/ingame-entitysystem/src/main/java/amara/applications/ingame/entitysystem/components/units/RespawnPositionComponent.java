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
public class RespawnPositionComponent {
    private Vector2f position;

    public RespawnPositionComponent() {
    }

    public RespawnPositionComponent(Vector2f position) {
        this.position = position.clone();
    }

    public Vector2f getPosition() {
        return position;
    }
}