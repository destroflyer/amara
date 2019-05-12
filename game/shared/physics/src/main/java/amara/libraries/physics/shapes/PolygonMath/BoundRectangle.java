/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.shapes.PolygonMath;

import com.jme3.network.serializing.*;
import amara.libraries.physics.shapes.Vector2D;

/**
 *
 * @author Philipp
 */
@Serializable
public class BoundRectangle
{
    private double minX, minY, maxX, maxY;

    public BoundRectangle()
    {
    }
    
    public BoundRectangle(double minX, double minY, double maxX, double maxY) {
        assert minX <= maxX;
        assert minY <= maxY;
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }
    
    public Polygon toPolygon()
    {
        PolygonBuilder builder = new PolygonBuilder();
        builder.nextOutline(false);
        builder.add(minX, minY);
        builder.add(minX, maxY);
        builder.add(maxX, maxY);
        builder.add(maxX, minY);
        return builder.build(false);
    }
    
    public BoundRectangle grow(double value)
    {
        assert value >= 0;
        return new BoundRectangle(minX - value, minY - value, maxX + value, maxY + value);
    }
    
    public boolean contains(Vector2D p)
    {
        return containsHelper(p.getX(), p.getY(), p.getX(), p.getY());
    }
    private boolean containsHelper(double minX, double minY, double maxX, double maxY)
    {
        return this.minX < minX && this.minY < minY && this.maxX > maxX && this.maxY > maxY;
    }
    
    public BoundRectangle flip()
    {
        return new BoundRectangle(-maxX, -maxY, -minX, -minY);
    }
}
