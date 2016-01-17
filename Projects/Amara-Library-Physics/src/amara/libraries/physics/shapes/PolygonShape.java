/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.shapes;

import java.util.*;
import com.jme3.network.serializing.*;
import amara.libraries.physics.PolyHelper;
import amara.libraries.physics.shapes.PolygonMath.*;

/**
 *
 * @author Philipp
 */
@Serializable
public class PolygonShape extends Shape
{
    private Polygon localPolygon;
    private transient Polygon globalPolygon = null;
    private transient BoundRectangle aabb = null;

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
        updateCache();
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
        System.out.println("Warning, slow function, do not use!");
        Polygon p = PolyHelper.fromShape(s).flip();
        p = globalPolygon.minkowskiAdd(p);
        return p.distanceToBorder(Vector2D.Zero);
    }
    
    private void updateAabb()
    {
        if(aabb == null)
        {
            updateCache();
            aabb = globalPolygon.boundRectangle();
        }
    }

    @Override
    public double getMinX()
    {
        if(localPolygon.isInfinite()) return Double.NEGATIVE_INFINITY;
        updateAabb();
        return aabb.getMinX();
    }

    @Override
    public double getMaxX()
    {
        if(localPolygon.isInfinite()) return Double.POSITIVE_INFINITY;
        updateAabb();
        return aabb.getMaxX();
    }

    @Override
    public double getMinY()
    {
        if(localPolygon.isInfinite()) return Double.NEGATIVE_INFINITY;
        updateAabb();
        return aabb.getMinY();
    }

    @Override
    public double getMaxY()
    {
        if(localPolygon.isInfinite()) return Double.POSITIVE_INFINITY;
        updateAabb();
        return aabb.getMaxY();
    }
    
}
