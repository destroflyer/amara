/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath;

/**
 *
 * @author Philipp
 */
public class Transform
{
    private double scalecos, sin, x, y;

    public Transform(double scale, double radians, double x, double y) {
        this.scalecos = scale * Math.cos(radians);
        this.sin = Math.sin(radians);
        this.x = x;
        this.y = y;
    }
    
    public Point2D transform(Point2D p)
    {
        return new Point2D(scalecos * p.getX() - sin * p.getY() + x, sin * p.getX() + scalecos * p.getY() + y);
    }
}
