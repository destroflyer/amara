/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes;

import com.jme3.network.serializing.*;

/**
 *
 * @author Philipp
 */
@Serializable
public class Circle extends ConvexShape
{
    private Vector2D localPosition;
    private double localRadius;
    
    private transient Vector2D globalPosition = null;
    private transient double globalRadius = Double.NaN;

    public Circle()
    {
    }

    public Circle(Vector2D localPosition, double localRadius)
    {
        this.localPosition = localPosition;
        this.localRadius = localRadius;
    }
    public Circle(double x, double y, double localRadius)
    {
        this.localPosition = new Vector2D(x, y);
        this.localRadius = localRadius;
    }
    public Circle(double localRadius)
    {
        this.localPosition = Vector2D.Zero;
        this.localRadius = localRadius;
    }

    @Override
    public void setTransform(Transform2D transform)
    {
        globalPosition = null;
        this.transform = transform;
    }

    public Vector2D getLocalPosition()
    {
        return localPosition;
    }

    public double getLocalRadius()
    {
        return localRadius;
    }

    public Vector2D getGlobalPosition()
    {
        updateCache();
        return globalPosition;
    }

    public double getGlobalRadius()
    {
        updateCache();
        return globalRadius;
    }
    
    private void updateCache()
    {
        if(globalPosition == null)
        {
            globalPosition = transform.transform(localPosition);
            globalRadius = localRadius * transform.extractScale();
        }
    }

    @Override
    public boolean contains(Vector2D point)
    {
        updateCache();
        return point.squaredDistance(globalPosition) < globalRadius * globalRadius;
    }

    @Override
    public void draw(ShapeGraphics graphics, boolean global)
    {
        if(global)
        {
            updateCache();
            graphics.drawCircle(globalPosition, globalRadius);
        }
        else graphics.drawCircle(localPosition, localRadius);
    }

    @Override
    public void fill(ShapeGraphics graphics, boolean global)
    {
        if(global)
        {
            updateCache();
            graphics.fillCircle(globalPosition, globalRadius);
        }
        else graphics.fillCircle(localPosition, localRadius);
    }

    @Override
    public Circle clone()
    {
        Circle c = new Circle(localPosition, localRadius);
        c.setTransform(transform);
        return c;
    }

    @Override
    public Circle getBoundCircle()
    {
        return this;
    }

    @Override
    public Vector2D calcLocalCentroid()
    {
        return localPosition;
    }

    @Override
    public double calcLocalArea()
    {
        return Math.PI * localRadius * localRadius;
    }
}
