/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.physics;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class ScaleComponent
{
    private float scale;

    public ScaleComponent()
    {
        
    }
    
    public ScaleComponent(float scale)
    {
        if(scale <= 0)
        {
            throw new IllegalArgumentException("scale value must be greater than 0");
            //this.scale = 1;
        }
        else
        {
            this.scale = scale;
        }
    }

    public float getScale()
    {
        return scale;
    }
    
}
