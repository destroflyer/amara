/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes;

import amara.game.entitysystem.systems.physics.shapes.PolygonMath.*;
import java.util.*;
import sun.reflect.generics.reflectiveObjects.*;

/**
 *
 * @author Philipp
 */
public class ShapeUtil
{
    public static boolean intersect(Shape a, Shape b)
    {
        if(a instanceof Circle)
        {
            return intersect((Circle)a, b);
        }
        if(a instanceof SimpleConvexPolygon)
        {
            return intersect((SimpleConvexPolygon)a, b);
        }
        if(a instanceof PolygonShape)
        {
            return intersect((PolygonShape)a, b);
        }
        throw new NotImplementedException();
    }
    public static boolean intersect(Circle a, Shape b)
    {
        if(b instanceof Circle)
        {
            return intersect(a, (Circle)b);
        }
        if(b instanceof SimpleConvexPolygon)
        {
            return intersect(a, (SimpleConvexPolygon)b);
        }
        if(b instanceof PolygonShape)
        {
            return intersect(a, (PolygonShape)b);
        }
        throw new NotImplementedException();
    }
    public static boolean intersect(SimpleConvexPolygon a, Shape b)
    {
        if(b instanceof Circle)
        {
            return intersect((Circle)b, a);
        }
        if(b instanceof SimpleConvexPolygon)
        {
            return intersect(a, (SimpleConvexPolygon)b);
        }
        if(b instanceof PolygonShape)
        {
            return intersect(a, (PolygonShape)b);
        }
        throw new NotImplementedException();
    }
    public static boolean intersect(PolygonShape a, Shape b)
    {
        if(b instanceof Circle)
        {
            return intersect((Circle)b, a);
        }
        if(b instanceof SimpleConvexPolygon)
        {
            return intersect((SimpleConvexPolygon)b, a);
        }
        if(b instanceof PolygonShape)
        {
            return intersect((PolygonShape)b, a);
        }
        throw new NotImplementedException();
    }
    
    public static boolean intersect(Circle a, Circle b)
    {
        return a.getGlobalPosition().squaredDistance(b.getGlobalPosition()) < Util.squared(a.getGlobalRadius() + b.getGlobalRadius());
    }
    public static boolean intersect(Circle a, SimpleConvexPolygon b)
    {
        if(intersect(a, b.getBoundCircle()))
        {
            if(outlineCircleIntersect(a.getGlobalPosition(), Util.squared(a.getGlobalRadius()), b.getGlobalPoints()))
            {
                return true;
            }
            if(b.contains(a.getGlobalPosition()))
            {
                return true;
            }
        }
        return false;
    }
    public static boolean intersect(Circle a, PolygonShape b)
    {
        Polygon poly = b.getGlobalPolygon();
        Vector2D center = a.getGlobalPosition();
            
        if(poly.contains(center)) return true;
        double radiusSquared = Util.squared(a.getGlobalRadius());
        
        for (ArrayList<Vector2D> points : poly.outlines())
        {
            if(outlineCircleIntersect(center, radiusSquared, points.toArray(new Vector2D[0]))) return true;
        }
        return false;
    }
    private static boolean outlineCircleIntersect(Vector2D center, double radiusSquared, Vector2D[] poly)
    {
        for (int i = 0; i < poly.length; i++)
        {
            int j = (i + 1) % poly.length;

            if(Vector2DUtil.fromLineSegmentToPoint(poly[i], poly[j], center).squaredLength() < radiusSquared) return true;
        }
        return false;
    }
    
    public static boolean intersect(SimpleConvexPolygon a, SimpleConvexPolygon b)
    {
        if(intersect(a.getBoundCircle(), b.getBoundCircle()))
        {
            return !(seperated(a.getGlobalPoints(), b.getGlobalPoints()) || seperated(b.getGlobalPoints(), a.getGlobalPoints()));
        }
        return false;
    }
    private static boolean seperated(Vector2D[] c1, Vector2D[] c2)
    {
        int b;
        boolean seperated;
        for(int a = 0; a < c1.length; a++)
        {
            seperated = true;
            b = (a + 1) % c1.length;
            for(int i = 0; seperated && i < c2.length; i++)
            {
                if(Vector2DUtil.lineSide(c2[i], c1[a], c1[b]) < 0)
                {
                    seperated = false;
                    break;
                }
            }
            if(seperated) return true;
        }
        return false;
    }
    public static boolean intersect(SimpleConvexPolygon a, PolygonShape b)
    {
        if(seperated(a.getGlobalPoints(), b.getGlobalPolygon().points().toArray(new Vector2D[0]))) return false;
        
        Vector2D[] points = a.getGlobalPoints();
        PolygonBuilder builder = new PolygonBuilder();
        builder.nextOutline(false);
        for (int i = 0; i < points.length; i++)
        {
            builder.add(points[i]);
        }
        Polygon c = builder.build(false);
        return b.getGlobalPolygon().intersects(c);
    }
    
    public static boolean intersect(PolygonShape a, PolygonShape b)
    {
        return a.getGlobalPolygon().intersects(b.getGlobalPolygon());
    }
    
    
    public static Vector2D intersectionResolver(ConvexShape a, ConvexShape b)
    {
        assert intersect(a, b);
        if(a instanceof Circle)
        {
            return intersectionResolver((Circle)a, b);
        }
        if(a instanceof SimpleConvexPolygon)
        {
            return intersectionResolver((SimpleConvexPolygon)a, b);
        }
        throw new NotImplementedException();
    }
    public static Vector2D intersectionResolver(Circle a, ConvexShape b)
    {
        if(b instanceof Circle)
        {
            return intersectionResolver(a, (Circle)b);
        }
        if(b instanceof SimpleConvexPolygon)
        {
            return intersectionResolver(a, (SimpleConvexPolygon)b);
        }
        throw new NotImplementedException();
    }
    public static Vector2D intersectionResolver(SimpleConvexPolygon a, ConvexShape b)
    {
        if(b instanceof Circle)
        {
            return intersectionResolver((Circle)b, a).inverse();
        }
        if(b instanceof SimpleConvexPolygon)
        {
            return intersectionResolver(a, (SimpleConvexPolygon)b);
        }
        throw new NotImplementedException();
    }
    
    public static Vector2D intersectionResolver(Circle a, Circle b)
    {
        Vector2D delta = b.getGlobalPosition().sub(a.getGlobalPosition());
        return intersectionResolverHelper(delta, a.getGlobalRadius() + b.getGlobalRadius());
    }
    private static Vector2D intersectionResolverHelper(Vector2D delta, double distance)
    {
        assert delta.length() <= distance: delta.length() + " / " + distance;
        double len = delta.length();
        return delta.mult((len - distance) / len);
    }
    public static Vector2D intersectionResolver(Circle a, SimpleConvexPolygon b)
    {
        Vector2D center = a.getGlobalPosition();
        double radius = a.getGlobalRadius();
        
        Vector2D[] points = b.getGlobalPoints();
        
        Vector2D resolver = Vector2D.Zero;
        double length = Double.POSITIVE_INFINITY;
        
        for (int i = 0; i < points.length; i++)
        {
            int j = (i + 1) % points.length;
            
            Vector2D dir = points[j].sub(points[i]);
            
            double c = points[i].dot(dir);
            double d = points[j].dot(dir);
            double e = center.dot(dir);
            assert c < d;
            if(e < d)
            {
                if(c < e)
                {
                    dir = dir.unit().rightHand();
                    assert Util.withinEpsilon(dir.dot(points[i]) - dir.dot(points[j]));
                    double distance = center.dot(dir) - points[i].dot(dir);
                    if(radius < distance) continue;
                    if(0 <= distance) return dir.mult(radius - distance);
                    if(radius - distance < length)
                    {
                        length = radius - distance;
                        resolver = dir.mult(length);
                    }
                }
            }
            else
            {
                int k = (i + 2) % points.length;
                
                dir = points[k].sub(points[j]);
                
                c = points[j].dot(dir);
                e = center.dot(dir);
                if(e <= c)
                {
                    dir = center.sub(points[j]).unit();
                    double distance = center.dot(dir) - points[j].dot(dir);
                    return dir.mult(radius - distance);
                }
            }
        }
        assert !resolver.withinEpsilon();
        return resolver;
    }
    public static Vector2D intersectionResolver(SimpleConvexPolygon a, SimpleConvexPolygon b)
    {
        return pusher(a.getGlobalPoints(), b.getGlobalPoints());
    }
    public static Vector2D pusher(Vector2D[] convex1, Vector2D[] convex2)
    {
        Vector2D v1 = minPenetration(convex1, convex2);
        Vector2D v2 = minPenetration(convex2, convex1);
        if(v2.squaredLength() < v1.squaredLength()) return v2.inverse();
        return v1;
    }
    private static Vector2D minPenetration(Vector2D[] c1, Vector2D[] c2)
    {
        int b;
        Vector2D tmp;
        Vector2D outer = new Vector2D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        Vector2D inner;
        for(int a = 0; a < c1.length; a++)
        {
            b = (a + 1) % c1.length;
            inner = Vector2D.Zero;
            for(int i = 0; i < c2.length; i++)
            {
                if(Vector2DUtil.lineSide(c2[i], c1[a], c1[b]) < 0)
                {
                    tmp = Vector2DUtil.fromLineSegmentToPoint(c1[a], c1[b], c2[i]);
                    if(inner.squaredLength() < tmp.squaredLength()) inner = tmp;
                }
            }
            if(outer.squaredLength() > inner.squaredLength()) outer = inner;
        }
        return outer;
    }
}