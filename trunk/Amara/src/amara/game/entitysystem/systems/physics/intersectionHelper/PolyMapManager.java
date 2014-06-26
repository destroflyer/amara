/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.intersectionHelper;

import amara.game.entitysystem.systems.physics.*;
import amara.game.entitysystem.systems.physics.shapes.PolygonMath.*;
import java.util.*;

/**
 *
 * @author Philipp
 */
public final class PolyMapManager
{
    private Vision vision = new Vision();
    private Polygon map;
    private HashMap<Double, NavigationMap> navis = new HashMap<Double, NavigationMap>();
    private double width, height;

    public PolyMapManager(Polygon map, double width, double height)
    {
        this.width = width;
        this.height = height;
        this.map = map.union(PolyHelper.rectangle(0, 0, width, height).inverse());
        addNavigationMap(this.map.inverse(), 0);
    }

    public double getWidth()
    {
        return width;
    }

    public double getHeight()
    {
        return height;
    }
    
    public boolean outOfMapBounds(Point2D pos, double radius)
    {
        if(pos.getX() + radius <= 0 || width <= pos.getX() - radius) return true;
        if(pos.getY() + radius <= 0 || height <= pos.getY() - radius) return true;
        return false;
    }
    
    public Polygon calcNavigationMap(double radius)
    {
        int edges = 8;
        Polygon poly = PolyHelper.regularCyclic(0, 0, radius, edges);
        poly = map.minkowskiAdd(poly).inverse();
        addNavigationMap(poly, radius);
        return poly;
    }
    public void addNavigationMap(Polygon poly, double radius)
    {
        NavigationMap navi = new NavigationMap(poly);
        navis.put(radius, navi);
    }
    
    private NavigationMap mapFromRadius(double radius)
    {
        assert 0 <= radius;
        double key = Double.NaN;
        assert !navis.isEmpty();
        for (Double value : navis.keySet())
        {
            if(Double.isNaN(key) || Math.abs(value - radius) < Math.abs(radius - key)) key = value;
        }
        return navis.get(key);
    }
    
    public Point2D closestValid(Point2D pos, double radius)
    {
        return mapFromRadius(radius).closestValid(pos);
    }
    
    public ArrayList<Point2D> findPath(Point2D start, Point2D end, double radius)
    {
        return mapFromRadius(radius).findPath(start, end);
    }
    
    public Polygon sightPolygon(Point2D source, double range)
    {
        if(map.contains(source))
        {
            return PolyHelper.empty();
        }
        Polygon mask = PolyHelper.regularCyclic(source.getX(), source.getY(), range, 16).inverse();
        return PolyHelper.fromOutline(vision.sightPolyOutline(source, map.union(mask).edges()));
    }
}
