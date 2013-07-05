/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.physics;

/**
 *
 * @author Philipp
 */
public class ScaleComponent
{
    private float scale;

    public ScaleComponent(float scale)
    {
        if(scale <= 0)
        {
            this.scale = 1;
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
