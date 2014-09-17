/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.intersectionHelper;

import amara.game.entitysystem.systems.physics.shapes.*;

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
