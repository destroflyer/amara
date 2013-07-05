/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics;

import intersections.HasShape;
import shapes.Shape;

/**
 *
 * @author Philipp
 */
public class Hitbox implements HasShape
{
    private int id;
    private Shape shape;

    public Hitbox(int id, Shape shape)
    {
        this.id = id;
        this.shape = shape;
    }

    public int getId()
    {
        return id;
    }

    public Shape getShape()
    {
        return shape;
    }
}
