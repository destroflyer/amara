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
public class MovementSpeedComponent
{
    private Vector2f speed;

    public MovementSpeedComponent(Vector2f speed)
    {
        this.speed = speed.clone();
    }

    public Vector2f getSpeed()
    {
        return speed;
    }
}
