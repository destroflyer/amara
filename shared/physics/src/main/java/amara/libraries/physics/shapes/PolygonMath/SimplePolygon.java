/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.shapes.PolygonMath;

import java.util.*;
import com.jme3.network.serializing.Serializable;
import amara.libraries.physics.shapes.*;

/**
 *
 * @author Philipp
 */
@Serializable
public class SimplePolygon
{
    private ArrayList<Vector2D> points = new ArrayList<Vector2D>();
    private double cachedArea = Double.NaN;

    public SimplePolygon() {
    }

    public SimplePolygon(Collection<Vector2D> points)
    {
        this.points.addAll(points);
    }
    public SimplePolygon(SimplePolygon simple)
    {
        this(simple.points);
    }
    
    
    public int numPoints()
    {
        return points.size();
    }
    public Vector2D getPoint(int index)
    {
        return points.get(index);
    }
    public void add(Vector2D point)
    {
        cachedArea = Double.NaN;
        points.add(point);
    }
    public void add(double x, double y)
    {
        add(new Vector2D(x, y));
    }
    public void insert(int index, Vector2D point)
    {
        cachedArea = Double.NaN;
        points.add(index, point);
    }
    public void removeAt(int index)
    {
        cachedArea = Double.NaN;
        points.remove(index);
    }
    public boolean hasPoint(Vector2D p)
    {
        return points.contains(p);
    }

    public SimplePolygon inverse()
    {
        SimplePolygon simple = new SimplePolygon(this);
        simple.invert();
        return simple;
    }
    public void invert()
    {
        cachedArea = -cachedArea;
        Util.reverse(points);
    }

    public boolean hasEdge(Vector2D a, Vector2D b)
    {
        for (int i = 0; i < points.size(); i++)
        {
            int j = (i + 1) % points.size();
            if (a.withinEpsilon(points.get(i)) && b.withinEpsilon(points.get(j))) return true;
        }
        return false;
    }

    public boolean isHole()
    {
        return signedArea() < 0;
    }
    public boolean isAreaPositive()
    {
        return signedArea() > 0;
    }
    public double signedArea()
    {
        if (Double.isNaN(cachedArea))
        {
            cachedArea = calcSignedArea();
            assert cachedArea * Vector2DUtil.kahanSumArea(points) >= 0;
        }
        return cachedArea;
    }
    private double calcSignedArea()
    {
        return Vector2DUtil.area(points);
    }
    
    public boolean onBorder(Vector2D point)
    {
        if (hasPoint(point)) return true;
        for (int i = 0; i < points.size(); i++)
        {
            int j = (i + 1) % points.size();
            if (point.between(points.get(i), points.get(j))) return true;
        }
        return false;
    }
    public boolean areaContainsFast(Vector2D point)
    {
        boolean inside = isHole();
        int i, j;
        for (i = points.size() - 1, j = 0; j < points.size(); i = j++)
        {
            if (((points.get(j).getY() > point.getY()) != (points.get(i).getY() > point.getY()))
                && (point.getX() < (points.get(i).getX() - points.get(j).getX()) * (point.getY() - points.get(j).getY()) / (points.get(i).getY() - points.get(j).getY()) + points.get(j).getX()))
            {
                inside = !inside;
            }
        }
        return inside;
    }
    public Containment areaContains(Vector2D point)
    {
        if (onBorder(point)) return Containment.Border;
        return areaContainsFast(point) ? Containment.Inside : Containment.Outside;
    }
    
    
    public boolean hasArea()
    {
        assert(Util.withinEpsilon(signedArea()) || points.size() >= 3);
        return !Util.withinEpsilon(signedArea());
    }
    
    public void insertAtTouchPoint(SimplePolygon simple)
    {
        assert SimplePolygonUtil.numCommonPoints(this, simple) == 1;
        assert(SimplePolygonUtil.haveCommonPoint(this, simple));
        assert !SimplePolygonUtil.outlinesIntersect(this, simple);
        assert (isHole() == simple.isHole()) || SimplePolygonUtil.haveOverlappingAreas(this, simple);
        double prevArea = signedArea();
        for (int i = 0; i < points.size(); i++)
        {
            for (int j = 0; j < simple.points.size(); j++)
            {
                if (points.get(i).withinEpsilon(simple.points.get(j)))
                {
                    insertPoly(simple, i, j);
                    assert(!hasRepetitions());
                    assert(Util.withinEpsilon(prevArea + simple.signedArea() - signedArea())): "" + prevArea + " / " + simple.signedArea() + " / " + signedArea();
                    return;
                }
            }
        }
        throw new Error("polys do not touch");
    }
    public void insertRange(int index, Collection<Vector2D> points)
    {
        this.points.addAll(index, points);
        cachedArea = Double.NaN;
    }
    public void insertPoly(SimplePolygon simple, int i, int j)
    {
        assert(!hasRepetitions());
        assert(!simple.hasRepetitions());
        SimplePolygon tmp = new SimplePolygon();
        for (int k = j; k < simple.numPoints(); k++)
        {
            tmp.add(simple.getPoint(k));
        }
        for (int k = 0; k < j; k++)
        {
            tmp.add(simple.getPoint(k));
        }
        assert(tmp.equals(simple)): "" + j + " / " + tmp.numPoints() + " / " + simple.numPoints();
        insertRange(i, tmp.points);
        assert(!hasRepetitions());
    }

    public void smooth()
    {
        double prevArea = signedArea();
        removeLooseLines();
        removeStraightAngles();
        assert(Util.withinEpsilon(prevArea - signedArea()));
        assert(isSmooth());
    }
    public boolean isSmooth()
    {
        return !hasStraightAngles() && !hasLooseLines();
    }
    public boolean hasLooseLines()
    {
        return firstLooseLineIndex() != -1;
    }
    public int firstLooseLineIndex()
    {
        for (int i = 0; i < points.size(); i++)
        {
            int j = (i + 1) % points.size();
            int k = (i + 2) % points.size();

            if (points.get(i).withinEpsilon(points.get(k))) return j;
            if (points.get(i).between(points.get(j), points.get(k))) return j;
            if (points.get(k).between(points.get(i), points.get(j))) return j;
        }
        return -1;
    }
    public boolean hasStraightAngles()
    {
        for (int i = 0; i < points.size(); i++)
        {
            int j = (i + 1) % points.size();
            int k = (i + 2) % points.size();
            if (points.get(j).between(points.get(i), points.get(k))) return true;
        }
        return false;
    }
    public void removeLooseLines()
    {
        insertTouchPoints(this);
        assert(!hasRepetitions());
        double area = signedArea();
        boolean changed = true;
        while (changed)
        {
            changed = false;
            if (points.size() <= 2)
            {
                points.clear();
                return;
            }
            for (int i = 0; i < points.size(); i++)
            {
                int j = (i + 2) % points.size();
                if (points.get(i).withinEpsilon(points.get(j)))
                {
                    ArrayList<Integer> rem = new ArrayList<Integer>();
                    rem.add(i);
                    rem.add((i + 1) % points.size());
                    Collections.sort(rem);
                    for (int k = rem.size() - 1; k >= 0; k--)
                    {
                        points.remove((int)rem.get(k));
                    }
                    changed = true;
                    break;
                }
            }
        }

        double area2 = signedArea();
        assert(Util.withinEpsilon(area - area2));
        assert(!hasRepetitions());
        assert(!hasLooseLines());
    }
    public void removeStraightAngles()
    {
        double prevArea = signedArea();
        for (int i = 0; i < points.size(); i++)
        {
            int j = (i + 1) % points.size();
            int k = (i + 2) % points.size();
            if (points.get(j).between(points.get(i), points.get(k)))
            {
                points.remove(j);
                i--;
            }
        }
        assert(!hasStraightAngles());
        assert(Util.withinEpsilon(prevArea - signedArea()));
    }
    public boolean hasDuplicates()
    {
        for (int i = 0; i + 1 < points.size(); i++)
        {
            for (int j = i + 1; j < points.size(); j++)
            {
                if (points.get(i).withinEpsilon(points.get(j))) return true;
            }
        }
        return false;
    }
    public int numDuplicates()
    {
        int count = 0;
        for (int i = 0; i + 1 < points.size(); i++)
        {
            for (int j = i + 1; j < points.size(); j++)
            {
                if (points.get(i).withinEpsilon(points.get(j))) count++;
            }
        }
        return count;
    }
    public boolean hasRepetitions()
    {
        for (int i = 0; i < points.size(); i++)
        {
            int j = (i + 1) % points.size();

            if (points.get(i).withinEpsilon(points.get(j))) return true;
        }
        return false;
    }
    
    public boolean areaContainsOutline(SimplePolygon b)
    {
        if (!b.hasArea()) return true;
        assert(b.numPoints() >= 2);
        assert(!SimplePolygonUtil.outlinesIntersect(this, b));
        SimplePolygon tmp = new SimplePolygon(b);
        tmp.insertTouchPoints(this);

        for (int i = 0; i < tmp.numPoints(); i++)
        {
            int j = (i + 1) % tmp.numPoints();

            if (areaContains(Vector2DUtil.avg(tmp.getPoint(i), tmp.getPoint(j))) == Containment.Outside) return false;
            if (areaContains(Vector2DUtil.avg(tmp.getPoint(i), tmp.getPoint(j))) == Containment.Inside)
            {
                for (int k = i + 1; k < tmp.numPoints(); k++)
                {
                    int l = (k + 1) % tmp.numPoints();

                    assert(areaContains(Vector2DUtil.avg(tmp.getPoint(k), tmp.getPoint(l))) != Containment.Outside);
                }
                return true;
            }
        }
        //outlines are identical
        return true;
    }

    public void insertTouchPoints(SimplePolygon poly)
    {
        assert(!hasRepetitions());
        HashMap<Integer, HashSet<Vector2D>> touchers = new HashMap<Integer, HashSet<Vector2D>>();
        for (int k = 0; k < poly.numPoints(); k++)
        {
            Vector2D p = poly.getPoint(k);
            for (int i = 0; i < numPoints(); i++)
            {
                int j = (i + 1) % numPoints();
                if (p.between(getPoint(i), getPoint(j)))
                {
                    if (!touchers.containsKey(i)) touchers.put(i, new HashSet<Vector2D>());
                    touchers.get(i).add(p);
                }
            }
        }

        for (int i = numPoints() - 1; i >= 0; i--)
        {
            if (touchers.containsKey(i))
            {
                ArrayList<Vector2D> list = new ArrayList<Vector2D>(touchers.get(i));
                Collections.sort(list, new PointDistanceComparator(getPoint(i)));
                for (int j = list.size() - 1; j >= 0; j--)
                {
                    insert(i + 1, list.get(j));
                }
            }
        }
        assert(!hasRepetitions());
    }

    public boolean isSelfTouching()
    {
        for (int i = 0; i + 1 < points.size(); i++)
        {
            for (int j = i + 1; j < points.size(); j++)
            {
                if(points.get(i).withinEpsilon(points.get(j))) return true;
            }
        }
        return false;
    }
    
    public boolean isSelfIntersecting()
    {
        for (int i = 0; i + 1 < points.size(); i++)
        {
            int j = i + 1;
            
            for (int k = j; k < points.size(); k++)
            {
                int l = (k + 1) % points.size();
                if(!Vector2DUtil.segmentAaBbCheck(points.get(i), points.get(j), points.get(k), points.get(l))) continue;
                if(Vector2DUtil.lineSegmentIntersectionPointWithoutCorners(points.get(i), points.get(j), points.get(k), points.get(l)) != null) return true;
            }
        }
//        if(SimplePolygonUtil.outlinesIntersect(this, this)) return true;
//        SimplePolygon simple = new SimplePolygon(points);
//        simple.smooth();
//        SimplePolygonUtil.addIntersectionPoints(simple, simple);
//        simple.insertTouchPoints(this);
//        
//        Point2D a, b, c, d;
//        for (int i = 0; i < simple.numPoints(); i++)
//        {
//            a = simple.getPoint(i);
//            int j = (i + 1) % simple.numPoints();
//            
//            for (int l = 0; l < simple.numPoints(); l++)
//            {
//                if(simple.getPoint(i).withinEpsilon(simple.getPoint(l))) continue;
//                int k = (l + simple.numPoints() - 1) % simple.numPoints();
//                d = simple.getPoint(l);
//                
//                int iterations = simple.numPoints();
//                while(simple.getPoint(j).withinEpsilon(simple.getPoint(k)))
//                {
//                    if(--iterations == 0) break;
//                    
//                    j = (j + 1) % simple.numPoints();
//                    k = (k + simple.numPoints() - 1) % simple.numPoints();
//                }
//                if(iterations == 0) continue;
//                
//                b = simple.getPoint(j);
//                c = simple.getPoint(k);
//                
//                if(a.withinEpsilon(b)) continue;
//                if(a.withinEpsilon(c)) continue;
//                if(a.withinEpsilon(d)) continue;
//                if(b.withinEpsilon(c)) continue;
//                if(b.withinEpsilon(d)) continue;
//                if(c.withinEpsilon(d)) continue;
//                
//                if(Point2DUtil.lineSegmentIntersectionPointWithoutCorners(a, b, c, d) != null)
//                {
//                    return true;
//                }
//            }
//        }
        return false;
    }
    
    public boolean isValid()
    {
        for (Vector2D p : points)
        {
            if(Double.isInfinite(p.getX())) return false;
            if(Double.isInfinite(p.getY())) return false;
            if(Double.isNaN(p.getX())) return false;
            if(Double.isNaN(p.getY())) return false;
        }
        SimplePolygon test = new SimplePolygon(points);
        test.insertTouchPoints(test);
        if(test.isSelfTouching()) return false;
        if(test.hasDuplicates())
        {
            return false;
        }
        if(isSelfIntersecting()) return false;
        if(hasRepetitions()) return false;
        SimplePolygonUtil.triangles(isHole()? inverse(): this);
        return true;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof SimplePolygon)
        {
            return equals((SimplePolygon)obj);
        }
        return false;
    }
    public boolean equals(SimplePolygon poly)
    {
        if (poly.points.size() != points.size()) return false;
        if(!Util.withinEpsilon(signedArea() - poly.signedArea())) return false;
        for (int i = 0; i < points.size(); i++)
        {
            boolean equal = true;
            for (int j = 0; j < points.size(); j++)
            {
                int k = (i + j) % points.size();
                if (!points.get(j).equals(poly.points.get(k)))
                {
                    equal = false;
                    break;
                }
            }
            if (equal)
            {
                assert(hashCode() == poly.hashCode());
                return true;
            }
        }
        assert(this != poly);
        return false;
    }
    @Override
    public int hashCode()
    {
        int hash = 0;
        for(Vector2D p: points)
        {
            hash ^= p.hashCode();
        }
        return hash;
    }
    @Override
    public String toString()
    {
        String str = "Simple[";
        if (points.size() >= 1)
        {
            str += points.get(0).toString();
            for (int i = 1; i < points.size(); i++)
            {
                str += ", " + points.get(i).toString();
            }
        }
        return str + "]";
    }
}
