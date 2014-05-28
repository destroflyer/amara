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
class HolePolygonUtil
{
    
    public static SimplePolygon cutPolygon(HolePolygon poly)
    {
        if(poly.isInfinite()) throw new Error("can't convert infinite poly to cutpolygon");
        ArrayList<SimplePolygon> remaining = new ArrayList<SimplePolygon>();
        for (int i = 0; i < poly.numHolePolys(); i++)
        {
            remaining.add(poly.getHolePoly(i));
        }
        SimplePolygon result = new SimplePolygon(poly.mainPoly());
        for (int i = 0; i < remaining.size(); i++)
        {
            assert Util.withinEpsilon(result.signedArea() + areaHelper(remaining.subList(i, remaining.size())) - poly.signedArea());
            SimplePolygon hole = remaining.get(i);
            assert Util.withinEpsilon(areaHelper(remaining.subList(i, remaining.size())) - hole.signedArea() - areaHelper(remaining.subList(i + 1, remaining.size())));
            if(SimplePolygonUtil.haveCommonPoint(result, hole))
            {
                result.insertAtTouchPoint(hole);
                assert Util.withinEpsilon(result.signedArea() + areaHelper(remaining.subList(i + 1, remaining.size())) - poly.signedArea());
                continue;
            }
            boolean cutFound = false;
            for (int j = 0; j < result.numPoints(); j++)
            {
                int cut = tryFindCut(result, j, remaining, i);
                if(cut < 0) continue;
                cutFound = true;
                SimplePolygon tmp = new SimplePolygon();
                tmp.add(result.getPoint(j));
                for (int k = 0; k < hole.numPoints(); k++)
                {
                    tmp.add(hole.getPoint((cut + k) % hole.numPoints()));
                }
                tmp.add(hole.getPoint(cut));
                assert Util.withinEpsilon(hole.signedArea() - tmp.signedArea());
                assert SimplePolygonUtil.haveCommonPoint(result, tmp);
                assert Util.withinEpsilon(result.signedArea() + areaHelper(remaining.subList(i, remaining.size())) - poly.signedArea());
                assert Util.withinEpsilon(areaHelper(remaining.subList(i + 1, remaining.size())) + hole.signedArea() - areaHelper(remaining.subList(i, remaining.size())));
                double prev = result.signedArea();
                result.insertAtTouchPoint(tmp);
                assert Util.withinEpsilon(areaHelper(remaining.subList(i, remaining.size())) - hole.signedArea() - areaHelper(remaining.subList(i + 1, remaining.size())));
                assert Util.withinEpsilon(prev + tmp.signedArea() - result.signedArea());
                assert Util.withinEpsilon(prev + areaHelper(remaining.subList(i, remaining.size())) - poly.signedArea());
                assert Util.withinEpsilon(prev + tmp.signedArea() + areaHelper(remaining.subList(i + 1, remaining.size())) - poly.signedArea());
                //result.insertRange(j, tmp);
                assert Util.withinEpsilon(result.signedArea() + areaHelper(remaining.subList(i + 1, remaining.size())) - poly.signedArea()): "" + poly.signedArea() + " / " + result.signedArea() + " / " + tmp.signedArea() + " / " + prev;
                break;
            }
            assert cutFound;
            assert Util.withinEpsilon(result.signedArea() + areaHelper(remaining.subList(i + 1, remaining.size())) - poly.signedArea());
            assert i + 1 < remaining.size() || Util.withinEpsilon(result.signedArea() - poly.signedArea());
        }
        assert Util.withinEpsilon(result.signedArea() - poly.signedArea()): "" + poly.signedArea() + " / " + result.signedArea();
        assert !SimplePolygonUtil.outlinesIntersect(result, result);
        assert result.isValid();
        return result;
    }
    private static double areaHelper(List<SimplePolygon> polys)
    {
        double area = 0;
        for (SimplePolygon simple : polys) {
            area += simple.signedArea();
        }
        return area;
    }
    private static int tryFindCut(SimplePolygon poly, int from, ArrayList<SimplePolygon> holes, int currentHole)
    {
        Point2D a = poly.getPoint(from);
        for (int i = 0; i < holes.get(currentHole).numPoints(); i++)
        {
            Point2D b = holes.get(currentHole).getPoint(i);
            
            boolean isCutValid = SimplePolygonUtil.isValidCut(poly, a, b);
            for (int j = currentHole; isCutValid && j < holes.size(); j++)
            {
                isCutValid = isCutValid && SimplePolygonUtil.isValidCut(holes.get(currentHole), a, b);
            }
            if(isCutValid) return i;
        }
        return -1;
    }

    public static boolean haveTouchingEdge(HolePolygon a, HolePolygon b)
    {
        for (int k = 0; k < a.numSimplePolys(); k++)
        {
            SimplePolygon simple = a.getSimplePoly(k);
            for (int i = 0; i < simple.numPoints(); i++)
            {
                int j = (i + 1) % simple.numPoints();
                if (b.hasEdge(simple.getPoint(j), simple.getPoint(i))) return true;
            }
        }
        return false;
    }
    public static boolean haveCommonPoint(HolePolygon a, HolePolygon b)
    {
        for (int k = 0; k < a.numSimplePolys(); k++)
        {
            SimplePolygon simple = a.getSimplePoly(k);
            for (int i = 0; i < simple.numPoints(); i++)
            {
                if (b.hasPoint(simple.getPoint(i))) return true;
            }
        }
        return false;
    }
    public static boolean areTouching(HolePolygon a, HolePolygon b)
    {
        HolePolygon tmpA = new HolePolygon(a);
        HolePolygon tmpB = new HolePolygon(b);
        insertCommonOutlinePoints(tmpA, tmpB);
        return haveCommonPoint(tmpA, tmpB);
    }
    public static void insertCommonOutlinePoints(HolePolygon a, HolePolygon b)
    {
        assert(!a.hasLooseLines());
        assert(!b.hasLooseLines());

        for (int i = 0; i < a.numSimplePolys(); i++)
        {
            SimplePolygon A = a.getSimplePoly(i);
            for (int j = 0; j < b.numSimplePolys(); j++)
            {
                SimplePolygon B = b.getSimplePoly(j);
                SimplePolygonUtil.insertCommonOutlinePoints(A, B);
            }
        }
        assert(!a.hasLooseLines());
        assert(!b.hasLooseLines());
    }

    public static boolean haveOverlappingAreas(HolePolygon a, HolePolygon b)
    {
        if(a.isInfinite() && b.isInfinite()) return true;
        if (!haveMainPolysOverlappingAreas(a.mainPoly(), b.mainPoly())) return false;
        if (mainContainsOutline(a.mainPoly(), b.mainPoly()))
        {
            for (int i = 0; i < a.numHolePolys(); i++)
            {
                if (!a.getHolePoly(i).areaContainsOutline(b.mainPoly())) return false;
            }
        }
        else if (mainContainsOutline(b.mainPoly(), a.mainPoly()))
        {
            for (int i = 0; i < b.numHolePolys(); i++)
            {
                if (!b.getHolePoly(i).areaContainsOutline(a.mainPoly())) return false;
            }
        }
        return true;
    }
    private static boolean haveMainPolysOverlappingAreas(SimplePolygon a, SimplePolygon b)
    {
        if(a == null || b == null) return true;
        return SimplePolygonUtil.haveOverlappingAreas(a, b);
    }
    private static boolean mainContainsOutline(SimplePolygon a, SimplePolygon b)
    {
        if(a == null) return true;
        if(b == null) return false;
        return a.areaContainsOutline(b);
    }

    public static boolean outlinesIntersect(HolePolygon a, HolePolygon b)
    {
        for (int i = 0; i < a.numSimplePolys(); i++)
        {
            for (int j = 0; j < b.numSimplePolys(); j++)
            {
                if (SimplePolygonUtil.outlinesIntersect(a.getSimplePoly(i), b.getSimplePoly(j))) return true;
            }
        }
        return false;
    }

    public static void write(ByteBuffer buffer, HolePolygon poly)
    {
        assert poly.isValid();
        assert poly.isInfinite() || poly.mainPoly().isAreaPositive();
        SimplePolygonUtil.write(buffer, poly.mainPoly());
        buffer.writeInt(poly.numHolePolys());
        for (int i = 0; i < poly.numHolePolys(); i++)
        {
            SimplePolygonUtil.write(buffer, poly.getHolePoly(i));
        }
    }

    public static HolePolygon read(ByteBuffer buffer)
    {
        HolePolygon hole = new HolePolygon(SimplePolygonUtil.read(buffer));
        int num = buffer.readInt();
        for (int i = 0; i < num; i++)
        {
            hole.add(SimplePolygonUtil.read(buffer));
        }
        assert hole.isValid();
        return hole;
    }
    
}
