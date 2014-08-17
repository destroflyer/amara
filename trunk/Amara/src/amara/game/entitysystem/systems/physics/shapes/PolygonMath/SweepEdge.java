/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath;

import amara.game.entitysystem.systems.physics.shapes.Vector2D;

/**
 *
 * @author Philipp
 */
public class SweepEdge
{
    private Vector2D a, b;
    private double minY, maxY;

    public SweepEdge(Vector2D a, Vector2D b)
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

    public Vector2D getA()
    {
        return a;
    }
    public Vector2D getB()
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