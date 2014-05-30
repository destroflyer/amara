/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Philipp
 */
class SetPolygonUtil
{
    public static ArrayList<SimplePolygon> cutPolys(SetPolygon setPoly)
    {
        ArrayList<SimplePolygon> list = new ArrayList<SimplePolygon>();
        for (int i = 0; i < setPoly.numPolygons(); i++)
        {
            SimplePolygon cutPoly = HolePolygonUtil.cutPolygon(setPoly.getPolygon(i));
            list.add(cutPoly);
            assert !cutPoly.isHole();
        }
        System.out.println("" + list.size() + " cutPolys");
        return list;
    }
    public static ArrayList<Point2D> triangles(SetPolygon setPoly)
    {
        ArrayList<Point2D> list = new ArrayList<Point2D>();
        for (SimplePolygon simple : cutPolys(setPoly))
        {
            assert simple.isAreaPositive();
            //list.addAll(SimplePolygonUtil.triangles(simple));
            for (SimplePolygon simplePolygon : SimplePolygonUtil.splitAtSelfTouch(simple, 1))
            {
                assert simplePolygon.isAreaPositive(): simplePolygon.signedArea();
                list.addAll(SimplePolygonUtil.triangles(simplePolygon));
            }
            System.out.println("" + list.size() / 3 + " tris so far");
        }
        return list;
    }
    
    public static void preSortedUnion(ArrayList<SetPolygon> polys)
    {
        while(polys.size() > 1)
        {
            for (int i = 0; i + 1 < polys.size(); i++)
            {
                SetPolygon poly = union(polys.get(i), polys.get(i + 1));
                polys.set(i, poly);
                polys.remove(i + 1);
            }
        }
    }
    
    public static boolean intersect(SetPolygon a, SetPolygon b)
    {
        for (int i = 0; i < a.numPolygons(); i++)
        {
            for (int j = 0; j < b.numPolygons(); j++)
            {
                if (HolePolygonUtil.outlinesIntersect(a.getPolygon(i), b.getPolygon(j))) return true;
                if (HolePolygonUtil.haveOverlappingAreas(a.getPolygon(i), b.getPolygon(j))) return true;
            }
        }
        return false;
    }
    public static boolean haveTouchingEdge(SetPolygon a, SetPolygon b)
    {
        for (int k = 0; k < a.numPolygons(); k++)
        {
            HolePolygon poly = a.getPolygon(k);
            for (int l = 0; l < poly.numSimplePolys(); l++)
            {
                SimplePolygon simple = poly.getSimplePoly(l);
                for (int i = 0; i < simple.numPoints(); i++)
                {
                    int j = (i + 1) % simple.numPoints();

                    if (b.hasEdge(simple.getPoint(j), simple.getPoint(i))) return true;
                }
            }
        }
        return false;
    }
    public static boolean haveCommonPoint(SetPolygon a, SetPolygon b)
    {
        for (int k = 0; k < a.numPolygons(); k++)
        {
            HolePolygon poly = a.getPolygon(k);
            for (int l = 0; l < poly.numSimplePolys(); l++)
            {
                SimplePolygon simple = poly.getSimplePoly(l);
                for (int i = 0; i < simple.numPoints(); i++)
                {
                    if (b.hasPoint(simple.getPoint(i))) return true;
                }
            }
        }
        return false;
    }
    public static void insertCommonOutlinePoints(SetPolygon a, SetPolygon b)
    {
        assert(!a.hasLooseLines());
        assert(!b.hasLooseLines());
        for (int i = 0; i < a.numPolygons(); i++)
        {
            HolePolygon pA = a.getPolygon(i);
            for (int j = 0; j < a.numPolygons(); j++)
            {
                HolePolygon pB = a.getPolygon(j);
                if (pA == pB) continue;
                HolePolygonUtil.insertCommonOutlinePoints(pA, pB);
            }
        }
        for (int i = 0; i < a.numPolygons(); i++)
        {
            HolePolygon pA = a.getPolygon(i);
            for (int j = 0; j < b.numPolygons(); j++)
            {
                HolePolygon pB = b.getPolygon(j);
                HolePolygonUtil.insertCommonOutlinePoints(pA, pB);
            }
        }
        assert(!a.hasLooseLines());
        assert(!b.hasLooseLines());
    }
    

    public static SetPolygon inverse(SetPolygon set)
    {
        ArrayList<SimplePolygon> simpleSet = new ArrayList<SimplePolygon>();
        for (int i = 0; i < set.numPolygons(); i++)
        {
            HolePolygon poly = set.getPolygon(i);
            for (int j = 0; j < poly.numSimplePolys(); j++)
            {
                SimplePolygon simple = poly.getSimplePoly(j);
                simpleSet.add(simple.inverse());
            }
        }
        SetPolygon setPoly = simpleSetToSetPolygon(simpleSet, !set.isInfinite());

        SetPolygon check = exclude(new SetPolygon(new HolePolygon()), set);
        assert(Util.withinEpsilon(setPoly.signedArea() + set.signedArea()));
        assert(Util.withinEpsilon(setPoly.signedArea() - check.signedArea()));
        return setPoly;
    }
    public static SetPolygon union(SetPolygon A, SetPolygon B)
    {
        SetPolygon set = operate(A, B, PolygonOperation.Union);
        return set;
    }
    public static SetPolygon intersection(SetPolygon A, SetPolygon B)
    {
        SetPolygon set = operate(A, B, PolygonOperation.Intersection);
        assert(set.numPolygons() == 0 || intersect(A, B));
        return set;
    }
    public static SetPolygon exclude(SetPolygon A, SetPolygon B)
    {
        SetPolygon set = operate(A, B, PolygonOperation.Exclude);
        return set;
    }
    private static SetPolygon operate(SetPolygon A, SetPolygon B, PolygonOperation o)
    {
        //System.out.println("a");
        boolean smoothA = A.isSmooth();
        boolean smoothB = B.isSmooth();
        assert(!A.hasLooseLines());
        assert(!B.hasLooseLines());
        SetPolygon a = new SetPolygon(A);
        SetPolygon b = new SetPolygon(B);
        assert(smoothA == A.isSmooth());
        assert(smoothB == B.isSmooth());
        insertCommonOutlinePoints(a, b);
        //System.out.println("b");
//        SetPolygon c = new SetPolygon(a);
//        SetPolygon d = new SetPolygon(b);
//        insertCommonOutlinePoints(c, d);
//        //System.out.println("c");
//        assert(a.equals(c) && b.equals(d));
        assert(smoothA == A.isSmooth());
        assert(smoothB == B.isSmooth());
        //System.out.println("d");
        ArrayList<Point2D> edges = operationEdges(a, b, o);
        //System.out.println("e");
        ArrayList<SimplePolygon> simpleSet = edgesToSimpleSet(edges);
        //System.out.println("f");
        SetPolygon polySet = simpleSetToSetPolygon(simpleSet, isInfinite(A.isInfinite(), B.isInfinite(), o));
        polySet.smooth();
        //System.out.println("g");
        return polySet;
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
    public static ArrayList<Point2D> operationEdges(SetPolygon A, SetPolygon B, PolygonOperation o)
    {
        assert(!A.hasLooseLines());
        assert(!B.hasLooseLines());

        ArrayList<Point2D> list = new ArrayList<Point2D>();

        for (int k = 0; k < A.numPolygons(); k++)
        {
            HolePolygon hole = A.getPolygon(k);
            for (int l = 0; l < hole.numSimplePolys(); l++)
            {
                SimplePolygon simple = hole.getSimplePoly(l);
                for (int i = 0; i < simple.numPoints(); i++)
                {
                    int j = (i + 1) % simple.numPoints();

                    operationLineHelperA(list, simple.getPoint(i), simple.getPoint(j), B, o);
                }
            }
        }
        for (int k = 0; k < B.numPolygons(); k++)
        {
            HolePolygon hole = B.getPolygon(k);
            for (int l = 0; l < hole.numSimplePolys(); l++)
            {
                SimplePolygon simple = hole.getSimplePoly(l);
                for (int i = 0; i < simple.numPoints(); i++)
                {
                    int j = (i + 1) % simple.numPoints();

                    operationLineHelperB(list, simple.getPoint(i), simple.getPoint(j), A, o);
                }
            }
        }
        assert(edgeCheck(list));
        return list;
    }
    private static void operationLineHelperA(ArrayList<Point2D> edges, Point2D a, Point2D b, SetPolygon B, PolygonOperation o)
    {
        switch (o)
        {
            case Union:
                if (B.areaContains(Point2DUtil.avg(a, b)) == Containment.Outside
                        || B.hasEdge(a, b))
                {
                    edges.add(a);
                    edges.add(b);
                }
                break;
            case Exclude:
                if (B.areaContains(Point2DUtil.avg(a, b)) == Containment.Outside
                        || B.hasEdge(b, a))
                {
                    edges.add(a);
                    edges.add(b);
                }
                break;
            case Intersection:
                if (B.areaContains(Point2DUtil.avg(a, b)) == Containment.Inside
                        || B.hasEdge(a, b))
                {
                    edges.add(a);
                    edges.add(b);
                }
                break;
            default:
                throw new NotImplementedException();
        }
        assert(edgeDuplicateCheck(edges));
    }
    private static void operationLineHelperB(ArrayList<Point2D> edges, Point2D a, Point2D b, SetPolygon A, PolygonOperation o)
    {
        switch (o)
        {
            case Union:
                if (A.areaContains(Point2DUtil.avg(a, b)) == Containment.Outside)
                {
                    edges.add(a);
                    edges.add(b);
                }
                break;
            case Exclude:
                if (A.areaContains(Point2DUtil.avg(a, b)) == Containment.Inside)
                {
                    edges.add(b);
                    edges.add(a);
                }
                break;
            case Intersection:
                if (A.areaContains(Point2DUtil.avg(a, b)) == Containment.Inside)
                {
                    edges.add(a);
                    edges.add(b);
                }
                break;
            default:
                throw new NotImplementedException();
        }
        assert(edgeDuplicateCheck(edges));
    }
    private static boolean edgeDuplicateCheck(List<Point2D> edges)
    {
        assert((edges.size() & 1) == 0);

        for (int i = 0; i + 2 < edges.size(); i += 2)
        {
            for (int j = i + 2; j < edges.size(); j += 2)
            {
                assert(!(edges.get(i).equals(edges.get(j)) && edges.get(i + 1).equals(edges.get(j + 1))));
            }
        }
        return true;
    }
    private static boolean edgeCheck(List<Point2D> edges)
    {
        edgeDuplicateCheck(edges);

        //for (int i = edges.size() - 2; i >= 0; i -= 2)
        //{
        //    for (int j = i + 2; j < edges.size(); j += 2)
        //    {
        //        if (edges[i].equals(edges[j]) && edges[i + 1].equals(edges[j + 1]))
        //        {
        //            edges.RemoveAt(i + 1);
        //            edges.RemoveAt(i);
        //        }
        //    }
        //}

        HashMap<Point2D, Integer> starts = new HashMap<Point2D, Integer>();
        HashMap<Point2D, Integer> ends = new HashMap<Point2D, Integer>();

        HashSet<Point2D> points = new HashSet<Point2D>(edges);
        for(Point2D point: points)
        {
            starts.put(point, 0);
            ends.put(point, 0);
        }

        for (int i = 0; i < edges.size(); i += 2)
        {
            starts.put(edges.get(i), starts.get(edges.get(i)) + 1);
            ends.put(edges.get(i), ends.get(edges.get(i)) + 1);
        }

        for(Point2D point: points)
        {
            //assert(starts[point] == ends[point]);
            if (starts.get(point) != ends.get(point)) return false;
        }
        return true;
    }
    private static ArrayList<SimplePolygon> edgesToSimpleSet(List<Point2D> edges)
    {
        assert(edgeCheck(edges));
        ArrayList<SimplePolygon> result = new ArrayList<SimplePolygon>();
        ArrayList<ArrayList<Point2D>> chains = new ArrayList<ArrayList<Point2D>>();
        for (int i = 0; i < edges.size(); i += 2)
        {
            Point2D A = edges.get(i);
            Point2D B = edges.get(i + 1);

            int endsWithA = -1;
            int startsWithB = -1;

            for (int j = 0; j < chains.size(); j++)
            {
                ArrayList<Point2D> chain = chains.get(j);
                if (chain.get(chain.size() - 1).withinEpsilon(A)) endsWithA = j;
                if (chain.get(0).withinEpsilon(B)) startsWithB = j;
            }

            if (endsWithA == -1)
            {
                if (startsWithB == -1)
                {
                    ArrayList<Point2D> C = new ArrayList<Point2D>();
                    C.add(A);
                    C.add(B);
                    chains.add(C);
                }
                else
                {
                    chains.get(startsWithB).add(0, A);
                }
            }
            else
            {
                if (startsWithB == -1)
                {
                    chains.get(endsWithA).add(B);
                }
                else
                {
                    if (endsWithA == startsWithB)
                    {
                        result.add(new SimplePolygon(chains.get(endsWithA)));
                        chains.remove(endsWithA);
                    }
                    else
                    {
                        chains.get(endsWithA).addAll(chains.get(startsWithB));
                        chains.remove(startsWithB);
                    }
                }
            }
        }
        boolean bla = !chains.isEmpty();
//        if(bla)
//            System.out.println("error in polygon operation, trying to hide it...");
        while(!chains.isEmpty())
        {
            double permittedErrorSquared = 0.01d;
            ArrayList<Point2D> firstChain = chains.get(0);
            Point2D last = firstChain.get(firstChain.size() - 1);
            for (int i = 0; i < chains.size(); i++)
            {
                Point2D first = chains.get(i).get(0);
                if(first.squaredDistance(last) < permittedErrorSquared)
                {
                    Point2D avg = Point2DUtil.avg(first, last);
                    firstChain.set(firstChain.size() - 1, avg);
                    chains.get(i).remove(0);
                    if(i != 0)
                    {
                        firstChain.addAll(chains.get(i));
                        chains.remove(i);
                    }
                    else
                    {
                        result.add(new SimplePolygon(firstChain));
                        chains.remove(0);
                    }
                    break;
                }
            }
        }
        if(bla)
        {
            if(!chains.isEmpty())
                System.out.println("could not hide polygon operation error, polygon(s) discarded...");
//            else System.out.println("*fixed*");
        }
        assert(chains.isEmpty()): "" + chains + result;
        return result;
    }
    private static double simpleSetArea(List<SimplePolygon> simpleSet)
    {
        double area = 0d;
        for(SimplePolygon simple: simpleSet)
        {
            area += simple.signedArea();
        }
        return area;
    }
    public static SetPolygon simpleSetToSetPolygon(ArrayList<SimplePolygon> simpleSet, boolean infinite)
    {
        ArrayList<SimplePolygon> simples = new ArrayList<SimplePolygon>(simpleSet);
        for (int i = simples.size() - 1; i >= 0; i--)
        {
            simples.get(i).smooth();
            if (!simples.get(i).hasArea()) simples.remove(i);
        }

        SetPolygon result = new SetPolygon();
        double area = simpleSetArea(simples);

        for (int i = simples.size() - 1; i >= 0; i--)
        {
            SimplePolygon current = simples.get(i);
            if (!current.isHole())
            {
                result.add(new HolePolygon(current));
                simples.remove(i);
            }
            else if (!current.hasArea())
            {
                assert(false);
                simples.remove(i);
            }
        }

        ArrayList<SimplePolygon> leftOvers = new ArrayList<SimplePolygon>();
        for(SimplePolygon hole: simples)
        {
            assert(hole.isHole());
            boolean parentFound = false;
            for (int i = 0; i < result.numPolygons(); i++)
            {
                HolePolygon current = result.getPolygon(i);
                SimplePolygon main = current.mainPoly();
                Point2D avg = Point2DUtil.avg(hole.getPoint(0), hole.getPoint(1));
                if (main.areaContains(avg) == Containment.Inside)
                {
                    for (int j = 0; j < hole.numPoints(); j++)
                    {
                        int k = (j + 1) % hole.numPoints();

                        assert(main.areaContains(Point2DUtil.avg(hole.getPoint(j), hole.getPoint(k))) == Containment.Inside);
                    }
                    current.add(hole);
                    parentFound = true;
                    break;
                }
            }
            if (!parentFound) leftOvers.add(hole);
        }

        if(infinite)
        {
            HolePolygon poly = new HolePolygon(null, leftOvers);
            result.add(poly);
        }

        assert(Util.withinEpsilon(result.signedArea() - area));
        return result;
    }

    public static void write(ByteBuffer buffer, SetPolygon poly)
    {
        assert poly.isValid();
        buffer.writeInt(poly.numPolygons());
        for (int i = 0; i < poly.numPolygons(); i++)
        {
            HolePolygonUtil.write(buffer, poly.getPolygon(i));
        }
    }
    public static SetPolygon read(ByteBuffer buffer)
    {
        SetPolygon set = new SetPolygon();
        int num = buffer.readInt();
        for (int i = 0; i < num; i++)
        {
            set.add(HolePolygonUtil.read(buffer));
        }
        assert set.isValid();
        return set;
    }
    public static void writePolys(ByteBuffer buffer, List<SetPolygon> polys)
    {
        buffer.writeInt(polys.size());
        for (int i = 0; i < polys.size(); i++)
        {
            write(buffer, polys.get(i));
        }
    }
    public static List<SetPolygon> readPolys(ByteBuffer buffer)
    {
        ArrayList<SetPolygon> list = new ArrayList<SetPolygon>();
        int num = buffer.readInt();
        for (int i = 0; i < num; i++)
        {
            list.add(read(buffer));
        }
        return list;
    }

    public static void writePolys(String filename, List<SetPolygon> polys)
    {
        ByteBuffer buffer = new ByteBuffer();
        writePolys(buffer, polys);
        byte[] data = buffer.toByteData();
        ByteBuffer bufferA = new ByteBuffer();
        bufferA.writeInt(data.length);

        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(filename);
            stream.write(bufferA.toByteData());
            stream.write(data);
            stream.close();
        } catch (Exception ex) {
            Logger.getLogger(SetPolygonUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                Logger.getLogger(SetPolygonUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    static SetPolygon transform(SetPolygon set, Transform t)
    {
        SetPolygon result = new SetPolygon();
        for (int i = 0; i < set.numPolygons(); i++)
        {
            result.add(HolePolygonUtil.transform(set.getPolygon(i), t));
        }
        return result;
    }

    static HashSet<Point2D> points(SetPolygon set)
    {
        HashSet<Point2D> points = new HashSet<Point2D>();
        for (int i = 0; i < set.numPolygons(); i++)
        {
            points.addAll(HolePolygonUtil.points(set.getPolygon(i)));
        }
        return points;
    }
}
