/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath;

import java.util.*;

/**
 *
 * @author Philipp
 */
public class Vision implements Comparator<Point2D>
{
    Point2D source;

    @Override
    public int compare(Point2D p1, Point2D p2)
    {
        double a = Math.atan2(p1.getY() - source.getY(), p1.getX() - source.getX());
        double b = Math.atan2(p2.getY() - source.getY(), p2.getX() - source.getX());
        return (int) Math.signum(b - a);
    }
    
    //in case we add circles in the future...
    private void convertCircle(ArrayList<Point2D> edges, Point2D source, double x, double y, double r)
    {
        if(Util.circleContainsOrOnBorder(source.getX(), source.getY(), x, y, r)) return;
        ArrayList<Point2D> points = Util.tangentPoints(x, y, r, source.getX(), source.getY());
        edges.addAll(points);
        Util.reverse(points);
        edges.addAll(points);
    }
    
    public ArrayList<Point2D> sightPolyOutline(Point2D source, ArrayList<Point2D> edges)
    {
        this.source = source;
        for (Point2D point2D : edges) {
            assert point2D != null;
        }
        cullBackfaces(edges);
        edges = sightArea(edges);
        smoothOutline(edges);
        return edges;
    }
    
    private void smoothOutline(ArrayList<Point2D> points)
    {
        for (int i = points.size() - 1; i >= 0; i--)
        {
            int j = (i + 1) % points.size();
            int h = (i + points.size() - 1) % points.size();
            if(points.get(i).withinEpsilon(points.get(j)) || Point2DUtil.onLineSegment(points.get(i), points.get(h), points.get(j)))
            {
                points.remove(i);
            }
        }
    }
    
    private void cullBackfaces(ArrayList<Point2D> edges)
    {
        for (int i = edges.size() - 2; i >= 0; i -= 2)
        {
            if (Point2DUtil.lineSide(source, edges.get(i), edges.get(i + 1)) <= 0)
            {
                edges.remove(i + 1);
                edges.remove(i);
            }
        }
    }
    private void checkForNoEpsilonLengthEdges(ArrayList<Point2D> edges)
    {
        for (int i = edges.size() - 2; i >= 0; i -= 2)
        {
            assert !(edges.get(i).withinEpsilon(edges.get(i + 1)));
        }
    }
    private boolean activesCheck(Point2D source, Point2D p, ArrayList<Point2D> actives)
    {
        assert !actives.isEmpty();
        assert (actives.size() & 1) == 0;
        for (int j = actives.size() - 2; j >= 0; j -= 2)
        {
            Point2D a = actives.get(j);
            Point2D b = actives.get(j + 1);

            assert Point2DUtil.lineSide(a, source, p) <= 0;
            assert Point2DUtil.lineSide(p, source, b) <= 0;

            Point2D inter = Point2DUtil.lineIntersectionPoint(source, p, a, b);
            boolean onSeg = Point2DUtil.onLineSegment(inter, a, b);
            onSeg = onSeg || inter.withinEpsilon(a) || inter.withinEpsilon(b);
            assert onSeg;
        }
        return true;
    }
    
    private ArrayList<Point2D> sightArea(ArrayList<Point2D> edges)
    {
        for (Point2D point2D : edges)
        {
            assert point2D != null;
        }
        checkForNoEpsilonLengthEdges(edges);
        HashMap<Point2D, ArrayList<Point2D>> startToEnd = new HashMap<Point2D, ArrayList<Point2D>>();

        ArrayList<Point2D> points = new ArrayList<Point2D>(new HashSet<Point2D>(edges));
        Collections.sort(points, this);
        
        ArrayList<Point2D> list = new ArrayList<Point2D>();
        ArrayList<Point2D> actives = new ArrayList<Point2D>();
        
        for (int i = 0; i < points.size(); i++)
        {
            startToEnd.put(points.get(i), new ArrayList<Point2D>());
        }
        
        for (int i = 0; i < edges.size(); i += 2)
        {
            assert !edges.get(i).withinEpsilon(source);
            assert !edges.get(i + 1).withinEpsilon(source);
            assert Point2DUtil.lineSide(source, edges.get(i), edges.get(i + 1)) >= 0: edges.get(i) + ", " + edges.get(i + 1) +  ", " + source;
            startToEnd.get(edges.get(i)).add(edges.get(i + 1));
            
            if((Util.withinEpsilon(edges.get(i).getY() - source.getY()) && edges.get(i).getX() <= source.getX())
            || Point2DUtil.lineSegmentAxisIntersectionX(edges.get(i), edges.get(i + 1), source.getY()) < source.getX())
            {
                actives.add(edges.get(i));
                actives.add(edges.get(i + 1));
                assert !edges.get(i).withinEpsilon(edges.get(i + 1));
            }
        }
        list.add(Point2DUtil.rayLinesIntersectionPoint(source, new Point2D(source.getX() - 1, source.getY()), actives));
        
        for (int i = 0; i < points.size(); i++)
        {
            Point2D p = points.get(i);
            
            assert activesCheck(source, p, actives);
            list.add(Point2DUtil.rayLinesIntersectionPoint(source, p, actives));
            
            for (int j = actives.size() - 2; j >= 0; j -= 2)
            {
                if(actives.get(j + 1).withinEpsilon(p))
                {
                    actives.remove(j + 1);
                    actives.remove(j);
                }
            }
            ArrayList<Point2D> ends = startToEnd.get(p);
            for (int j = 0; j < ends.size(); j++)
            {
                actives.add(p);
                actives.add(ends.get(j));
            }
            assert activesCheck(source, p, actives);
            list.add(Point2DUtil.rayLinesIntersectionPoint(source, p, actives));
        }
        
        list.add(list.get(0));
        return list;
    }
}
