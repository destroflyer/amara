/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes;

import amara.game.entitysystem.systems.physics.intersection.BoundAabb;
import com.jme3.network.serializing.*;

/**
 *
 * @author Philipp
 */
@Serializable
public abstract class ConvexShape extends Shape implements BoundAabb
{
    public Vector2D getIntersectionResolver(Shape s)
    {
        return getIntersectionResolver((ConvexShape)s);
    }
    public Vector2D getIntersectionResolver(ConvexShape s)
    {
        assert Util.INTERSECTORS.intersect(this, s);
        return Util.INTERSECTORS.resolveVector(this, s);
    }
    
    public abstract Vector2D calcLocalCentroid();
    
    public abstract double calcLocalArea();
    
    public abstract Circle getBoundCircle();
    
    @Override
    public abstract ConvexShape clone();
    
    public double getMinX()
    {
        return getBoundCircle().getGlobalPosition().getX() - getBoundCircle().getGlobalRadius();
    }
    public double getMaxX()
    {
        return getBoundCircle().getGlobalPosition().getX() + getBoundCircle().getGlobalRadius();
    }
    public double getMinY()
    {
        return getBoundCircle().getGlobalPosition().getY() - getBoundCircle().getGlobalRadius();
    }
    public double getMaxY()
    {
        return getBoundCircle().getGlobalPosition().getY() + getBoundCircle().getGlobalRadius();
    }
}
