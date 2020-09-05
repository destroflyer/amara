/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.shapes;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class Rectangle extends SimpleConvexPolygon
{

    public Rectangle()
    {
    }
    
    public Rectangle(double width, double height) {
        this(Vector2D.Zero, width, height);
    }
    public Rectangle(double x, double y, double width, double height) {
        this(new Vector2D(x, y), width, height);
    }

    public Rectangle(Vector2D position, double width, double height) {
        super(new Vector2D[] {
            new Vector2D(-width / 2, height / 2).add(position),
            new Vector2D(-width / 2, -height / 2).add(position),
            new Vector2D(width / 2, -height / 2).add(position),
            new Vector2D(width / 2, height / 2).add(position)
        });
    }
    
    
}
