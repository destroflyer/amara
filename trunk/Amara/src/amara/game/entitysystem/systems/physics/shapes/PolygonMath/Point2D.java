/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */

@Serializable
public class Point2D
{
    private double x, y;

    public static Point2D Zero = new Point2D();
    
    public Point2D()
    {
    }

    public Point2D(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }
    
    
    public double length()
    {
        return Math.sqrt(squaredLength());
    }
    public double squaredLength()
    {
        return squaredHelper(getX(), getY());
    }
    public double distance(Point2D point)
    {
        return Math.sqrt(squaredDistance(point));
    }
    public double squaredDistance(Point2D point)
    {
        return squaredHelper(getX() - point.getX(), getY() - point.getY());
    }
    private static double squaredHelper(double a, double b)
    {
        return a * a + b * b;
    }
    
    public Point2D mult(double factor)
    {
        return new Point2D(x * factor, y * factor);
    }
    public Point2D div(double denom)
    {
        assert(denom != 0);
        return mult(1d / denom);
    }
    public Point2D add(Point2D p)
    {
        return add(p.x, p.y);
    }
    public Point2D add(double x, double y)
    {
        return new Point2D(this.x + x, this.y + y);
    }
    public Point2D sub(Point2D p)
    {
        return add(-p.x, -p.y);
    }
    public Point2D inverse()
    {
        return new Point2D(-x, -y);
    }
    public Point2D leftHand()
    {
        return new Point2D(-y, x);
    }
    public Point2D rightHand()
    {
        return new Point2D(y, -x);
    }
    public Point2D unit()
    {
        return div(length());
    }
    public Point2D addLength(double value)
    {
        return mult(value / length() + 1);
    }
    public Point2D setLength(double value)
    {
        return mult(value / length());
    }
    
    public double undirectedAngle(Point2D p)
    {
        return Math.acos(unit().dot(p.unit()));
    }
    
    public double dot(Point2D p)
    {
        return x * p.x + y * p.y;
    }
    public double cross(Point2D p)
    {
        return y * p.x - x * p.y;
    }
    
    public boolean between(Point2D a, Point2D b)
    {
        if (withinEpsilon(a)) return false;
        if (withinEpsilon(b)) return false;
        return onLineSegment(a, b);
    }
    public boolean onLineSegment(Point2D a, Point2D b)
    {
        Point2D c = sub(a);
        Point2D d = b.sub(a);
//        double cx = x - a.x;
//        double cy = y - a.y;
//        double dx = b.x - a.x;
//        double dy = b.y - a.y;
        double cross = c.cross(d);//cy * dx - cx * dy;
        if (Math.abs(cross) > Util.Epsilon) return false;

        double dot = c.dot(d);//cx * dx + cy * dy;
        if (dot < 0) return false;
        if (dot > a.squaredDistance(b)) return false;
        return true;
    }

    public boolean withinEpsilon()
    {
        return Util.withinEpsilon(x) && Util.withinEpsilon(y);
    }
    public boolean withinEpsilon(Point2D b)
    {
        return sub(b).withinEpsilon();
    }

    @Override
    public int hashCode() {
        return Double.valueOf(x).hashCode() ^ (373 * Double.valueOf(y).hashCode());
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Point2D) {
            return equals((Point2D)obj);
        }
        return false;
    }
    public boolean equals(Point2D p)
    {
        return p.x == x && p.y == y;
    }

    @Override
    protected Point2D clone() 
    {
        return new Point2D(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
