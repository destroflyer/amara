/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath;

import amara.game.entitysystem.systems.physics.shapes.Vector2D;
import amara.game.entitysystem.systems.physics.shapes.Vector2DUtil;
import static amara.game.entitysystem.systems.physics.shapes.PolygonMath.PolygonOperation.*;
import java.util.ArrayList;
import sun.reflect.generics.reflectiveObjects.*;

/**
 *
 * @author Philipp
 */
public class SweepPolygon
{
    private ArrayList<SweepEdge> edges;//ascending a.x value
    private ArrayList<SweepEdge> actives = new ArrayList<SweepEdge>();//ascending b.x value
    private boolean infinite;

    public SweepPolygon(ArrayList<SweepEdge> edges, boolean infinite)
    {
        this.edges = edges;
        this.infinite = infinite;
    }
    
    public boolean areaContains(Vector2D p)
    {
        boolean inside = infinite;
        for (int i = 0; i < edges.size(); i++)
        {
            SweepEdge edge = edges.get(i);
            if(p.getX() < edge.minX()) break;
            if(p.getX() < edge.maxX())
            {
                if(Vector2DUtil.lineAxisIntersectionY(edge.getA(), edge.getB(), p.getX()) <= p.getY())
                {
                    inside = !inside;
                }
            }
        }
        return inside;
    }
    
    public boolean selfIntersecting()
    {
        assert actives.isEmpty();
        for (int i = 0; i < edges.size(); i++)
        {
            SweepEdge edge = edges.get(i);
            removeActives(edge.minX());
            for (int j = 0; j < actives.size(); j++)
            {
                if(lineSegmentsIntersect(edge, actives.get(j))) return true;
            }
            insertActive(edge);
        }
        actives.clear();
        return false;
    }
    
    public boolean intersects(SweepPolygon poly)
    {
        throw new NotImplementedException();
    }
    
    public SweepPolygon operate(SweepPolygon poly, PolygonOperation o)
    {
        ArrayList<SweepEdge> result = new ArrayList<SweepEdge>();
        int i = 0, j = 0;
        while(i + j < edges.size() + poly.edges.size())
        {
            boolean select;
            if(i == edges.size()) select = false;
            else if(j == poly.edges.size()) select = true;
            else select = edges.get(i).minX() <= poly.edges.get(j).minX();
            
            if(select)
            {
                
            }
            else
            {
                
            }
            throw new NotImplementedException();
        }
        return new SweepPolygon(edges, isInfinite(infinite, poly.infinite, o));
    }
    private static boolean isInfinite(boolean aInfinite, boolean bInfinite, PolygonOperation o)
    {
        switch (o)
        {
            case Union:
                return aInfinite || bInfinite;
            case Exclude:
                return aInfinite && !bInfinite;
            case Intersection:
                return aInfinite && bInfinite;
            default:
                throw new NotImplementedException();
        }
    }
    
    private boolean lineSegmentsIntersect(SweepEdge first, SweepEdge second)
    {
        assert first.minX() <= second.minX();
        assert first.maxX() <= second.maxX();
        
        assert second.minX() < first.maxX();
        
        if(first.maxY() <= second.minY() || second.maxY() <= first.minY()) return false;
        
        if(first.minX() == first.maxX())
        {
            if(second.minX() == second.maxX()) return false;
            double a = Vector2DUtil.lineAxisIntersectionY(second.getA(), second.getB(), first.maxX());
            return first.minY() < a && a < first.maxY();
        }
        if(second.minX() == second.maxX())
        {
            double b = Vector2DUtil.lineAxisIntersectionY(first.getA(), first.getB(), second.minX());
            return second.minY() < b && b < second.maxY();
        }
        
        double a = Vector2DUtil.lineAxisIntersectionY(second.getA(), second.getB(), first.maxX());
        double b = Vector2DUtil.lineAxisIntersectionY(first.getA(), first.getB(), second.minX());
        assert !Double.isNaN(a) && !Double.isNaN(b);
        return (a - first.getB().getY()) * (b - second.getA().getY()) < 0;
    }
    
    private void removeActives(double minX)
    {
        while(!actives.isEmpty() && actives.get(0).maxX() <= minX)
        {
            actives.remove(0);
        }
    }
    
    private void insertActive(SweepEdge edge)
    {
        int i;
        for (i = 0; i < actives.size(); i++)
        {
            if(edge.getB().getX() < actives.get(i).getB().getX()) break;
        }
        actives.add(i, edge);
    }
}
