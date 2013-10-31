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
public class PositionComponent
{
    private Vector2f position;

    public PositionComponent()
    {
        
    }
    
    public PositionComponent(Vector2f position)
    {
        this.position = position.clone();
    }

    public Vector2f getPosition()
    {
        return position;
    }
}