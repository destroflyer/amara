/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.shapes.vision;

import java.util.*;
import amara.libraries.physics.shapes.*;

/**
 *
 * @author Philipp
 */
public class Vision implements Comparator<Vector2D>
{
    Vector2D source;

    @Override
    public int compare(Vector2D p1, Vector2D p2)
    {
        double a = Math.atan2(p1.getY() - source.getY(), p1.getX() - source.getX());
        double b = Math.atan2(p2.getY() - source.getY(), p2.getX() - source.getX());
        return (int) Math.signum(b - a);
    }
    
    //in case we add circles in the future...
    private void convertCircle(ArrayList<Vector2D> edges, Vector2D source, double x, double y, double r)
    {
        if(Util.circleContainsOrOnBorder(source.getX(), source.getY(), x, y, r)) return;
        ArrayList<Vector2D> points = Util.tangentPoints(x, y, r, source.getX(), source.getY());
        edges.addAll(points);
        Util.reverse(points);
        edges.addAll(points);
    }
    
    public ArrayList<Vector2D> sightPolyOutline(Vector2D source, ArrayList<Vector2D> edges)
    {
        this.source = source;
        for (Vector2D point2D : edges) {
            assert point2D != null;
        }
        cullBackfaces(edges);
        edges = sightArea(edges);
        smoothOutline(edges);
        return edges;
    }
    
    private void smoothOutline(ArrayList<Vector2D> points)
    {
        for (int i = points.size() - 1; i >= 0; i--)
        {
            int j = (i + 1) % points.size();
            int h = (i + points.size() - 1) % points.size();
            if(points.get(i).withinEpsilon(points.get(j)) || Vector2DUtil.onLineSegment(points.get(i), points.get(h), points.get(j)))
            {
                points.remove(i);
            }
        }
    }
    
    private void cullBackfaces(ArrayList<Vector2D> edges)
    {
        for (int i = edges.size() - 2; i >= 0; i -= 2)
        {
            if (Vector2DUtil.lineSide(source, edges.get(i), edges.get(i + 1)) <= 0)
            {
                edges.remove(i + 1);
                edges.remove(i);
            }
        }
    }
    private void checkForNoEpsilonLengthEdges(ArrayList<Vector2D> edges)
    {
        for (int i = edges.size() - 2; i >= 0; i -= 2)
        {
            assert !(edges.get(i).withinEpsilon(edges.get(i + 1)));
        }
    }
    private boolean activesCheck(Vector2D source, Vector2D p, ArrayList<Vector2D> actives)
    {
        assert !actives.isEmpty();
        assert (actives.size() & 1) == 0;
        for (int j = actives.size() - 2; j >= 0; j -= 2)
        {
            Vector2D a = actives.get(j);
            Vector2D b = actives.get(j + 1);

            assert Vector2DUtil.lineSide(a, source, p) <= 0;
            assert Vector2DUtil.lineSide(p, source, b) <= 0;

            Vector2D inter = Vector2DUtil.lineIntersectionPoint(source, p, a, b);
            boolean onSeg = Vector2DUtil.onLineSegment(inter, a, b);
            onSeg = onSeg || inter.withinEpsilon(a) || inter.withinEpsilon(b);
            assert onSeg;
        }
        return true;
    }
    
    private ArrayList<Vector2D> sightArea(ArrayList<Vector2D> edges)
    {
        for (Vector2D point2D : edges)
        {
            assert point2D != null;
        }
        checkForNoEpsilonLengthEdges(edges);
        HashMap<Vector2D, ArrayList<Vector2D>> startToEnd = new HashMap<Vector2D, ArrayList<Vector2D>>();

        ArrayList<Vector2D> points = new ArrayList<Vector2D>(new HashSet<Vector2D>(edges));
        Collections.sort(points, this);
        
        ArrayList<Vector2D> list = new ArrayList<Vector2D>();
        ArrayList<Vector2D> actives = new ArrayList<Vector2D>();
        
        for (int i = 0; i < points.size(); i++)
        {
            startToEnd.put(points.get(i), new ArrayList<Vector2D>());
        }
        
        for (int i = 0; i < edges.size(); i += 2)
        {
            assert !edges.get(i).withinEpsilon(source);
            assert !edges.get(i + 1).withinEpsilon(source);
            assert Vector2DUtil.lineSide(source, edges.get(i), edges.get(i + 1)) >= 0: edges.get(i) + ", " + edges.get(i + 1) +  ", " + source;
            startToEnd.get(edges.get(i)).add(edges.get(i + 1));
            
            if((Util.withinEpsilon(edges.get(i).getY() - source.getY()) && edges.get(i).getX() <= source.getX())
            || Vector2DUtil.lineSegmentAxisIntersectionX(edges.get(i), edges.get(i + 1), source.getY()) < source.getX())
            {
                actives.add(edges.get(i));
                actives.add(edges.get(i + 1));
                assert !edges.get(i).withinEpsilon(edges.get(i + 1));
            }
        }
        list.add(Vector2DUtil.rayLinesIntersectionPoint(source, new Vector2D(source.getX() - 1, source.getY()), actives));
        
        for (int i = 0; i < points.size(); i++)
        {
            Vector2D p = points.get(i);
            
            assert activesCheck(source, p, actives);
            list.add(Vector2DUtil.rayLinesIntersectionPoint(source, p, actives));
            
            for (int j = actives.size() - 2; j >= 0; j -= 2)
            {
                if(actives.get(j + 1).withinEpsilon(p))
                {
                    actives.remove(j + 1);
                    actives.remove(j);
                }
            }
            ArrayList<Vector2D> ends = startToEnd.get(p);
            for (int j = 0; j < ends.size(); j++)
            {
                actives.add(p);
                actives.add(ends.get(j));
            }
            assert activesCheck(source, p, actives);
            list.add(Vector2DUtil.rayLinesIntersectionPoint(source, p, actives));
        }
        
        list.add(list.get(0));
        return list;
    }
}
