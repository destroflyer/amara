/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath;

import amara.game.entitysystem.systems.physics.shapes.PolygonMath.Public.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Philipp
 */
public class SimplePolygonUtil {
    public static boolean haveTouchingEdge(SimplePolygon a, SimplePolygon b)
    {
        for (int i = 0; i < a.numPoints(); i++)
        {
            int j = (i + 1) % a.numPoints();
            if (b.hasEdge(a.getPoint(j), a.getPoint(i))) return true;
        }
        return false;
    }
    public static boolean haveCommonPoint(SimplePolygon a, SimplePolygon b)
    {
        for (int i = 0; i < a.numPoints(); i++)
        {
            if (b.hasPoint(a.getPoint(i))) return true;
        }
        return false;
    }
    private static void insertIntersectionPoints(SimplePolygon a, SimplePolygon b)
    {
        assert(!a.hasRepetitions());
        assert(!b.hasRepetitions());
        assert(!a.hasLooseLines());
        assert(!b.hasLooseLines());

        HashMap<Integer, HashSet<Point2D>> intersectionsA = new HashMap<Integer, HashSet<Point2D>>();
        HashMap<Integer, HashSet<Point2D>> intersectionsB = new HashMap<Integer, HashSet<Point2D>>();
        
        for (int i = 0; i < a.numPoints(); i++)
        {
            int j = (i + 1) % a.numPoints();
            for (int k = 0; k < b.numPoints(); k++)
            {
                int l = (k + 1) % b.numPoints();
                Point2D intersection = Point2DUtil.lineSegmentIntersectionPointWithoutCorners(a.getPoint(i), a.getPoint(j), b.getPoint(k), b.getPoint(l));
                if (intersection != null)
                {
                    assert(!a.hasPoint(intersection));
                    assert(!b.hasPoint(intersection));
                    if (!intersectionsA.containsKey(i)) intersectionsA.put(i, new HashSet<Point2D>());
                    if (!intersectionsB.containsKey(k)) intersectionsB.put(k, new HashSet<Point2D>());
                    intersectionsA.get(i).add(intersection);
                    intersectionsB.get(k).add(intersection);
                }
            }
        }
        insertIntersectionHelper(a, intersectionsA);
        insertIntersectionHelper(b, intersectionsB);
        
        assert(!a.hasRepetitions());
        assert(!b.hasRepetitions());
        assert(!a.hasLooseLines());
        assert(!b.hasLooseLines());
    }
    private static void insertIntersectionHelper(SimplePolygon simple, HashMap<Integer, HashSet<Point2D>> intersections)
    {
        for (int i = simple.numPoints() - 1; i >= 0; i--)
        {
            if (intersections.containsKey(i))
            {
                ArrayList<Point2D> list = new ArrayList<Point2D>(intersections.get(i));
                Point2D point = simple.getPoint(i);
                Collections.sort(list, new PointDistanceComparator(point));
                simple.insertRange(i + 1, list);
                assert(!simple.hasRepetitions());
                assert(!simple.hasLooseLines());
            }
        }
    }

    private static boolean polysLinesIntersectIndirectlyHelper(SimplePolygon a, SimplePolygon b)
    {
        Containment cont = Containment.Border;
        for (int i = 0; i < b.numPoints(); i++)
        {
            int j = (i + 1) % b.numPoints();
            Containment tmp = a.areaContains(Point2DUtil.avg(b.getPoint(i), b.getPoint(j)));
            if (tmp == Containment.Border) continue;
            if (cont == Containment.Border) cont = tmp;
            if (cont != tmp) return true;
        }
        return false;
    }
    
     public static void insertCommonOutlinePoints(SimplePolygon a, SimplePolygon b)
    {
        assert(!a.hasRepetitions());
        assert(!b.hasRepetitions());
        assert(!a.hasLooseLines());
        assert(!b.hasLooseLines());
        insertIntersectionPoints(a, b);
        assert(!a.hasLooseLines());
        assert(!b.hasLooseLines());
        a.insertTouchPoints(a);
        a.insertTouchPoints(b);
        b.insertTouchPoints(a);
        assert(!a.hasRepetitions());
        assert(!b.hasRepetitions());
        assert(!a.hasLooseLines());
        assert(!b.hasLooseLines());
    }
     
    public static boolean outlinesIntersect(SimplePolygon a, SimplePolygon b)
    {
        assert(!a.hasRepetitions());
        assert(!b.hasRepetitions());
        if (a.equals(b)) return false;
        for (int i = 0; i < a.numPoints(); i++)
        {
            int j = (i + 1) % a.numPoints();
            for (int k = 0; k < b.numPoints(); k++)
            {
                int l = (k + 1) % b.numPoints();
                boolean inter = Point2DUtil.lineSegmentIntersectionPointWithoutCorners(a.getPoint(i), a.getPoint(j), b.getPoint(k), b.getPoint(l)) != null;
                if (inter) return true;
            }
        }
        //a = a.ToPolygon();
        //a.InsertTouchPoints(b);
        b = new SimplePolygon(b);
        b.insertTouchPoints(a);
        //if (PolysLinesIntersectIndirectlyHelper(a, b)) return true;
        //b.Reverse();
        return polysLinesIntersectIndirectlyHelper(a, b);
    }
     

    public static boolean haveOverlappingAreas(SimplePolygon a, SimplePolygon b)
    {
        if (a.numPoints() < 3 || b.numPoints() < 3) return false;
        if (outlinesIntersect(a, b)) return true;
        if (a.areaContainsOutline(b)) return true;
        if (b.areaContainsOutline(a)) return true;

        return false;
    }
    
    public static void write(ByteBuffer buffer, SimplePolygon simple)
    {
        if(simple == null)
        {
            buffer.writeInt(-1);
            return;
        }
        buffer.writeInt(simple.numPoints());
        for (int i = 0; i < simple.numPoints(); i++)
        {
            buffer.writeDouble(simple.getPoint(i).getX());
            buffer.writeDouble(simple.getPoint(i).getY());
        }
    }

    public static SimplePolygon read(ByteBuffer buffer)
    {
        int num = buffer.readInt();
        if(num == -1) return null;
        ArrayList<Point2D> points = new ArrayList<Point2D>();
        for (int i = 0; i < num; i++)
        {
            points.add(new Point2D(buffer.readDouble(), buffer.readDouble()));
        }
        return new SimplePolygon(points);
    }
}
