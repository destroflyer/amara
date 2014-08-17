/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath;

import amara.game.entitysystem.systems.physics.shapes.Transform2D;
import amara.game.entitysystem.systems.physics.shapes.Vector2D;
import amara.game.entitysystem.systems.physics.shapes.Vector2DUtil;
import java.util.*;

/**
 *
 * @author Philipp
 */
class HolePolygonUtil
{
    public static ArrayList<Vector2D> triangles(HolePolygon poly)
    {
        assert !poly.isInfinite();
        return SimplePolygonUtil.triangles(cutToSimple(poly));
    }
    
    private static int rightmostIndex(SimplePolygon simple)
    {
        int index = 0;
        for (int i = 1; i < simple.numPoints(); i++)
        {
            if(simple.getPoint(i).getX() > simple.getPoint(index).getX()) index = i;
        }
        return index;
    }
    
    private static int leftmostRayIntersectionPreIndex(Vector2D raySource, SimplePolygon simple)
    {
        double minIntersectionX = Double.NaN;
        int index = -1;
        for (int i = 0; i < simple.numPoints(); i++)
        {
            int j = (i + 1) % simple.numPoints();
            
            double intersectionX = rayIntersectionHelper(raySource, simple.getPoint(i), simple.getPoint(j));
            if(raySource.getX() <= intersectionX)
            {
                if(!(minIntersectionX <= intersectionX))
                {
                    index = i;
                    minIntersectionX = intersectionX;
                }
            }
        }
        assert index != -1;
        return index;
    }
    private static double rayIntersectionHelper(Vector2D raySource, Vector2D a, Vector2D b)
    {
        if(Util.withinEpsilon(a.getY() - raySource.getY())) return a.getX();
        return Vector2DUtil.lineSegmentAxisIntersectionX(a, b, raySource.getY());
    }
    
    private static int closestCandidateIndex(SimplePolygon outer, Vector2D iP, int outerIndex, Vector2D intersection)
    {
        int next = (outerIndex + 1) % outer.numPoints();
        assert !intersection.withinEpsilon(outer.getPoint(next));
        if(outer.getPoint(outerIndex).getX() < outer.getPoint(next).getX())
        {
            outerIndex = next;
        }

        SimplePolygon tri = new SimplePolygon();
        tri.add(iP);
        tri.add(outer.getPoint(outerIndex));
        tri.add(intersection);
        if(tri.isHole()) tri.invert();

        double len = outer.getPoint(outerIndex).sub(iP).squaredLength();
        for (int i = 0; i < outer.numPoints(); i++)
        {
            int j = (i + 1) % outer.numPoints();
            int h = (i + 2) % outer.numPoints();

            if(iP.getX() <= outer.getPoint(j).getX())
            {
                if(Vector2DUtil.lineSide(outer.getPoint(j), outer.getPoint(i), outer.getPoint(h)) <= 0)
                {
                    SimplePolygon t = new SimplePolygon();
                    t.add(outer.getPoint(i));
                    t.add(outer.getPoint(j));
                    t.add(outer.getPoint(h));
                    assert !t.isAreaPositive();
                    if(tri.areaContains(outer.getPoint(j)) != Containment.Outside)
                    {
                        double tmpLen = outer.getPoint(j).sub(iP).squaredLength();
                        if(tmpLen < len)
                        {
                            len = tmpLen;
                            outerIndex = j;
                        }
                    }
                }
                else
                {
                    SimplePolygon t = new SimplePolygon();
                    t.add(outer.getPoint(i));
                    t.add(outer.getPoint(j));
                    t.add(outer.getPoint(h));
                    assert !t.isHole();
                }
            }
        }
        assert iP.getX() <= outer.getPoint(outerIndex).getX(): iP.getX() + " - " + outer.getPoint(outerIndex).getX();

        assert !SimplePolygonUtil.outlineIntersectsSegment(outer, iP, outer.getPoint(outerIndex));
        return outerIndex;
    }
    
    private static SimplePolygon mergeHelper(SimplePolygon outer, int outerIndex, SimplePolygon inner, int innerIndex)
    {
        SimplePolygon poly = new SimplePolygon();
        poly.add(outer.getPoint(outerIndex));
        for (int k = 0; k < inner.numPoints(); k++)
        {
            poly.add(inner.getPoint((innerIndex + k) % inner.numPoints()));
        }
        poly.add(inner.getPoint(innerIndex));
        
        assert Util.withinEpsilon(poly.signedArea() - inner.signedArea());
        
        SimplePolygon result = new SimplePolygon(outer);
        result.insertPoly(poly, outerIndex, 0);
        return result;
    }
    
    public static SimplePolygon mergePolys(SimplePolygon outer, SimplePolygon inner)
    {
        if(SimplePolygonUtil.haveCommonPoint(outer, inner))
        {
            SimplePolygon result = new SimplePolygon(outer);
            result.insertAtTouchPoint(inner);
            return result;
        }
        assert outer.isAreaPositive();
        assert inner.isHole();
        assert outer.signedArea() + inner.signedArea() > 0;
        assert !SimplePolygonUtil.haveCommonPoint(outer, inner);
        assert !SimplePolygonUtil.outlinesIntersect(outer, inner);
        assert !SimplePolygonUtil.outlinesIntersect(outer, inner);
        
        int innerIndex = rightmostIndex(inner);
        Vector2D iP = inner.getPoint(innerIndex);
        
        int outerIndex = leftmostRayIntersectionPreIndex(iP, outer);
        Vector2D oP = outer.getPoint(outerIndex);
        double minIntersectionX = rayIntersectionHelper(iP, oP, outer.getPoint((outerIndex + 1) % outer.numPoints()));
        
        assert outerIndex != -1;
        assert iP.getX() <= oP.getX(): iP.getX() + " - " + oP.getX();
        
        Vector2D tmp = new Vector2D(minIntersectionX, iP.getY());
        assert !SimplePolygonUtil.outlineIntersectsSegment(outer, iP, tmp);
        if(!tmp.withinEpsilon(oP))
        {
            outerIndex = closestCandidateIndex(outer, iP, outerIndex, tmp);
            oP = outer.getPoint(outerIndex);
        
            assert iP.getX() <= oP.getX(): iP.getX() + " - " + oP.getX();

            assert !SimplePolygonUtil.outlineIntersectsSegment(outer, iP, oP);
            assert !SimplePolygonUtil.outlineIntersectsSegment(inner, iP, oP);
        }
        assert !SimplePolygonUtil.outlineIntersectsSegment(outer, iP, oP);
        assert !SimplePolygonUtil.outlineIntersectsSegment(inner, iP, oP);
        
        SimplePolygon result = mergeHelper(outer, outerIndex, inner, innerIndex);
        assert Util.withinEpsilon(outer.signedArea() + inner.signedArea() - result.signedArea());
        
        try
        {
            SimplePolygonUtil.triangles(outer);
        }
        catch(IndexOutOfBoundsException e)
        {
            assert false;
        }
        try
        {
            SimplePolygonUtil.triangles(inner.inverse());
        }
        catch(IndexOutOfBoundsException e)
        {
            assert false;
        }
        try
        {
            SimplePolygonUtil.triangles(result);
        }
        catch(IndexOutOfBoundsException e)
        {
            assert false;
        }
        
        return result;
    }
    public static SimplePolygon cutToSimple(HolePolygon poly)
    {
        assert !poly.isInfinite();
        SimplePolygon result = poly.mainPoly();
        
        ArrayList<SimplePolygon> holes = new ArrayList<SimplePolygon>();
        for (int i = 0; i < poly.numHolePolys(); i++)
        {
            holes.add(poly.getHolePoly(i));
        }
        maxXSort(holes);
        for (int i = 0; i < holes.size(); i++)
        {
            result = mergePolys(result, holes.get(i));
        }
        
        return result;
    }
    private static void maxXSort(ArrayList<SimplePolygon> simples)
    {
        ArrayList<Double> keys = new ArrayList<Double>();
        for (int i = 0; i < simples.size(); i++)
        {
            keys.add(-maxX(simples.get(i)));
        }
        Util.keySort(simples, keys);
    }
    private static double maxX(SimplePolygon simple)
    {
        double maxX = simple.getPoint(0).getX();
        for (int i = 1; i < simple.numPoints(); i++)
        {
            if(maxX < simple.getPoint(i).getX()) maxX = simple.getPoint(i).getX();
        }
        return maxX;
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
        for (int i = 0; i < a.numSimplePolys(); i++)
        {
            SimplePolygon A = a.getSimplePoly(i);
            for (int j = 0; j < b.numSimplePolys(); j++)
            {
                SimplePolygon B = b.getSimplePoly(j);
                SimplePolygonUtil.insertCommonOutlinePoints(A, B);
            }
        }
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
    
    static HolePolygon transform(HolePolygon hole, Transform2D t)
    {
        HolePolygon result = new HolePolygon(SimplePolygonUtil.transform(hole.mainPoly(), t));
        for (int i = 0; i < hole.numHolePolys(); i++)
        {
            result.add(SimplePolygonUtil.transform(hole.getHolePoly(i), t));
        }
        return result;
    }

    static HashSet<Vector2D> points(HolePolygon polygon)
    {
        HashSet<Vector2D> points = new HashSet<Vector2D>();
        for (int i = 0; i < polygon.numSimplePolys(); i++)
        {
            points.addAll(SimplePolygonUtil.points(polygon.getSimplePoly(i)));
        }
        return points;
    }

    static ArrayList<ArrayList<Vector2D>> outlines(HolePolygon poly)
    {
        ArrayList<ArrayList<Vector2D>> outlines = new ArrayList<ArrayList<Vector2D>>();
        for (int i = 0; i < poly.numSimplePolys(); i++) {
            outlines.add(SimplePolygonUtil.outline(poly.getSimplePoly(i)));
        }
        return outlines;
    }
    
    static void edges(ArrayList<Vector2D> edges, HolePolygon polygon)
    {
        for (int i = 0; i < polygon.numSimplePolys(); i++)
        {
            SimplePolygonUtil.edges(edges, polygon.getSimplePoly(i));
        }
    }
}
