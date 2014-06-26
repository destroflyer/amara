/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics;

import amara.game.entitysystem.systems.physics.shapes.*;
import amara.game.entitysystem.systems.physics.shapes.PolygonMath.*;
import java.util.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Philipp
 */
public class PolyHelper
{
    private static PolygonBuilder builder = new PolygonBuilder();
    
    public static Polygon fromShape(Shape shape)
    {
        if(shape instanceof PolygonShape) return ((PolygonShape)shape).getTransformed();
        SimpleConvex convex;
        if(shape instanceof SimpleConvex)
        {
            convex = (SimpleConvex)shape;
        }
        else if(shape instanceof Circle)
        {
            Circle circle = (Circle)shape;
            convex = new RegularCyclic(circle.getX(), circle.getY(), 8, circle.getBoundRadius());
            convex.getTransform().rotateRadian(Math.PI / 8);
        }
        else throw new NotImplementedException();
        builder.reset();
        builder.nextOutline(false);
        Vector2D[] points = convex.getPoints();
        for (int i = 0; i < points.length; i++)
        {
            builder.add(points[i].getX(), points[i].getY());
        }
        return builder.build(false);
    }
    public static Polygon fromShapes(ArrayList<Shape> shapes)
    {
        if(shapes.isEmpty()) return empty();
        ArrayList<Polygon> polys = new ArrayList<Polygon>();
        for (Shape shape : shapes)
        {
            polys.add(fromShape(shape));
        }
        return Polygon.preSortedUnion(polys);
    }
    
    public static Polygon fromOutline(ArrayList<Point2D> outline)
    {
        builder.reset();
        builder.nextOutline(false);
        for (int i = 0; i < outline.size(); i++)
        {
            builder.add(outline.get(i));
        }
        return builder.build(false);
    }
    
    public static Polygon empty()
    {
        builder.reset();
        return builder.build(false);
    }
    public static Polygon regularCyclic(double x, double y, double radius, int edges)
    {
        Point2D p = new Point2D(radius, 0);
        Transform2D t = new Transform2D(1, Math.PI * 2 / edges, 0, 0);
        builder.reset();
        builder.nextOutline(false);
        for (int i = 0; i < edges; i++)
        {
            builder.add(p.add(x, y));
            p = t.transform(p);
        }
        return builder.build(false);
    }
    
    public static Polygon rectangle(double x, double y, double width, double height)
    {
        builder.reset();
        builder.nextOutline(false);
        builder.add(new Point2D(x, y));
        builder.add(new Point2D(x + width, y));
        builder.add(new Point2D(x + width, y + height));
        builder.add(new Point2D(x, y + height));
        return builder.build(false);
    }
    
    public static Point2D stepwiseOverlap(Polygon map, Polygon poly)
    {
        assert map.intersects(poly);
        boolean solved = false;
        BoundRectangle bounds = poly.boundRectangle();
        double border = 5;
        Polygon flipped = poly.flip();
        Point2D solver = Point2D.Zero;
        while(!solved)
        {
            Polygon masked = bounds.grow(border).toPolygon().intersection(map);
            Polygon result = masked.minkowskiAdd(flipped);
            solver = result.distanceToBorder(Point2D.Zero);
            solved = withinBorder(solver, border);
//            if(solved && result.contains(solver)) throw new Error();
            border += 5;
        }
        //System.out.println("" + solver);
        return solver;
    }
    private static boolean withinBorder(Point2D p, double border)
    {
        return Math.abs(p.getX()) < border && Math.abs(p.getY()) < border;
    }
}
