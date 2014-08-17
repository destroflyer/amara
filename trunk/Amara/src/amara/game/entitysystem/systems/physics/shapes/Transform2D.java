/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes;

import amara.game.entitysystem.systems.physics.shapes.PolygonMath.Util;
import com.jme3.network.serializing.*;

/**
 *
 * @author Philipp
 */
@Serializable
public class Transform2D
{
    private double scalecos, scalesin, x, y;
    
    public static final Transform2D Identity = new Transform2D(1, 0, 0, 0, true);

    public Transform2D()
    {
    }

    public Transform2D(double scale, double radians, double x, double y)
    {
        this.scalecos = scale * Math.cos(radians);
        this.scalesin = scale * Math.sin(radians);
        this.x = x;
        this.y = y;
    }
    private Transform2D(double scalecos, double scalesin, double x, double y, boolean tmp)
    {
        this.scalecos = scalecos;
        this.scalesin = scalesin;
        this.x = x;
        this.y = y;
    }
    
    public double extractRadians()
    {
        assert Util.withinEpsilon(extractScale() * Math.cos(Math.atan2(scalesin, scalecos)) - scalecos);
        assert Util.withinEpsilon(extractScale() * Math.sin(Math.atan2(scalesin, scalecos)) - scalesin);
        return Math.atan2(scalesin, scalecos);
    }
    public double extractScale()
    {
        return Math.sqrt(determinant());
    }
    public double extractX()
    {
        return x;
    }
    public double extractY()
    {
        return y;
    }
    public Vector2D extractTranslation()
    {
        return new Vector2D(x, y);
    }
    
    public Vector2D transform(Vector2D p)
    {
        return new Vector2D(scalecos * p.getX() - scalesin * p.getY() + x, scalesin * p.getX() + scalecos * p.getY() + y);
    }
    
    public Transform2D append(Transform2D t)
    {
        double sc = scalecos * t.scalecos - scalesin * t.scalesin;
        double s = scalecos * t.scalesin + scalesin * t.scalecos;
        double nx = scalecos * t.x - scalesin * t.y + x;
        double ny = scalesin * t.x + scalecos * t.y + y;
        return new Transform2D(sc, s, nx, ny, true);
    }
    
    private double determinant()
    {
        return scalecos * scalecos + scalesin * scalesin;
    }
    
    public Transform2D inverse()
    {
        double det = determinant();
        assert det != 0;
        
        double sc = scalecos / det;
        double s = scalesin / det;
        double nx = (-scalesin * y - scalecos * x) / det;
        double ny = (scalesin * x - scalecos * y) / det;

        Transform2D t = new Transform2D(sc, s, nx, ny, true);
        assert t.append(this).withinEpsilon(Identity);
        assert this.append(t).withinEpsilon(Identity);
        return t;
    }
    
    public boolean withinEpsilon(Transform2D t)
    {
        if(!Util.withinEpsilon(t.scalecos - scalecos)) return false;
        if(!Util.withinEpsilon(t.scalesin - scalesin)) return false;
        if(!Util.withinEpsilon(t.x - x)) return false;
        if(!Util.withinEpsilon(t.y - y)) return false;
        return true;
    }
}
