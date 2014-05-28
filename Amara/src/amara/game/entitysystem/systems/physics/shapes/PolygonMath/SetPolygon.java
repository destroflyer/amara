/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath;

import java.util.ArrayList;

/**
 *
 * @author Philipp
 */
class SetPolygon
{
    private ArrayList<HolePolygon> polys = new ArrayList<HolePolygon>();

    public SetPolygon()
    {
    }

    public SetPolygon(SetPolygon setPoly)
    {
        this(setPoly.polys);
    }
    public SetPolygon(HolePolygon holePoly)
    {
        polys.add(new HolePolygon(holePoly));
    }
    public SetPolygon(SimplePolygon simplePoly)
    {
        polys.add(new HolePolygon(simplePoly));
    }
    public SetPolygon(ArrayList<HolePolygon> holePolys)
    {
        for (HolePolygon holePoly : holePolys)
        {
            polys.add(new HolePolygon(holePoly));
        }
    }
    
    public HolePolygon getPolygon(int index)
    {
        return polys.get(index);
    }

    public int numPolygons()
    {
        return polys.size();
    }

    public void add(HolePolygon holePoly)
    {
        for(HolePolygon poly: polys)
        {
            assert(!HolePolygonUtil.haveOverlappingAreas(holePoly, poly));
            assert(!HolePolygonUtil.haveTouchingEdge(holePoly, poly));
        }
        polys.add(holePoly);
    }

    public void smooth()
    {
        for(HolePolygon poly: polys)
        {
            poly.smooth();
        }
        assert(isSmooth());
    }
    public boolean isSmooth()
    {
        for(HolePolygon poly: polys)
        {
            if (!poly.isSmooth()) return false;
        }
        return true;
    }
    public boolean hasLooseLines()
    {
        for(HolePolygon poly: polys)
        {
            if (poly.hasLooseLines()) return true;
        }
        return false;
    }
    public boolean isValid()
    {
        for (int i = 0; i < polys.size(); i++)
        {
            if(!polys.get(i).isValid()) return false;
            for (int j = i + 1; j < polys.size(); j++)
            {
                if(HolePolygonUtil.haveOverlappingAreas(polys.get(i), polys.get(j))) return false;
            }
        }
        return true;
    }

    public double signedArea()
    {
        double area = 0d;
        for(HolePolygon poly: polys)
        {
            area += poly.signedArea();
        }
        return area;
    }

    public boolean isInfinite()
    {
        for(HolePolygon poly: polys)
        {
            if (poly.isInfinite()) return true;
        }
        return false;
    }

    public Containment areaContains(Point2D point)
    {
        for(HolePolygon poly: polys)
        {
            Containment tmp = poly.areaContains(point);
            if (tmp != Containment.Outside) return tmp;
        }
        return Containment.Outside;
    }
    public boolean areaContainsFast(Point2D point)
    {
        for(HolePolygon poly: polys)
        {
            if (poly.areaContainsFast(point)) return true;
        }
        return false;
    }

    public boolean hasEdge(Point2D a, Point2D b)
    {
        for(HolePolygon poly: polys)
        {
            if (poly.hasEdge(a, b)) return true;
        }
        return false;
    }

    public boolean hasPoint(Point2D p)
    {
        for(HolePolygon poly: polys)
        {
            if (poly.hasPoint(p)) return true;
        }
        return false;
    }

    public Point2D minBorderDistance(Point2D p)
    {
        assert(areaContains(p) == Containment.Inside);
        assert(areaContains(p) != Containment.Outside);
        double squaredDistance = Double.POSITIVE_INFINITY;
        Point2D distance = Point2D.Zero;
        for(HolePolygon hole: polys)
        {
            for (int k = 0; k < hole.numSimplePolys(); k++)
            {
                SimplePolygon simple = hole.getSimplePoly(k);
                for (int i = 0; i < simple.numPoints(); i++)
                {
                    int j = (i + 1) % simple.numPoints();
                    Point2D a = simple.getPoint(i);
                    Point2D b = simple.getPoint(j);
                    if (0 < Point2DUtil.lineSide(p, a, b)) continue;
                    Point2D tmp = Point2DUtil.fromLineSegmentToPoint(a, b, p);
                    double squaredTmp = tmp.squaredLength();
                    if (squaredTmp < squaredDistance)
                    {
                        squaredDistance = squaredTmp;
                        distance = tmp;
                    }
                }
            }
        }
        assert(!distance.equals(Point2D.Zero));
        assert areaContains(p.sub(distance)) == Containment.Border;
        return distance.inverse();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof SetPolygon) return equals((SetPolygon)obj);
        return false;
    }
    public boolean equals(SetPolygon set)
    {
        if (set.polys.size() != polys.size()) return false;
        ArrayList<HolePolygon> remaining = new ArrayList<HolePolygon>(set.polys);
        for(HolePolygon poly: polys)
        {
            boolean found = false;
            for (int i = 0; i < remaining.size(); i++)
            {
                if (poly.equals(remaining.get(i)))
                {
                    found = true;
                    remaining.remove(i);
                }
            }
            if (!found)
            {
                assert(this != set);
                return false;
            }
        }
        assert(remaining.size() == 0);
        assert(hashCode() == set.hashCode());
        return true;
    }
    @Override
    public int hashCode()
    {
        int hash = 0;
        for(HolePolygon p: polys)
        {
            hash ^= p.hashCode();
        }
        return hash;
    }
    @Override
    public String toString()
    {
        String str = "Set[";
        if (polys.size() >= 1)
        {
            str += polys.get(0).toString();
            for (int i = 1; i < polys.size(); i++)
            {
                str += ", " + polys.get(i).toString();
            }
        }
        return str + "]";
    }
}
