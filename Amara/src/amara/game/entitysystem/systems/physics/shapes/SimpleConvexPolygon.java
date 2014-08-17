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
public class SimpleConvexPolygon extends ConvexShape
{
    private Vector2D[] localPoints, globalPoints;
    private Circle boundCircle;
    private boolean cached = false;

    public SimpleConvexPolygon()
    {
    }

    public SimpleConvexPolygon(Vector2D... localPoints)
    {
        this.localPoints = localPoints;
        globalPoints = new Vector2D[localPoints.length];
        double lengthSquared = localPoints[0].squaredLength();
        for (int i = 1; i < localPoints.length; i++)
        {
            double tmp = localPoints[i].squaredLength();
            if(tmp > lengthSquared) lengthSquared = tmp;
        }
        boundCircle = new Circle(Math.sqrt(lengthSquared));
    }

    public Circle getBoundCircle()
    {
        return boundCircle;
    }

    public Vector2D[] getLocalPoints()
    {
        return localPoints;
    }

    public Vector2D[] getGlobalPoints()
    {
        updateCache();
        return globalPoints;
    }

    @Override
    public void setTransform(Transform2D transform)
    {
        this.transform = transform;
        boundCircle.setTransform(transform);
        cached = false;
    }
    
    private void updateCache()
    {
        if(cached) return;
        for (int i = 0; i < localPoints.length; i++)
        {
            globalPoints[i] = transform.transform(localPoints[i]);
        }
        cached = true;
    }

    @Override
    public boolean contains(Vector2D point)
    {
        updateCache();
        boolean inside = false;
        int i, j;
        for (i = globalPoints.length - 1, j = 0; j < globalPoints.length; i = j++)
        {
            if (((globalPoints[j].getY() > point.getY()) != (globalPoints[i].getY() > point.getY()))
                && (point.getX() < (globalPoints[i].getX() - globalPoints[j].getX()) * (point.getY() - globalPoints[j].getY()) / (globalPoints[i].getY() - globalPoints[j].getY()) + globalPoints[j].getX()))
            {
                inside = !inside;
            }
        }
        return inside;
    }

    @Override
    public void draw(ShapeGraphics graphics, boolean global)
    {
        if(global)
        {
            updateCache();
            graphics.drawPolygon(globalPoints);
        }
        else graphics.drawPolygon(localPoints);
    }

    @Override
    public void fill(ShapeGraphics graphics, boolean global)
    {
        if(global)
        {
            updateCache();
            graphics.fillPolygon(globalPoints);
        }
        else graphics.fillPolygon(localPoints);
    }

    @Override
    public SimpleConvexPolygon clone()
    {
        SimpleConvexPolygon c = new SimpleConvexPolygon(localPoints);
        c.setTransform(transform);
        return c;
    }
    
}
