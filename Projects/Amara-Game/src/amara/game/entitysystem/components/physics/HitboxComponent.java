/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.physics;

import amara.game.entitysystem.systems.physics.shapes.Shape;
import com.jme3.network.serializing.Serializable;

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
