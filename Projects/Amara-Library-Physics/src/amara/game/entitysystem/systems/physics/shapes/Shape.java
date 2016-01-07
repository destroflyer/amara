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
public abstract class Shape implements BoundAabb//TODO: remove BoundAabb implementation
{
    protected Transform2D transform = Transform2D.Identity;

    public Transform2D getTransform()
    {
        return transform;
    }

    public abstract void setTransform(Transform2D transform);
    
    public abstract boolean contains(Vector2D point);
    public abstract Vector2D getIntersectionResolver(Shape s);
    public boolean intersects(Shape s)
    {
        return Util.INTERSECTORS.intersect(this, s);
    }
    
    public abstract void draw(ShapeGraphics graphics, boolean global);
    public abstract void fill(ShapeGraphics graphics, boolean global);
    
    @Override
    public abstract Shape clone();
    
    public abstract double getMinX();
    public abstract double getMaxX();
    public abstract double getMinY();
    public abstract double getMaxY();
}