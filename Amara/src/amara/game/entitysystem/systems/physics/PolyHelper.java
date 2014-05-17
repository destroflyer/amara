/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics;

import amara.game.entitysystem.systems.physics.shapes.*;
import amara.game.entitysystem.systems.physics.shapes.PolygonMath.Public.*;
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
        SimpleConvex convex;
        if(shape instanceof SimpleConvex)
        {
            convex = (SimpleConvex)shape;
        }
        else if(shape instanceof Circle)
        {
            Circle circle = (Circle)shape;
            convex = new RegularCyclic(circle.getX(), circle.getY(), 8, circle.getBoundRadius());
        }
        else throw new NotImplementedException();
        builder.reset();
        builder.nextOutline(false);
        Vector2D[] points = convex.getPoints();
        for (int i = 0; i < points.length; i++)
        {
            builder.add(points[i].getX(), points[(i + 1) % points.length].getY());
        }
        return builder.build(false);
    }
    
    public static Point2D stepwiseOverlap(Polygon map, Polygon poly)
    {
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
            if(solved && result.contains(solver)) throw new Error();
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
