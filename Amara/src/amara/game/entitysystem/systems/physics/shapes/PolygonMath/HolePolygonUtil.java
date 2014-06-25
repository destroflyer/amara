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
    public static ArrayList<Point2D> triangles(HolePolygon poly)
    {
        assert !poly.isInfinite();
        return SimplePolygonUtil.triangles(cutToSimple(poly));
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
        
        int innerIndex = 0;
        for (int i = 1; i < inner.numPoints(); i++)
        {
            if(inner.getPoint(i).getX() > inner.getPoint(innerIndex).getX()) innerIndex = i;
        }
        Point2D iP = inner.getPoint(innerIndex);
        
        double minIntersectionX = Double.NaN;
        int outerIndex = -1;
        for (int i = 0; i < outer.numPoints(); i++)
        {
            int j = (i + 1) % outer.numPoints();
            
            if(outer.getPoint(i).getY() == iP.getY())
            {
                if(iP.getX() < outer.getPoint(i).getX())
                {
                    if(!(minIntersectionX >= outer.getPoint(i).getX()))
                    {
                        if(outer.getPoint(j).getX() < outer.getPoint(i).getX()) outerIndex = i;
                        else outerIndex = j;
                        minIntersectionX = outer.getPoint(i).getX();
                    }
                }
            }
            if(outer.getPoint(j).getY() == iP.getY())
            {
                if(iP.getX() < outer.getPoint(j).getX())
                {
                    if(!(minIntersectionX >= outer.getPoint(j).getX()))
                    {
                        if(outer.getPoint(j).getX() < outer.getPoint(i).getX()) outerIndex = i;
                        else outerIndex = j;
                        minIntersectionX = outer.getPoint(j).getX();
                    }
                }
            }
            
            double intersectionX = Point2DUtil.lineSegmentAxisIntersectionX(outer.getPoint(i), outer.getPoint(j), iP.getY());
            if(iP.getX() < intersectionX)
            {
                if(!(minIntersectionX >= intersectionX))
                {
                    if(outer.getPoint(j).getX() < outer.getPoint(i).getX()) outerIndex = i;
                    else outerIndex = j;
                    minIntersectionX = intersectionX;
                }
            }
        }
        assert outerIndex != -1;
        assert iP.getX() <= outer.getPoint(outerIndex).getX(): iP.getX() + " - " + outer.getPoint(outerIndex).getX();
        
        Point2D tmp = new Point2D(minIntersectionX, iP.getY());
        if(tmp.withinEpsilon(outer.getPoint(outerIndex)))
        {
            
        }
        else if(tmp.withinEpsilon(outer.getPoint((outerIndex + 1) % outer.numPoints())))
        {
            outerIndex = (outerIndex + 1) % outer.numPoints();
        }
        else
        {
            SimplePolygon tri = new SimplePolygon();
            tri.add(iP);
            tri.add(tmp);
            tri.add(outer.getPoint(outerIndex));
            if(tri.isHole()) tri.invert();
            ArrayList<Integer> candidates = new ArrayList<Integer>();
            for (int i = 0; i < outer.numPoints(); i++)
            {
                int j = (i + 1) % outer.numPoints();
                int h = (i + 2) % outer.numPoints();
                
                if(iP.getX() <= outer.getPoint(j).getX())
                {
                    if(Point2DUtil.lineSide(outer.getPoint(j), outer.getPoint(i), outer.getPoint(h)) < 0)
                    {
                        if(tri.areaContains(outer.getPoint(j)) != Containment.Outside)
                        {
                            candidates.add(j);
                        }
                    }
                }
            }
            assert iP.getX() <= outer.getPoint(outerIndex).getX(): iP.getX() + " - " + outer.getPoint(outerIndex).getX();
            if(!candidates.isEmpty())
            {
                Point2D delta = outer.getPoint(outerIndex).sub(iP);
                double len = delta.squaredLength();
                for (int i = 0; i < candidates.size(); i++)
                {
                    delta = outer.getPoint(candidates.get(i)).sub(iP);
                    double tmpLen = delta.squaredLength();
                    if(tmpLen < len)
                    {
                        len = tmpLen;
                        outerIndex = candidates.get(i);
                    }
                }
            } else assert iP.getX() <= outer.getPoint(outerIndex).getX(): iP.getX() + " - " + outer.getPoint(outerIndex).getX();
        }
        
        assert iP.getX() <= outer.getPoint(outerIndex).getX(): iP.getX() + " - " + outer.getPoint(outerIndex).getX();
        
        assert !SimplePolygonUtil.outlineIntersectsSegment(outer, iP, outer.getPoint(outerIndex));
        assert !SimplePolygonUtil.outlineIntersectsSegment(inner, iP, outer.getPoint(outerIndex));
        
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
        assert Util.withinEpsilon(outer.signedArea() + inner.signedArea() - result.signedArea());
        
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

    static HashSet<Point2D> points(HolePolygon polygon)
    {
        HashSet<Point2D> points = new HashSet<Point2D>();
        for (int i = 0; i < polygon.numSimplePolys(); i++)
        {
            points.addAll(SimplePolygonUtil.points(polygon.getSimplePoly(i)));
        }
        return points;
    }

    static ArrayList<ArrayList<Point2D>> outlines(HolePolygon poly)
    {
        ArrayList<ArrayList<Point2D>> outlines = new ArrayList<ArrayList<Point2D>>();
        for (int i = 0; i < poly.numSimplePolys(); i++) {
            outlines.add(SimplePolygonUtil.outline(poly.getSimplePoly(i)));
        }
        return outlines;
    }
    
}
