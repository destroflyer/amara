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
public class DirectionComponent
{
    private float radian;

    public DirectionComponent()
    {
        
    }
    
    public DirectionComponent(float radian)
    {
        this.radian = radian;
    }
    
    public DirectionComponent(Vector2f directionVector)
    {
        radian = (float)Math.atan2(directionVector.y, directionVector.x);
    }

    public float getRadian()
    {
        return radian;
    }
    
    public Vector2f getVector()
    {
        return new Vector2f((float)Math.cos(radian), (float)Math.sin(radian));
    }
}
