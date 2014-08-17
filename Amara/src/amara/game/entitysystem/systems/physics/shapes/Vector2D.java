/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes;

import amara.game.entitysystem.systems.physics.shapes.PolygonMath.Util;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */

@Serializable
public class Vector2D
{
    private double x, y;

    public static Vector2D Zero = new Vector2D();
    
    public Vector2D()
    {
    }

    public Vector2D(double x, double y)
    {
        assert !Double.isNaN(x);
        assert !Double.isNaN(y);
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
    public double distance(Vector2D point)
    {
        return Math.sqrt(squaredDistance(point));
    }
    public double squaredDistance(Vector2D point)
    {
        return squaredHelper(getX() - point.getX(), getY() - point.getY());
    }
    private static double squaredHelper(double a, double b)
    {
        return a * a + b * b;
    }
    
    public Vector2D mult(double factor)
    {
        return new Vector2D(x * factor, y * factor);
    }
    public Vector2D div(double denom)
    {
        assert(denom != 0);
        return mult(1d / denom);
    }
    public Vector2D add(Vector2D p)
    {
        return add(p.x, p.y);
    }
    public Vector2D add(double x, double y)
    {
        return new Vector2D(this.x + x, this.y + y);
    }
    public Vector2D sub(Vector2D p)
    {
        return add(-p.x, -p.y);
    }
    public Vector2D inverse()
    {
        return new Vector2D(-x, -y);
    }
    public Vector2D leftHand()
    {
        return new Vector2D(-y, x);
    }
    public Vector2D rightHand()
    {
        return new Vector2D(y, -x);
    }
    public Vector2D unit()
    {
        return div(length());
    }
    public boolean isUnit()
    {
        return Util.withinEpsilon(length() - 1);
    }
    public Vector2D addLength(double value)
    {
        return mult(value / length() + 1);
    }
    public Vector2D setLength(double value)
    {
        return mult(value / length());
    }
    
    public double undirectedAngle(Vector2D p)
    {
        return Math.acos(unit().dot(p.unit()));
    }
    public double directedAngle(Vector2D p)
    {
        return p.angle() - angle();
    }
    public double angle()
    {
        return Math.atan2(y, x);
    }
    
    public double dot(Vector2D p)
    {
        return x * p.x + y * p.y;
    }
    public double cross(Vector2D p)
    {
        return x * p.y - y * p.x;
    }
    
    public boolean between(Vector2D a, Vector2D b)
    {
        if (withinEpsilon(a)) return false;
        if (withinEpsilon(b)) return false;
        return onLineSegment(a, b);
    }
    public boolean onLineSegment(Vector2D a, Vector2D b)
    {
        Vector2D c = sub(a);
        Vector2D d = b.sub(a);
        double cross = c.cross(d);
        if (Math.abs(cross) > Util.Epsilon) return false;

        double dot = c.dot(d);
        if (dot < 0) return false;
        if (dot > a.squaredDistance(b)) return false;
        return true;
    }
    
    public Vector2D scale(double scaleX, double scaleY)
    {
        return new Vector2D(x * scaleX, y * scaleY);
    }

    public boolean withinEpsilon()
    {
        return Util.withinEpsilon(x) && Util.withinEpsilon(y);
    }
    public boolean withinEpsilon(Vector2D b)
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
        if(obj instanceof Vector2D) {
            return equals((Vector2D)obj);
        }
        return false;
    }
    public boolean equals(Vector2D p)
    {
        return p.x == x && p.y == y;
    }

    @Override
    protected Vector2D clone() 
    {
        return new Vector2D(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
