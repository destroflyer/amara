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
class SimplePolygonUtil
{
    public static ArrayList<Point2D> triangles_wrong(SimplePolygon simple)
    {
        ArrayList<Point2D> list = new ArrayList<Point2D>();
        for (SimplePolygon subPoly : splitAtSelfTouch(simple, 1))
        {
            trianglesHelper(subPoly, list);
        }
        return list;
    }
    private static void trianglesHelper(SimplePolygon simple, ArrayList<Point2D> tris)
    {
        assert simple.isAreaPositive();
        assert simple.isSmooth();
        if(simple.numPoints() == 3)
        {
            for (int i = 0; i < 3; i++)
            {
                tris.add(simple.getPoint(i));
                return;
            }
        }
        Random rnd = new Random();
        int offset = rnd.nextInt(simple.numPoints());
        for (int i = 0; i + 2 < simple.numPoints(); i++)
        {
            for (int j = i + 2; j < simple.numPoints(); j++)
            {
                int k = (i + offset) % simple.numPoints();
                int l = (j + offset) % simple.numPoints();
                
                if(simple.getPoint(k).equals(simple.getPoint(l))) continue;
                if(isValidCut(simple, simple.getPoint(k), simple.getPoint(l)))
                {
                    for (SimplePolygon subPoly : cut(simple, k, l))
                    {
                        assert splitAtSelfTouch(subPoly, 1).size() == 1;
                        trianglesHelper(subPoly, tris);
                    }
                    return;
                }
            }
        }
//        ArrayList<SimplePolygon> sp = splitAtSelfTouch(simple, 0);
//        boolean neg = false;
//        for (SimplePolygon s : sp)
//        {
//            if(s.isHole())
//            {
//                neg = true;
//                break;
//            }
//        }
//        assert neg;
//        assert splitAtSelfTouch(simple, 1).size() == 1;
        new Polygon(new SetPolygon(simple)).writeToFile("error");
        throw new Error("" + simple.numPoints());
    }
    private static ArrayList<SimplePolygon> cut(SimplePolygon simple, int i, int j)
    {
        assert simple.isValid();
        if(j < i)
        {
            int tmp = i;
            i = j;
            j = tmp;
        }
        assert simple.isAreaPositive();
        assert i < j;
        assert isValidCut(simple, simple.getPoint(i), simple.getPoint(j));
        SimplePolygon a = new SimplePolygon(simple);
        SimplePolygon b = new SimplePolygon();
        
        for (int k = i; k <= j; k++)
        {
            b.add(a.getPoint(k));
        }
        for (int k = j - 1; k > i; k--)
        {
            a.removeAt(k);
        }
        
//        b.add(simple.getPoint(j));
//        for (int k = j - 1; k > i; k--)
//        {
//            Point2D p = a.getPoint(k);
//            a.removeAt(k);
//            b.add(p);
//        }
//        b.add(simple.getPoint(i));
//        b.invert();
        
        assert Util.withinEpsilon(a.signedArea() + b.signedArea() - simple.signedArea());
        assert a.isAreaPositive();
        assert b.isAreaPositive();
        
        assert simple.numPoints() + 2 == a.numPoints() + b.numPoints();
        
        assert a.isValid();
        assert b.isValid();
        
        assert !outlinesIntersect(a, b): a.numPoints() + " / " + b.numPoints();
        assert !outlinesIntersect(a, a);
        assert !outlinesIntersect(b, b);
        
        if(b.isHole())
        {
            ArrayList<SetPolygon> polys = new ArrayList<SetPolygon>();
            //polys.add(new SetPolygon(simple));
            polys.add(new SetPolygon(a));
            polys.add(new SetPolygon(b));
            SetPolygonUtil.writePolys("errors", polys);
        }
        
        ArrayList<SimplePolygon> list = new ArrayList<SimplePolygon>();
        list.add(a);
        list.add(b);
        return list;
    }
    public static ArrayList<Point2D> triangles(SimplePolygon simple)
    {
        assert simple.isValid();
        assert simple.isSmooth();
        //assert !simple.isSelfTouching();
        assert simple.isAreaPositive();
        ArrayList<Point2D> list = new ArrayList<Point2D>();
        if(simple.numPoints() < 3) return list;
        SimplePolygon helper = new SimplePolygon(simple);
        while(helper.numPoints() > 3)
        {
            assert !SimplePolygonUtil.outlinesIntersect(helper, helper);
            int i = findEar(helper);
            if(i == -1)
            {
                hardCutTri(helper, list);
                return list;
//                ArrayList<SimplePolygon> split = splitAtSelfTouch(helper, 1);
//                //assert split.size() > 1;
//                if(split.size() == 1)
//                {
//                    //assert !split.get(0).equals(helper);
//                    new Polygon(new SetPolygon(split.get(0))).writeToFile("error");
//                    throw new Error("no ear found");
//                }
//                assert !split.isEmpty();
//                for (SimplePolygon splitPoly : split)
//                {
//                    list.addAll(triangles(splitPoly));
//                }
//                return list;
            }
            int h = (i + helper.numPoints() - 1) % helper.numPoints();
            int j = (i + 1) % helper.numPoints();
            
            list.add(helper.getPoint(h));
            list.add(helper.getPoint(i));
            list.add(helper.getPoint(j));
            
            helper.removeAt(i);
            System.out.println("" + helper.numPoints());
        }
        for (int i = 0; i < 3; i++)
        {
            list.add(helper.getPoint(i));
        }
        assert list.size() / 3 == simple.numPoints() - 2: "" + list.size() + " / " + simple.numPoints();
        return list;
    }
    private static void hardCutTri(SimplePolygon simple, ArrayList<Point2D> tris)
    {
        assert simple.isValid();
        assert simple.isAreaPositive();
        
        for (int i = 0; i < simple.numPoints(); i++)
        {
            int j = (i + 1) % simple.numPoints();
            for (int k = j; k < simple.numPoints(); k++)
            {
                int l = (k + 1) % simple.numPoints();
                
                if(simple.getPoint(j).equals(simple.getPoint(k)))
                {
                    assert i < k: i + " / " + k;
                    assert j <= k: j + " / " + k;
                    assert k < l: k + " / " + l;
                    if(isValidCut(simple, simple.getPoint(i), simple.getPoint(l)))
                    {
                        //assert false: i + ", " + j + ", " + k + ", " + l;
                        SimplePolygon a = new SimplePolygon();
                        for (int m = 0; m <= i; m++)
                        {
                            a.add(simple.getPoint(m));
                        }
                        for (int m = l; m < simple.numPoints(); m++)
                        {
                            a.add(simple.getPoint(m));
                        }
                        if(!a.isAreaPositive()) continue;
                        assert a.isAreaPositive();
                        
                        SimplePolygon b = new SimplePolygon();
                        for (int m = j; m < k; m++)
                        {
                            b.add(simple.getPoint(m));
                        }
                        if(!b.isAreaPositive()) continue;
                        assert b.isAreaPositive();
                        
                        SimplePolygon tri = new SimplePolygon();
                        tri.add(simple.getPoint(i));
                        tri.add(simple.getPoint(j));
                        tri.add(simple.getPoint(l));
                        
                        assert Util.withinEpsilon(a.signedArea() + b.signedArea() + tri.signedArea() - simple.signedArea());
                        tris.add(simple.getPoint(i));
                        tris.add(simple.getPoint(j));
                        tris.add(simple.getPoint(l));
                        
                        tris.addAll(triangles(a));
                        tris.addAll(triangles(b));
                        return;
                    }
                }
            }
        }
        throw new Error();
    }
    private static int findEar(SimplePolygon simple)
    {
        assert simple.numPoints() > 3;
        assert !simple.isHole();
        assert simple.isSmooth();
        Random rnd = new Random();
        int offset = rnd.nextInt(simple.numPoints());
        for (int k = 0; k < simple.numPoints(); k++)
        {
            int h = (k + offset) % simple.numPoints();
            int j = (h + 2) % simple.numPoints();
            int i = (h + 1) % simple.numPoints();
//            boolean b = true;
            if(Point2DUtil.lineSide(simple.getPoint(j), simple.getPoint(h), simple.getPoint(i)) < 0)
            {
                if(Point2DUtil.lineSide(simple.getPoint(h), simple.getPoint(i), simple.getPoint(j)) < 0)
                {
                    if(!outlineIntersectsSegment(simple, simple.getPoint(j), simple.getPoint(h)))
                    {
                        //assert isValidCut(simple, simple.getPoint(j), simple.getPoint(h));
//                        b = false;
                        return i;
                    }
                }
            }
//            if(b) assert !isValidCut(simple, simple.getPoint(j), simple.getPoint(h));
//            if(isValidCut(simple, simple.getPoint(h), simple.getPoint(j)))
//            {
//                return i;
//            }
        }
        return -1;
    }
    public static boolean isValidCut(SimplePolygon simple, Point2D a, Point2D b)
    {
        assert !a.equals(b);
        return !outlineIntersectsSegment(simple, a, b) && simple.areaContains(Point2DUtil.avg(a, b)) == Containment.Inside;
    }
    
    public static ArrayList<SimplePolygon> splitAtSelfTouch(SimplePolygon simple, double sign)
    {
        double area = simple.signedArea();
        assert area * sign >= 0;
        ArrayList<SimplePolygon> list = new ArrayList<SimplePolygon>();
        SimplePolygon helper = new SimplePolygon(simple);
        insertCommonOutlinePoints(helper, helper);
        splitAtSelfTouchHelper(helper, list, sign);
        double area2 = 0;
        for (SimplePolygon simplePolygon : list)
        {
            area2 += simplePolygon.signedArea();
        }
        assert Util.withinEpsilon(area - area2);
        return list;
    }
    private static void splitAtSelfTouchHelper(SimplePolygon simple, ArrayList<SimplePolygon> list, double sign)
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
                        Point2D p = simple.getPoint(k);
                        a.removeAt(k);
                        b.add(p);
                    }
                    b.invert();
                    
                    if(a.signedArea() * sign < 0 || b.signedArea() * sign < 0) continue;
                    
                    assert Util.withinEpsilon(a.signedArea() + b.signedArea() - simple.signedArea());
                    //assert Math.abs(a.signedArea()) <= Math.abs(simple.signedArea()) + Util.Epsilon: a.signedArea() + " / " + simple.signedArea();
                    //assert Math.abs(b.signedArea()) <= Math.abs(simple.signedArea()) + Util.Epsilon: b.signedArea() + " / " + simple.signedArea();
                    splitAtSelfTouchHelper(a, list, sign);
                    splitAtSelfTouchHelper(b, list, sign);
                    return;
                }
            }
        }
        assert !(sign == 0 && simple.isSelfTouching());
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
        assert a.numPoints() >= 3;
        assert b.numPoints() >= 3;
        for (int i = 0; i < a.numPoints(); i++)
        {
            Point2D p2 = a.getPoint(i);
            for (int j = 0; j < b.numPoints(); j++)
            {
                Point2D p5 = b.getPoint(j);
                if(p2.withinEpsilon(p5))
                {
                    Point2D p1 = a.getPoint((i + a.numPoints() - 1) % a.numPoints());
                    Point2D p3 = a.getPoint((i + 1) % a.numPoints());
                    Point2D p4 = b.getPoint((j + b.numPoints() - 1) % b.numPoints());
                    Point2D p6 = b.getPoint((j + 1) % b.numPoints());
                    Point2D p = Point2DUtil.avg(p2, p5);
                    if(Point2DUtil.chainsCenterIntersection(p1, p3, p4, p6, p)) return true;
                }
            }
        }
        return false;
        
//        Containment cont = Containment.Border;
//        for (int i = 0; i < b.numPoints(); i++)
//        {
//            int j = (i + 1) % b.numPoints();
//            Containment tmp = a.areaContains(Point2DUtil.avg(b.getPoint(i), b.getPoint(j)));
//            if (tmp == Containment.Border) continue;
//            if (cont == Containment.Border) cont = tmp;
//            if (cont != tmp) return true;
//        }
//        return false;
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
            if(outlineIntersectsSegment(b, a.getPoint(i), a.getPoint(j))) return true;
        }
        //a = a.ToPolygon();
        //a.InsertTouchPoints(b);
        b = new SimplePolygon(b);
        b.insertTouchPoints(a);
        //if (PolysLinesIntersectIndirectlyHelper(a, b)) return true;
        //b.Reverse();
        return polysLinesIntersectIndirectlyHelper(a, b);
    }
     
    public static boolean outlineIntersectsSegment(SimplePolygon simple, Point2D a, Point2D b)
    {
        for (int i = 0; i < simple.numPoints(); i++)
        {
            int j = (i + 1) % simple.numPoints();
            boolean inter = Point2DUtil.lineSegmentIntersectionPointWithoutCorners(a, b, simple.getPoint(i), simple.getPoint(j)) != null;
            if (inter) return true;
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
        ArrayList<Point2D> points = new ArrayList<Point2D>();
        for (int i = 0; i < num; i++)
        {
            points.add(new Point2D(buffer.readDouble(), buffer.readDouble()));
        }
        SimplePolygon simple = new SimplePolygon(points);
        assert simple.isValid();
        return simple;
    }
    
    static SimplePolygon transform(SimplePolygon simple, Transform t)
    {
        if(simple == null) return null;
        SimplePolygon result = new SimplePolygon();
        for (int i = 0; i < simple.numPoints(); i++)
        {
            result.add(t.transform(simple.getPoint(i)));
        }
        return result;
    }
    
    static HashSet<Point2D> points(SimplePolygon simple)
    {
        HashSet<Point2D> points = new HashSet<Point2D>();
        for (int i = 0; i < simple.numPoints(); i++)
        {
            points.add(simple.getPoint(i));
        }
        return points;
    }
}
