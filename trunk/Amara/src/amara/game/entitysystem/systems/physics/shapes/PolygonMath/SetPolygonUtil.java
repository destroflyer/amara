/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath;

import amara.game.entitysystem.systems.physics.shapes.Transform2D;
import amara.game.entitysystem.systems.physics.shapes.Vector2D;
import amara.game.entitysystem.systems.physics.shapes.Vector2DUtil;
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
    public static ArrayList<Vector2D> triangles(SetPolygon setPoly)
    {
        ArrayList<Vector2D> list = new ArrayList<Vector2D>();
        for (int i = 0; i < setPoly.numPolygons(); i++)
        {
            list.addAll(HolePolygonUtil.triangles(setPoly.getPolygon(i)));
        }
        assert triTest(list, setPoly);
        return list;
    }
    private static boolean triTest(ArrayList<Vector2D> triangles, SetPolygon poly)
    {
        double area = 0;
        for (int i = 0; i < triangles.size(); i += 3)
        {
            SimplePolygon tri = new SimplePolygon();
            tri.add(triangles.get(i));
            tri.add(triangles.get(i + 1));
            tri.add(triangles.get(i + 2));
            assert !tri.isHole(): tri.signedArea();
            assert !triangles.get(i).withinEpsilon(triangles.get(i + 1));
            assert !triangles.get(i).withinEpsilon(triangles.get(i + 2));
            assert !triangles.get(i + 1).withinEpsilon(triangles.get(i + 2));
            area += tri.signedArea();
        }
        assert Util.withinEpsilon(area - poly.signedArea());
        return true;
    }
    
    public static void preSortedUnion(ArrayList<SetPolygon> polys)
    {
        assert !polys.isEmpty();
        while(polys.size() > 1)
        {
            for (int i = 0; i + 1 < polys.size(); i++)
            {
                SetPolygon poly = union(polys.get(i), polys.get(i + 1));
                assert poly.isValid();
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
    }
    

    public static SetPolygon inverse(SetPolygon set)
    {
        ArrayList<SimplePolygon> simpleSet = new ArrayList<SimplePolygon>();
        for (int i = 0; i < set.numPolygons(); i++)
        {
            HolePolygon poly = set.getPolygon(i);
            for (int j = 0; j < poly.numSimplePolys(); j++)
            {
                simpleSet.addAll(SimplePolygonUtil.splitAtSelfTouch(poly.getSimplePoly(j).inverse()));
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
        assert A.isValid();
//        triangles(A.isInfinite()? inverse(A): A);
        assert B.isValid();
//        triangles(B.isInfinite()? inverse(B): B);
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
        ArrayList<Vector2D> edges = operationEdges(a, b, o);
        //System.out.println("e");
        ArrayList<SimplePolygon> simpleSet = edgesToSimpleSet(edges);
        //System.out.println("f");
        SetPolygon polySet = simpleSetToSetPolygon(simpleSet, isInfinite(A.isInfinite(), B.isInfinite(), o));
        polySet.smooth();
        //System.out.println("g");
        assert polySet.isValid();
//        triangles(polySet.isInfinite()? inverse(polySet): polySet);
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
    public static ArrayList<Vector2D> operationEdges(SetPolygon A, SetPolygon B, PolygonOperation o)
    {
//        assert(!A.hasLooseLines());
//        assert(!B.hasLooseLines());

        ArrayList<Vector2D> list = new ArrayList<Vector2D>();

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
    private static void operationLineHelperA(ArrayList<Vector2D> edges, Vector2D a, Vector2D b, SetPolygon B, PolygonOperation o)
    {
        switch (o)
        {
            case Union:
                if (B.areaContains(Vector2DUtil.avg(a, b)) == Containment.Outside
                        || B.hasEdge(a, b))
                {
                    edges.add(a);
                    edges.add(b);
                }
                break;
            case Exclude:
                if (B.areaContains(Vector2DUtil.avg(a, b)) == Containment.Outside
                        || B.hasEdge(b, a))
                {
                    edges.add(a);
                    edges.add(b);
                }
                break;
            case Intersection:
                if (B.areaContains(Vector2DUtil.avg(a, b)) == Containment.Inside
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
    private static void operationLineHelperB(ArrayList<Vector2D> edges, Vector2D a, Vector2D b, SetPolygon A, PolygonOperation o)
    {
        switch (o)
        {
            case Union:
                if (A.areaContains(Vector2DUtil.avg(a, b)) == Containment.Outside)
                {
                    edges.add(a);
                    edges.add(b);
                }
                break;
            case Exclude:
                if (A.areaContains(Vector2DUtil.avg(a, b)) == Containment.Inside)
                {
                    edges.add(b);
                    edges.add(a);
                }
                break;
            case Intersection:
                if (A.areaContains(Vector2DUtil.avg(a, b)) == Containment.Inside)
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
    private static boolean edgeDuplicateCheck(List<Vector2D> edges)
    {
        assert((edges.size() & 1) == 0);

        for (int i = 0; i + 2 < edges.size(); i += 2)
        {
            for (int j = i + 2; j < edges.size(); j += 2)
            {
                assert(!(edges.get(i).withinEpsilon(edges.get(j)) && edges.get(i + 1).withinEpsilon(edges.get(j + 1))));
            }
        }
        return true;
    }
    private static boolean edgeCheck(List<Vector2D> edges)
    {
        edgeDuplicateCheck(edges);

        HashMap<Vector2D, Integer> starts = new HashMap<Vector2D, Integer>();
        HashMap<Vector2D, Integer> ends = new HashMap<Vector2D, Integer>();

        HashSet<Vector2D> points = new HashSet<Vector2D>(edges);
        for(Vector2D point: points)
        {
            starts.put(point, 0);
            ends.put(point, 0);
        }

        for (int i = 0; i < edges.size(); i += 2)
        {
            starts.put(edges.get(i), starts.get(edges.get(i)) + 1);
            ends.put(edges.get(i), ends.get(edges.get(i)) + 1);
        }

        for(Vector2D point: points)
        {
            if (starts.get(point) != ends.get(point)) return false;
        }
        return true;
    }
    private static ArrayList<SimplePolygon> edgesToSimpleSet(List<Vector2D> edges)
    {
        assert(edgeCheck(edges));
        ArrayList<SimplePolygon> result = new ArrayList<SimplePolygon>();
        ArrayList<ArrayList<Vector2D>> chains = new ArrayList<ArrayList<Vector2D>>();
        for (int i = 0; i < edges.size(); i += 2)
        {
            Vector2D A = edges.get(i);
            Vector2D B = edges.get(i + 1);

            int endsWithA = -1;
            int startsWithB = -1;

            for (int j = 0; j < chains.size(); j++)
            {
                ArrayList<Vector2D> chain = chains.get(j);
                if (chain.get(chain.size() - 1).withinEpsilon(A)) endsWithA = j;
                if (chain.get(0).withinEpsilon(B)) startsWithB = j;
            }

            if (endsWithA == -1)
            {
                if (startsWithB == -1)
                {
                    ArrayList<Vector2D> C = new ArrayList<Vector2D>();
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
                        SimplePolygon simple = new SimplePolygon(chains.get(endsWithA));
                        ArrayList<SimplePolygon> simples = SimplePolygonUtil.splitAtSelfTouch(simple);
                        for (SimplePolygon simplePolygon : simples)
                        {
                            assert simplePolygon.isValid();
                        }
                        result.addAll(simples);
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
        
        if(!chains.isEmpty())
        {
            double permittedErrorSquared = 0.01d;
            ArrayList<Vector2D> firstChain = chains.get(0);
            Vector2D last = firstChain.get(firstChain.size() - 1);
            for (int i = 0; i < chains.size(); i++)
            {
                Vector2D first = chains.get(i).get(0);
                if(first.squaredDistance(last) < permittedErrorSquared)
                {
                    Vector2D avg = Vector2DUtil.avg(first, last);
                    firstChain.set(firstChain.size() - 1, avg);
                    chains.get(i).remove(0);
                    if(i != 0)
                    {
                        firstChain.addAll(chains.get(i));
                        chains.remove(i);
                    }
                    else
                    {
                        SimplePolygon simple = new SimplePolygon(firstChain);
                        ArrayList<SimplePolygon> simples = SimplePolygonUtil.splitAtSelfTouch(simple);
                        for (SimplePolygon simplePolygon : simples)
                        {
                            assert simplePolygon.isValid();
                        }
                        result.addAll(simples);
                        chains.remove(0);
                    }
                    break;
                }
            }
        }
        
        if(!chains.isEmpty())
        {
            System.out.println("could not hide polygon operation error, " + chains.size() + " polygon(s) will be deformed...");
            while(!chains.isEmpty())
            {
                ArrayList<SimplePolygon> simples = SimplePolygonUtil.splitAtSelfTouch(new SimplePolygon(chains.get(0)));
                for (SimplePolygon simplePolygon : simples)
                {
                    assert simplePolygon.isValid();
                }
                result.addAll(simples);
                chains.remove(0);
            }
        }
        assert(chains.isEmpty());
        for (SimplePolygon simplePolygon : result)
        {
            assert simplePolygon.isValid();
        }
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
        for (int i = 0; i < simples.size(); i++)
        {
            assert !simples.get(i).isSelfTouching();
            assert simples.get(i).isValid();
        }

        SetPolygon result = new SetPolygon();
        double area = simpleSetArea(simples);

        for (int i = simples.size() - 1; i >= 0; i--)
        {
            SimplePolygon current = simples.get(i);
            if (!current.isHole())
            {
                HolePolygon poly = new HolePolygon(current);
                assert poly.isValid();
                result.add(poly);
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
                Vector2D avg = Vector2DUtil.avg(hole.getPoint(0), hole.getPoint(1));
                if (main.areaContains(avg) == Containment.Inside)
                {
                    for (int j = 0; j < hole.numPoints(); j++)
                    {
                        int k = (j + 1) % hole.numPoints();

                        assert(main.areaContains(Vector2DUtil.avg(hole.getPoint(j), hole.getPoint(k))) == Containment.Inside);
                    }
                    current.add(hole);
                    assert current.isValid();
                    parentFound = true;
                    break;
                }
            }
            if (!parentFound) leftOvers.add(hole);
        }
        assert result.isValid();

        if(infinite)
        {
            HolePolygon poly = new HolePolygon(null, leftOvers);
            assert poly.isValid();
            assert result.isValid();
            result.add(poly);
            assert result.isValid();
        } else assert leftOvers.isEmpty();

        assert(Util.withinEpsilon(result.signedArea() - area));
        assert result.isValid();
        return result;
    }

    private static ArrayList<Float> createGet(HashMap<Integer, ArrayList<Float>> map, int key)
    {
        ArrayList<Float> value = map.get(key);
        if(value == null)
        {
            value = new ArrayList<Float>();
            map.put(key, value);
        }
        return value;
    }
    public static void rasterize(SetPolygon poly, RasterMap raster, Vector2D source, double squaredDistance)
    {
        HashMap<Integer, ArrayList<Float>> map = new HashMap<Integer, ArrayList<Float>>();
        
        float cx = (float) (source.getX() * raster.getScale());
        float cy = (float) (source.getY() * raster.getScale());
        float cr = (float) (squaredDistance * raster.getScale() * raster.getScale());
        
        ArrayList<ArrayList<Vector2D>> outlines = outlines(poly);
        for (int h = 0; h < outlines.size(); h++)
        {
            for (int i = 0; i < outlines.get(h).size(); i++)
            {
                int j = (i + 1) % outlines.get(h).size();
                Vector2D a = outlines.get(h).get(i).scale(raster.getScale(), raster.getScale()).add(0, -0.5);
                Vector2D b = outlines.get(h).get(j).scale(raster.getScale(), raster.getScale()).add(0, -0.5);
                if(b.getY() < a.getY())
                {
                    Vector2D tmp = a;
                    a = b;
                    b = tmp;
                }
                for (int y = (int)b.getY(); y > (int)a.getY(); y--)
                {
                    float x = (float)(Vector2DUtil.lineAxisIntersectionX(a, b, y));
                    x = Math.max(0, x);
                    x = Math.min(x, raster.getWidth() - 1);
                    
                    ArrayList<Float> line = createGet(map, y);
                    line.add(x);
                }
            }
        }
        for (Integer y : map.keySet())
        {
            ArrayList<Float> limits = map.get(y);
            Collections.sort(limits);
            for (int i = 0; i < limits.size(); i += 2)
            {
                int startX = (int)(float)limits.get(i);
                int endX = (int)(float)limits.get(i + 1);
                for (int x = startX + 1; x < endX; x++)
                {
                    raster.setValue(x, y, 1 - (Util.squaredHelper(x - cx, y - cy) / cr));
                }
                float weightA = (float)Math.floor(limits.get(i) + 1) - limits.get(i);
                float weightB = limits.get(i + 1) - (float)Math.floor(limits.get(i + 1));
                if(startX == endX)
                {
                    raster.setValue(startX, y, Math.min(weightA + weightB - 1, 1 - (Util.squaredHelper(startX - cx, y - cy) / cr)));
                }
                else
                {
                    raster.setValue(startX, y, Math.min(weightA, 1 - (Util.squaredHelper(startX - cx, y - cy) / cr)));
                    raster.setValue(endX, y, Math.min(weightB, 1 - (Util.squaredHelper(endX - cx, y - cy) / cr)));
                }
            }
        }
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
    public static ArrayList<SetPolygon> readPolys(ByteBuffer buffer)
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
        ByteBuffer lengthBuffer = new ByteBuffer();
        lengthBuffer.writeInt(data.length);

        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(filename);
            stream.write(lengthBuffer.toByteData());
            stream.write(data);
            stream.close();
        } catch (Exception ex) {
//            Logger.getLogger(SetPolygonUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(stream != null) stream.close();
            } catch (IOException ex) {
//                Logger.getLogger(SetPolygonUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static ArrayList<SetPolygon> readPolys(String filename)
    {
        FileInputStream stream = null;
        try
        {
            stream = new FileInputStream(filename);
            
            ByteBuffer buffer = new ByteBuffer();
            
            buffer.writeByte((byte)stream.read());
            buffer.writeByte((byte)stream.read());
            buffer.writeByte((byte)stream.read());
            buffer.writeByte((byte)stream.read());
            int len = buffer.readInt();
            buffer.cleanup();
            for (int i = 0; i < len; i++)
            {
                buffer.writeByte((byte)stream.read());
            }
            return readPolys(buffer);
        } catch (Exception ex) {
//            Logger.getLogger(SetPolygonUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(stream != null) stream.close();
            } catch (IOException ex) {
//                Logger.getLogger(SetPolygonUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    static SetPolygon transform(SetPolygon set, Transform2D t)
    {
        SetPolygon result = new SetPolygon();
        for (int i = 0; i < set.numPolygons(); i++)
        {
            result.add(HolePolygonUtil.transform(set.getPolygon(i), t));
        }
        return result;
    }

    static HashSet<Vector2D> points(SetPolygon set)
    {
        HashSet<Vector2D> points = new HashSet<Vector2D>();
        for (int i = 0; i < set.numPolygons(); i++)
        {
            points.addAll(HolePolygonUtil.points(set.getPolygon(i)));
        }
        return points;
    }

    static ArrayList<ArrayList<Vector2D>> outlines(SetPolygon setPoly)
    {
        ArrayList<ArrayList<Vector2D>> outlines = new ArrayList<ArrayList<Vector2D>>();
        for (int i = 0; i < setPoly.numPolygons(); i++) {
            outlines.addAll(HolePolygonUtil.outlines(setPoly.getPolygon(i)));
        }
        return outlines;
    }
    static void edges(ArrayList<Vector2D> edges, SetPolygon polygon)
    {
        for (int i = 0; i < polygon.numPolygons(); i++)
        {
            HolePolygonUtil.edges(edges, polygon.getPolygon(i));
        }
    }
}
