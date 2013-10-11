/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.physics;

import com.jme3.math.Vector2f;

/**
 *
 * @author Philipp
 */
public class MovementComponent {
    Vector2f push;

    public MovementComponent(Vector2f push) {
        this.push = push;
    }

    public Vector2f getPush() {
        return push;
    }
}
