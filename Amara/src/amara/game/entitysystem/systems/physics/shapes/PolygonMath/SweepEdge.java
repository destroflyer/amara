/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath;

/**
 *
 * @author Philipp
 */
public class SweepEdge
{
    private Point2D a, b;
    private double minY, maxY;

    public SweepEdge(Point2D a, Point2D b)
    {
        assert !a.equals(b);
        if(a.getX() <= b.getX())
        {
            this.a = a;
            this.b = b;
        }
        else
        {
            this.a = b;
            this.b = a;
        }
        
        if(a.getY() <= b.getY())
        {
            minY = a.getY();
            maxY = b.getY();
        }
        else
        {
            minY = b.getY();
            maxY = a.getY();
        }
    }

    public Point2D getA()
    {
        return a;
    }
    public Point2D getB()
    {
        return b;
    }
    
    public double minX()
    {
        return a.getX();
    }
    public double minY()
    {
        return minY;
    }
    public double maxX()
    {
        return b.getX();
    }
    public double maxY()
    {
        return maxY;
    }
}