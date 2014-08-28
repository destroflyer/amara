/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes;

import java.util.*;

/**
 *
 * @author Philipp
 */
public class Vector2DUtil {
    public static Vector2D lineIntersectionPoint(Vector2D p1, Vector2D p2, Vector2D p3, Vector2D p4)
    {
        double denom = (p4.getY() - p3.getY()) * (p2.getX() - p1.getX()) - (p4.getX() - p3.getX()) * (p2.getY() - p1.getY());
        if (Util.withinEpsilon(denom)) return null;

        double nomT = (p3.getX() - p1.getX()) * (p4.getY() - p3.getY()) + (p1.getY() - p3.getY()) * (p4.getX() - p3.getX());
        double t = nomT / denom;

        return new Vector2D(p1.getX() + t * (p2.getX() - p1.getX()), p1.getY() + t * (p2.getY() - p1.getY()));
    }
    public static boolean segmentAaBbCheck(Vector2D a, Vector2D b, Vector2D c, Vector2D d)
    {
        double aMin, aMax, bMin, bMax;
        if(a.getX() < b.getX())
        {
            aMin = a.getX();
            aMax = b.getX();
        }
        else
        {
            aMin = b.getX();
            aMax = a.getX();
        }
        if(c.getX() < d.getX())
        {
            bMin = c.getX();
            bMax = d.getX();
        }
        else
        {
            bMin = d.getX();
            bMax = c.getX();
        }
        if(aMax <= bMin || bMax <= aMin) return false;
        
        if(a.getY() < b.getY())
        {
            aMin = a.getY();
            aMax = b.getY();
        }
        else
        {
            aMin = b.getY();
            aMax = a.getY();
        }
        if(c.getY() < d.getY())
        {
            bMin = c.getY();
            bMax = d.getY();
        }
        else
        {
            bMin = d.getY();
            bMax = c.getY();
        }
        return aMax > bMin && bMax > aMin;
    }
    public static Vector2D lineSegmentIntersectionPointWithoutCorners(Vector2D p1, Vector2D p2, Vector2D p3, Vector2D p4)
    {
        assert(!p1.equals(p2));
        assert(!p3.equals(p4));
        if (p1.withinEpsilon(p3)) return null;
        if (p1.withinEpsilon(p4)) return null;
        if (p2.withinEpsilon(p3)) return null;
        if (p2.withinEpsilon(p4)) return null;

        double denom = (p4.getY() - p3.getY()) * (p2.getX() - p1.getX()) - (p4.getX() - p3.getX()) * (p2.getY() - p1.getY());
        if (Util.withinEpsilon(denom)) return null;

        double nomT = (p3.getX() - p1.getX()) * (p4.getY() - p3.getY()) + (p1.getY() - p3.getY()) * (p4.getX() - p3.getX());
        double t = nomT / denom;
        double T = ((p3.getX() - p1.getX()) * (p4.getY() - p3.getY()) + (p1.getY() - p3.getY()) * (p4.getX() - p3.getX())) / ((p4.getY() - p3.getY()) * (p2.getX() - p1.getX()) - (p4.getX() - p3.getX()) * (p2.getY() - p1.getY()));

        //boolean test1 = !(0 < t && t < 1);
        boolean test2 = !(Util.aboveEpsilon(t) && Util.belowEpsilon(t - 1));

        if (test2) return null;
        assert(T == t);

        double nomU = (p1.getX() - p3.getX()) * (p2.getY() - p1.getY()) + (p3.getY() - p1.getY()) * (p2.getX() - p1.getX());
        double u = -nomU / denom;

        //test1 = !(0 < u && u < 1);
        test2 = !(Util.aboveEpsilon(u) && Util.belowEpsilon(u - 1));

        if (test2) return null;

        Vector2D result = new Vector2D(p1.getX() + t * (p2.getX() - p1.getX()), p1.getY() + t * (p2.getY() - p1.getY()));
        if (result.withinEpsilon(p1)) return null;
        if (result.withinEpsilon(p2)) return null;
        if (result.withinEpsilon(p3)) return null;
        if (result.withinEpsilon(p4)) return null;

        assert(Math.min(p1.getX(), p2.getX()) <= result.getX() + Util.Epsilon);
        assert(result.getX() <= Math.max(p1.getX(), p2.getX()) + Util.Epsilon);
        assert(Math.min(p1.getY(), p2.getY()) <= result.getY() + Util.Epsilon);
        assert(result.getY() <= Math.max(p1.getY(), p2.getY()) + Util.Epsilon);

        assert(Math.min(p3.getX(), p4.getX()) <= result.getX() + Util.Epsilon);
        assert(result.getX() <= Math.max(p3.getX(), p4.getX()) + Util.Epsilon);
        assert(Math.min(p3.getY(), p4.getY()) <= result.getY() + Util.Epsilon);
        assert(result.getY() <= Math.max(p3.getY(), p4.getY()) + Util.Epsilon);

        assert(result.between(p1, p2));
        assert(result.between(p3, p4));

        return result;
    }
    
    public static double angle(Vector2D a, Vector2D b, Vector2D c)
    {
        return a.sub(b).undirectedAngle(c.sub(b));
    }
    
    public static Vector2D fromLineSegmentToPoint(Vector2D a, Vector2D b, Vector2D p)
    {
        return fromLineSegmentToPoint(a.getX(), a.getY(), b.getX(), b.getY(), p.getX(), p.getY());
    }
    public static Vector2D fromLineSegmentToPoint(double ax, double ay, double bx, double by, double px, double py)
    {
        bx -= ax;
        by -= ay;
        px -= ax;
        py -= ay;
        ay = (px * bx + py * by);
        if (ay <= 0) return new Vector2D(px, py);
        ax = (bx * bx + by * by);
        assert(0 < ax);
        if (ax <= ay) return new Vector2D(px - bx, py - by);
        ay /= -ax;
        ax = ay * bx + px;
        ay = ay * by + py;
        return new Vector2D(ax, ay);
    }
    public static Vector2D fromLineToPoint(Vector2D a, Vector2D b, Vector2D p)
    {
        return fromLineToPoint(a.getX(), a.getY(), b.getX(), b.getY(), p.getX(), p.getY());
    }
    public static Vector2D fromLineToPoint(double ax, double ay, double bx, double by, double px, double py)
    {
        bx -= ax;
        by -= ay;
        px -= ax;
        py -= ay;
        ay = (px * bx + py * by);
        ax = (bx * bx + by * by);
        assert(0 < ax);
        ay /= -ax;
        ax = ay * bx + px;
        ay = ay * by + py;
        return new Vector2D(ax, ay);
    }
    
    public static double area(Vector2D... points)
    {
        double area = 0d;
        for (int i = 0; i < points.length; i++)
        {
            int j = (i + 1) % points.length;

            area += (points[i].getX() - points[j].getX()) * (points[i].getY() + points[j].getY());
        }
        area /= 2;
        return area;
    }

    public static Vector2D sum(Vector2D... points)
    {
        double x = 0d;
        double y = 0d;
        for(Vector2D point: points)
        {
            x += point.getX();
            y += point.getY();
        }
        return new Vector2D(x, y);
    }
    public static Vector2D avg(Vector2D... points)
    {
        return sum(points).div(points.length);
    }
    public static Vector2D weightAvg(Vector2D a, double weightA, Vector2D b, double weightB)
    {
        return new Vector2D((a.getX() * weightA + b.getX() * weightB) / (weightA + weightB), (a.getY() * weightA + b.getY() * weightB) / (weightA + weightB));
    }
    public static Vector2D interpolate(Vector2D a, Vector2D b, double weight)
    {
        assert 0 <= weight && weight <= 1 : weight;
        return weightAvg(a, 1 - weight, b, weight);
    }
//left < 0 < right
    public static double lineSide(Vector2D p, Vector2D a, Vector2D b)
    {
        return (b.getY() - a.getY()) * (p.getX() - a.getX()) - (b.getX() - a.getX()) * (p.getY() - a.getY());
    }

    public static double lineAxisIntersectionX(Vector2D a, Vector2D b, double yValue)
    {
        return lineAxisIntersectionYHelper(a.getY(), a.getX(), b.getY(), b.getX(), yValue);
    }
    public static double lineAxisIntersectionY(Vector2D a, Vector2D b, double xValue)
    {
        return lineAxisIntersectionYHelper(a.getX(), a.getY(), b.getX(), b.getY(), xValue);
    }
    private static double lineAxisIntersectionYHelper(double ax, double ay, double bx, double by, double xValue)
    {
        if (ax == bx) return Double.NaN;
        return ay + ((xValue - ax) / (bx - ax)) * (by - ay);
    }
    public static double lineSegmentAxisIntersectionX(Vector2D a, Vector2D b, double yValue)
    {
        return lineSegmentAxisIntersectionYHelper(a.getY(), a.getX(), b.getY(), b.getX(), yValue);
    }
    public static double lineSegmentAxisIntersectionY(Vector2D a, Vector2D b, double xValue)
    {
        return lineSegmentAxisIntersectionYHelper(a.getX(), a.getY(), b.getX(), b.getY(), xValue);
    }
    private static double lineSegmentAxisIntersectionYHelper(double ax, double ay, double bx, double by, double xValue)
    {
        if ((ax - xValue) * (bx - xValue) >= 0) return Double.NaN;
        return ay + ((xValue - ax) / (bx - ax)) * (by - ay);
    }
    
    public static boolean onLineSegment(Vector2D p, Vector2D a, Vector2D b)
    {
        assert p != null && a != null && b != null;
        p = p.sub(a);
        b = b.sub(a);
        double cross = b.cross(p);
        if (!Util.withinEpsilon(cross)) return false;

        double dot = b.dot(p);
        if (dot < 0) return false;
        if (dot > b.squaredLength()) return false;
        return true;
    }
    
    public static Vector2D rayLinesIntersectionPoint(Vector2D source, Vector2D through, ArrayList<Vector2D> lines)
    {
        Vector2D point = null;
        double squaredDist = Double.POSITIVE_INFINITY;
        for (int i = 0; i < lines.size(); i += 2)
        {
            Vector2D tmp = lineIntersectionPoint(source, through, lines.get(i), lines.get(i + 1));
            if(tmp == null) continue;
            double tmpSqDist = source.squaredDistance(tmp);
            if(tmpSqDist < squaredDist)
            {
                point = tmp;
                squaredDist = tmpSqDist;
            }
        }
        return point;
    }
    
    public static Vector2D circumCircleCenter(Vector2D A, Vector2D B, Vector2D C)
    {
        assert !A.withinEpsilon(B);
        assert !A.withinEpsilon(C);
        assert !B.withinEpsilon(C);
        
        double ax = A.getX();
        double bx = B.getX();
        double cx = C.getX();
        double ay = A.getY();
        double by = B.getY();
        double cy = C.getY();

        double a = ax * ax + ay * ay;
        double b = bx * bx + by * by;
        double c = cx * cx + cy * cy;

        double denominator = 2 * (ax * (by - cy) + bx * (cy - ay) + cx * (ay - by));
        double x = (a * (by - cy) + b * (cy - ay) + c * (ay - by)) / denominator;
        double y = (a * (cx - bx) + b * (ax - cx) + c * (bx - ax)) / denominator;

        Vector2D center = new Vector2D(x, y);
        assert Util.withinEpsilon(center.squaredDistance(A) - center.squaredDistance(B));
        assert Util.withinEpsilon(center.squaredDistance(A) - center.squaredDistance(C));
        assert Util.withinEpsilon(center.squaredDistance(B) - center.squaredDistance(C));
        
        return center;
    }
    
}
