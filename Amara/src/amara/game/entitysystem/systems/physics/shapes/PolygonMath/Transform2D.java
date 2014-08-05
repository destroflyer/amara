/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath;

/**
 *
 * @author Philipp
 */
public class Transform2D
{
    private double scalecos, sin, x, y;
    
    public static final Transform2D Identity = new Transform2D(1, 0, 0, 0, true);

    public Transform2D(double scale, double radians, double x, double y)
    {
        this.scalecos = scale * Math.cos(radians);
        this.sin = Math.sin(radians);
        this.x = x;
        this.y = y;
    }
    private Transform2D(double scalecos, double sin, double x, double y, boolean tmp)
    {
        this.scalecos = scalecos;
        this.sin = sin;
        this.x = x;
        this.y = y;
    }
    
    public double extractRadians()
    {
        return Math.asin(sin);
    }
    public double extractScale()
    {
        return scalecos / Math.sqrt(1 - sin * sin);
    }
    public double extractX()
    {
        return x;
    }
    public double extractY()
    {
        return y;
    }
    
    public Point2D transform(Point2D p)
    {
        return new Point2D(scalecos * p.getX() - sin * p.getY() + x, sin * p.getX() + scalecos * p.getY() + y);
    }
    
    public Transform2D append(Transform2D t)
    {
        double sc = scalecos * t.scalecos - sin * t.sin;
        double s = scalecos * t.sin + sin * t.scalecos;
        double nx = scalecos * t.x - sin * t.y + x;
        double ny = sin * t.x + scalecos * t.y + y;
        return new Transform2D(sc, s, nx, ny, true);
    }
    
    private double determinant()
    {
        return scalecos * scalecos + sin * sin;
    }
    
    public Transform2D inverse()
    {
        double det = determinant();
        assert det != 0;
        
        double sc = scalecos / det;
        double s = sin / det;
        double nx = (-sin * y - scalecos * x) / det;
        double ny = (sin * x - scalecos * y) / det;

        Transform2D t = new Transform2D(sc, s, nx, ny, true);
        assert t.append(this).withinEpsilon(Identity);
        assert this.append(t).withinEpsilon(Identity);
        return t;
    }
    
    public boolean withinEpsilon(Transform2D t)
    {
        if(!Util.withinEpsilon(t.scalecos - scalecos)) return false;
        if(!Util.withinEpsilon(t.sin - sin)) return false;
        if(!Util.withinEpsilon(t.x - x)) return false;
        if(!Util.withinEpsilon(t.y - y)) return false;
        return true;
    }
}
