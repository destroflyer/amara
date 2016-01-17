/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.shapes.PolygonMath;

import java.util.*;
import amara.libraries.physics.shapes.*;

/**
 *
 * @author Philipp
 */
class SimplePolygonUtil
{
    public static ArrayList<Vector2D> triangles(SimplePolygon simple)
    {
        assert !simple.hasRepetitions();
        assert simple.isAreaPositive();
        ArrayList<Vector2D> list = new ArrayList<Vector2D>();
        if(simple.numPoints() < 3) return list;
        SimplePolygon helper = new SimplePolygon(simple);
        while(helper.numPoints() > 3)
        {
            assert !helper.hasRepetitions();
            assert !helper.hasLooseLines();
            int i = findEar(helper);
            int h = (i + helper.numPoints() - 1) % helper.numPoints();
            int j = (i + 1) % helper.numPoints();
            
            assert !helper.getPoint(i).withinEpsilon(helper.getPoint(h));
            assert !helper.getPoint(i).withinEpsilon(helper.getPoint(j));
            assert !helper.getPoint(h).withinEpsilon(helper.getPoint(j));
            
            list.add(helper.getPoint(h));
            list.add(helper.getPoint(i));
            list.add(helper.getPoint(j));
            
            SimplePolygon tri = new SimplePolygon();
            tri.add(helper.getPoint(h));
            tri.add(helper.getPoint(i));
            tri.add(helper.getPoint(j));
            assert !tri.isHole(): tri.signedArea();
            assert !Util.aboveEpsilon(tri.signedArea() - helper.signedArea()): tri.signedArea() + " / " + helper.signedArea();
            
            double prevArea = helper.signedArea();
            
            assert !helper.isHole(): helper.signedArea();
            helper.removeAt(i);
            assert !helper.isHole(): helper.signedArea();
            helper.removeLooseLines();
            assert Util.withinEpsilon(tri.signedArea() + helper.signedArea() - prevArea);
            assert !helper.isHole(): helper.signedArea();
            assert !helper.hasRepetitions();
            assert !helper.hasLooseLines();
        }
        assert helper.numPoints() == 3;
        for (int i = 0; i < 3; i++)
        {
            list.add(helper.getPoint(i));
        }
        assert list.size() % 3 == 0;
        
        double a = 0;
        for (int i = 0; i < list.size(); i += 3)
        {
            SimplePolygon tri = new SimplePolygon();
            tri.add(list.get(i));
            tri.add(list.get(i + 1));
            tri.add(list.get(i + 2));
            a += tri.signedArea();
        }
        assert Util.withinEpsilon(a - simple.signedArea());
        return list;
    }
    private static int findEar(SimplePolygon simple)
    {
        assert simple.numPoints() > 3;
        assert !simple.isHole();
        assert !simple.isSelfIntersecting();
        Random rnd = new Random();
        int offset = 0;//rnd.nextInt(simple.numPoints());
        //todo: enable random
        int numCorners = 0;
        for (int k = 0; k < simple.numPoints(); k++)
        {
            int h = (k + offset) % simple.numPoints();
            int i = (h + 1) % simple.numPoints();
            int j = (h + 2) % simple.numPoints();
            
            SimplePolygon tri = new SimplePolygon();
            tri.add(simple.getPoint(h));
            tri.add(simple.getPoint(i));
            tri.add(simple.getPoint(j));
            if(tri.isHole()) continue;
            
            numCorners++;
            
            boolean ear = true;
            for (int l = 0; l < simple.numPoints(); l++)
            {
                if(tri.areaContains(simple.getPoint(l)) == Containment.Inside)
                {
                    ear = false;
                    break;
                }
                int m = (l + 1) % simple.numPoints();
                if(tri.areaContains(Vector2DUtil.avg(simple.getPoint(l), simple.getPoint(m))) == Containment.Inside)
                {
                    ear = false;
                    break;
                }
            }
            if(ear) return i;
        }
        throw new IndexOutOfBoundsException();
    }
    public static boolean isValidCut(SimplePolygon simple, Vector2D a, Vector2D b)
    {
        if(a.withinEpsilon(b)) return true;
        assert !a.withinEpsilon(b);
        return !outlineIntersectsSegment(simple, a, b) && simple.areaContains(Vector2DUtil.avg(a, b)) != Containment.Outside;
    }
    public static ArrayList<SimplePolygon> splitAtSelfTouch(SimplePolygon simple)
    {
        assert !simple.hasRepetitions();
        double area = simple.signedArea();
        ArrayList<SimplePolygon> list = new ArrayList<SimplePolygon>();
        SimplePolygon helper = new SimplePolygon(simple);
        insertCommonOutlinePoints(helper, helper);
        splitAtSelfTouchHelper(helper, list);
        double area2 = 0;
        for (SimplePolygon simplePolygon : list)
        {
            area2 += simplePolygon.signedArea();
        }
        assert Util.withinEpsilon(area - area2);
        return list;
    }
    private static void splitAtSelfTouchHelper(SimplePolygon simple, ArrayList<SimplePolygon> list)
    {
        for (int i = 0; i + 1 < simple.numPoints(); i++)
        {
            for (int j = i + 1; j < simple.numPoints(); j++)
            {
                if(simple.getPoint(i).withinEpsilon(simple.getPoint(j)))
                {
                    SimplePolygon a = new SimplePolygon(simple);
                    SimplePolygon b = new SimplePolygon();
                    for (int k = j; k > i; k--)
                    {
                        Vector2D p = simple.getPoint(k);
                        a.removeAt(k);
                        b.add(p);
                    }
                    b.invert();
                    
                    assert Util.withinEpsilon(a.signedArea() + b.signedArea() - simple.signedArea());
                    //assert Math.abs(a.signedArea()) <= Math.abs(simple.signedArea()) + Util.Epsilon: a.signedArea() + " / " + simple.signedArea();
                    //assert Math.abs(b.signedArea()) <= Math.abs(simple.signedArea()) + Util.Epsilon: b.signedArea() + " / " + simple.signedArea();
                    splitAtSelfTouchHelper(a, list);
                    splitAtSelfTouchHelper(b, list);
                    return;
                }
            }
        }
        simple.smooth();
        if(simple.numPoints() >= 3) list.add(simple);
    }
    
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
    public static int numCommonPoints(SimplePolygon a, SimplePolygon b)
    {
        int count = 0;
        for (int i = 0; i < a.numPoints(); i++)
        {
            if (b.hasPoint(a.getPoint(i))) count++;
        }
        return count;
    }
    public static void addIntersectionPoints(SimplePolygon target, SimplePolygon source)
    {
        insertIntersectionPoints(target, new SimplePolygon(source));
    }
    private static void insertIntersectionPoints(SimplePolygon a, SimplePolygon b)
    {
        assert(!a.hasRepetitions());
        assert(!b.hasRepetitions());

        HashMap<Integer, HashSet<Vector2D>> intersectionsA = new HashMap<Integer, HashSet<Vector2D>>();
        HashMap<Integer, HashSet<Vector2D>> intersectionsB = new HashMap<Integer, HashSet<Vector2D>>();
        
        for (int i = 0; i < a.numPoints(); i++)
        {
            int j = (i + 1) % a.numPoints();
            for (int k = 0; k < b.numPoints(); k++)
            {
                int l = (k + 1) % b.numPoints();
                if(!Vector2DUtil.segmentAaBbCheck(a.getPoint(i), a.getPoint(j), b.getPoint(k), b.getPoint(l))) continue;
                Vector2D intersection = Vector2DUtil.lineSegmentIntersectionPointWithoutCorners(a.getPoint(i), a.getPoint(j), b.getPoint(k), b.getPoint(l));
                if (intersection != null)
                {
                    assert(!a.hasPoint(intersection));
                    assert(!b.hasPoint(intersection));
                    if (!intersectionsA.containsKey(i)) intersectionsA.put(i, new HashSet<Vector2D>());
                    if (!intersectionsB.containsKey(k)) intersectionsB.put(k, new HashSet<Vector2D>());
                    intersectionsA.get(i).add(intersection);
                    intersectionsB.get(k).add(intersection);
                }
            }
        }
        insertIntersectionHelper(a, intersectionsA);
        insertIntersectionHelper(b, intersectionsB);
        
        assert(!a.hasRepetitions());
        assert(!b.hasRepetitions());
    }
    private static void insertIntersectionHelper(SimplePolygon simple, HashMap<Integer, HashSet<Vector2D>> intersections)
    {
        for (int i = simple.numPoints() - 1; i >= 0; i--)
        {
            if (intersections.containsKey(i))
            {
                ArrayList<Vector2D> list = new ArrayList<Vector2D>(intersections.get(i));
                Vector2D point = simple.getPoint(i);
                Collections.sort(list, new PointDistanceComparator(point));
                simple.insertRange(i + 1, list);
            }
        }
    }

    private static boolean polysLinesIntersectIndirectlyHelper(SimplePolygon a, SimplePolygon b)
    {
        assert a.numPoints() >= 3;
        assert b.numPoints() >= 3;
        
        Containment cont = Containment.Border;
        for (int i = 0; i < b.numPoints(); i++)
        {
            int j = (i + 1) % b.numPoints();
            Containment tmp = a.areaContains(Vector2DUtil.avg(b.getPoint(i), b.getPoint(j)));
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
        insertIntersectionPoints(a, b);
        a.insertTouchPoints(a);
        a.insertTouchPoints(b);
        b.insertTouchPoints(a);
        assert(!a.hasRepetitions());
        assert(!b.hasRepetitions());
    }
     
    public static boolean outlinesIntersect(SimplePolygon a, SimplePolygon b)
    {
        assert(!a.hasRepetitions());
        assert(!b.hasRepetitions());
        for (int i = 0; i < a.numPoints(); i++)
        {
            int j = (i + 1) % a.numPoints();
            if(outlineIntersectsSegment(b, a.getPoint(i), a.getPoint(j))) return true;
        }
        b = new SimplePolygon(b);
        b.insertTouchPoints(a);
        return polysLinesIntersectIndirectlyHelper(a, b);
    }
     
    public static boolean outlineIntersectsSegment(SimplePolygon simple, Vector2D a, Vector2D b)
    {
        for (int i = 0; i < simple.numPoints(); i++)
        {
            int j = (i + 1) % simple.numPoints();
            
            if(!Vector2DUtil.segmentAaBbCheck(a, b, simple.getPoint(i), simple.getPoint(j))) continue;
            boolean inter = Vector2DUtil.lineSegmentIntersectionPointWithoutCorners(a, b, simple.getPoint(i), simple.getPoint(j)) != null;
            if (inter)
            {
                return true;
            }
        }
        return false;
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
        assert simple.isValid();
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
        ArrayList<Vector2D> points = new ArrayList<Vector2D>();
        for (int i = 0; i < num; i++)
        {
            points.add(new Vector2D(buffer.readDouble(), buffer.readDouble()));
        }
        SimplePolygon simple = new SimplePolygon(points);
        assert simple.isValid();
        return simple;
    }
    
    static SimplePolygon transform(SimplePolygon simple, Transform2D t)
    {
        if(simple == null) return null;
        SimplePolygon result = new SimplePolygon();
        for (int i = 0; i < simple.numPoints(); i++)
        {
            result.add(t.transform(simple.getPoint(i)));
        }
        return result;
    }
    
    static HashSet<Vector2D> points(SimplePolygon simple)
    {
        HashSet<Vector2D> points = new HashSet<Vector2D>();
        for (int i = 0; i < simple.numPoints(); i++)
        {
            points.add(simple.getPoint(i));
        }
        return points;
    }

    static ArrayList<Vector2D> outline(SimplePolygon simple)
    {
        ArrayList<Vector2D> outline = new ArrayList<Vector2D>();
        for (int i = 0; i < simple.numPoints(); i++) {
            outline.add(simple.getPoint(i));
        }
        return outline;
    }
    static void edges(ArrayList<Vector2D> edges, SimplePolygon polygon)
    {
        for (int i = 0; i < polygon.numPoints(); i++)
        {
            int j = (i + 1) % polygon.numPoints();
            
            edges.add(polygon.getPoint(i));
            edges.add(polygon.getPoint(j));
        }
    }
}
