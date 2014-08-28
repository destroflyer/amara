/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes;

import amara.game.entitysystem.systems.physics.shapes.PolygonMath.Polygon;
import com.jme3.network.serializing.*;
import java.util.*;

/**
 *
 * @author Philipp
 */
@Serializable
public class PolygonShape extends Shape
{
    private Polygon localPolygon, globalPolygon = null;

    public PolygonShape()
    {
    }

    public PolygonShape(Polygon polygon)
    {
        localPolygon = polygon;
    }

    @Override
    public void setTransform(Transform2D transform)
    {
        globalPolygon = null;
        this.transform = transform;
    }
    
    private void updateCache()
    {
        if(globalPolygon == null)
        {
            globalPolygon = localPolygon.transform(transform);
        }
    }

    @Override
    public boolean contains(Vector2D point)
    {
        updateCache();
        return globalPolygon.contains(point);
    }

    @Override
    public void draw(ShapeGraphics graphics, boolean global)
    {
        Polygon polygon;
        if(global)
        {
            updateCache();
            polygon = globalPolygon;
        }
        else polygon = localPolygon;
        
        for (ArrayList<Vector2D> poly : polygon.outlines())
        {
            graphics.drawPolygon(poly.toArray(new Vector2D[0]));
        }
    }

    @Override
    public void fill(ShapeGraphics graphics, boolean global)
    {
        Polygon polygon;
        if(global)
        {
            updateCache();
            polygon = globalPolygon;
        }
        else polygon = localPolygon;
        
        for (ArrayList<Vector2D> poly : polygon.cutPolys())
        {
            graphics.fillPolygon(poly.toArray(new Vector2D[0]));
        }
    }

    public Polygon getLocalPolygon()
    {
        return localPolygon;
    }

    public Polygon getGlobalPolygon()
    {
        return globalPolygon;
    }

    @Override
    public PolygonShape clone()
    {
        PolygonShape p = new PolygonShape(localPolygon);
        p.setTransform(transform);
        return p;
    }

    @Override
    public Vector2D getIntersectionResolver(Shape s)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getMinX()
    {
        return Double.NEGATIVE_INFINITY;
    }

    @Override
    public double getMaxX()
    {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double getMinY()
    {
        return Double.NEGATIVE_INFINITY;
    }

    @Override
    public double getMaxY()
    {
        return Double.POSITIVE_INFINITY;
    }
    
}
