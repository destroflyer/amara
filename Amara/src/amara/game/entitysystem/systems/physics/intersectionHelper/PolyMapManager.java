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
    
    private HashMap<Integer, ArrayList<Point2D>> paths = new HashMap<Integer, ArrayList<Point2D>>();
    private HashMap<Integer, Double> walked = new HashMap<Integer, Double>();
    
    private HashMap<Integer, TrianglePath> triPaths = new HashMap<Integer, TrianglePath>();

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
    
    private ArrayList<Point2D> findPath(Point2D start, Point2D end, double radius)
    {
        return mapFromRadius(0).findPath(start, end, radius);
    }
    public Point2D followPath(int id, Point2D from, Point2D to, double distance, double radius)
    {
        ArrayList<Point2D> path = paths.get(id);
        if(path == null || 1 < path.get(path.size() - 1).squaredDistance(to))
        {
            path = findPath(from, to, radius);
            if(path == null)
            {
                if(distance * distance < from.squaredDistance(to)) return Point2DUtil.interpolate(from, to, distance);
                return to;
            }
            paths.put(id, path);
        }
        else distance += walked.get(id);
        walked.put(id, distance);
        if(path.get(0).withinEpsilon(path.get(1)))
        {
            path.remove(0);
        }
        
        int current = 0;
        int next = 1;
        while(next < path.size())
        {
            double distNext = path.get(current).distance(path.get(next));
            if(distNext < distance)
            {
                distance -= distNext;
                current = next++;
            }
            else
            {
                return Point2DUtil.interpolate(path.get(current), path.get(next), distance / distNext);
            }
        }
        paths.remove(id);
        walked.remove(id);
        return path.get(path.size() - 1);
    }
    
    public Point2D followTriPath(int id, Point2D from, Point2D to, double distance, double radius)
    {
        from = closestValid(from, radius);
        to = closestValid(to, radius);
        TrianglePath path = triPaths.get(id);
        if(path == null)
        {
            path = mapFromRadius(0).findTriPath(from, to, radius);
            if(path == null)
            {
                System.out.println("Error in pathfinding, walking straight line.");
                if(distance * distance < from.squaredDistance(to)) return Point2DUtil.interpolate(from, to, distance / from.distance(to));
                return to;
            }
            triPaths.put(id, path);
            Point2D position = path.moveDistance(from, to, distance);
            if(position == null)
            {
                System.out.println("Error in pathfinding, walking straight line.");
                return Point2DUtil.interpolate(from, to, distance / from.distance(to));
            }
            if(position.equals(to)) triPaths.remove(id);
            return position;
        }
        Point2D position = path.moveDistance(from, to, distance);
        if(position == null)
        {
            triPaths.remove(id);
            return followTriPath(id, from, to, distance, radius);
        }
        if(position.equals(to)) triPaths.remove(id);
        return position;
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
