/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes;

import amara.game.entitysystem.systems.physics.PolyHelper;
import amara.game.entitysystem.systems.physics.shapes.PolygonMath.*;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class PolygonShape extends Shape
{
    private Polygon poly, cache;

    public PolygonShape() {
    }

    public PolygonShape(Polygon poly)
    {
        if(poly.isInfinite()) throw new Error("does not support infinite polys");
        this.poly = poly;
        calcBoundRadius();
    }
    
    private void calcBoundRadius()
    {
        for (Point2D point : poly.points())
        {
            if(point.length() > baseBoundRadius) baseBoundRadius = point.length();
        }
    }
    
    public Polygon getTransformed()
    {
        updateShape();
        return cache;
    }
    
    @Override
    public boolean contains(Vector2D point) {
        return poly.contains(new Point2D(point.getX(), point.getY()));
    }

    @Override
    public boolean intersects(Shape s)
    {
        return PolyHelper.fromShape(s).intersects(getTransformed());
    }

    @Override
    public boolean intersects(SimpleConvex c) {
        return PolyHelper.fromShape(c).intersects(getTransformed());
    }

    @Override
    public boolean intersects(Circle c) {
        return PolyHelper.fromShape(c).intersects(getTransformed());
    }

    @Override
    public Vector2D getResolveVector(Shape shape) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Vector2D getResolveVector(SimpleConvex convex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Vector2D getResolveVector(Circle circle) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void updateTransform()
    {
        cache = poly.transform(new Transform2D(transform.getScale(), transform.getRadian(), transform.getX(), transform.getY()));
    }

    @Override
    public Bounds getScalarProjectOnto(Vector2D axis) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PolygonShape clone()
    {
        PolygonShape clone = new PolygonShape(poly);
        clone.transform = transform.clone();
        return clone;
    }
    
}
