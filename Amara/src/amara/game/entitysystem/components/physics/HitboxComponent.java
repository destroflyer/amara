/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.physics;

import shapes.Shape;

/**
 *
 * @author Philipp
 */
public class HitboxComponent
{
    private Shape shape;

    public HitboxComponent(Shape shape)
    {
        this.shape = shape.clone();
    }

    public Shape getShape() {
        return shape;
    }
}
