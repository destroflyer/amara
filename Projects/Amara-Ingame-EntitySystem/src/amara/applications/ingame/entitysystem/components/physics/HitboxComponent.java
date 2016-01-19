/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.physics;

import com.jme3.network.serializing.Serializable;
import amara.libraries.physics.shapes.Shape;

/**
 *
 * @author Philipp
 */
@Serializable
public class HitboxComponent
{
    private Shape shape;

    public HitboxComponent()
    {
        
    }

    public HitboxComponent(Shape shape)
    {
        this.shape = shape.clone();
    }

    public Shape getShape() {
        return shape;
    }
}
