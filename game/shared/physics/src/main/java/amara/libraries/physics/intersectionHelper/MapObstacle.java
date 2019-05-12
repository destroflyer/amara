/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.intersectionHelper;

import amara.libraries.physics.shapes.Shape;

/**
 *
 * @author Philipp
 */
public class MapObstacle
{
    private Shape shape;

    public MapObstacle(Shape shape)
    {
        this.shape = shape;
    }
    
    public Shape getShape()
    {
        return shape;
    }
}
