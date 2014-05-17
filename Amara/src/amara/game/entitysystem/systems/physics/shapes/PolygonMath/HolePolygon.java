/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath;

import amara.game.entitysystem.systems.physics.shapes.PolygonMath.Public.Point2D;
import java.util.*;

/**
 *
 * @author Philipp
 */
public class HolePolygon
{
    private ArrayList<SimplePolygon> holes = new ArrayList<SimplePolygon>();
    private SimplePolygon main;

    public HolePolygon()
    {
        this((SimplePolygon)null);
    }

    public HolePolygon(SimplePolygon main)
    {
        if(main == null) this.main = null;
        else this.main = new SimplePolygon(main);
    }
    public HolePolygon(SimplePolygon main, ArrayList<SimplePolygon> holes)
    {
        this(main);
        for (int i = 0; i < holes.size(); i++) {
            this.holes.add(new SimplePolygon(holes.get(i)));
        }
    }
    public HolePolygon(HolePolygon poly)
    {
        this(poly.main, poly.holes);
    }

    public boolean isInfinite()
    {
        return main == null;
    }

    public SimplePolygon mainPoly()
    {
        return main;
    }
    
    public int numHolePolys()
    {
        return holes.size();
    }
    public SimplePolygon getHolePoly(int index)
    {
        return holes.get(index);
    }
    public int numSimplePolys()
    {
        return main == null? holes.size(): holes.size() + 1;
    }
    public SimplePolygon getSimplePoly(int index)
    {
        if(index == holes.size() && main != null)
        {
            return main;
        }
        return holes.get(index);
    }

    public void add(SimplePolygon hole)
    {
        assert(main.areaContainsOutline(hole));
        assert(hole.isHole());
        for (int i = 0; i < holes.size(); i++) {
            assert(!SimplePolygonUtil.haveTouchingEdge(hole, holes.get(i)));
            assert(!SimplePolygonUtil.outlinesIntersect(hole, holes.get(i)));
        }
        holes.add(hole);
    }
    private boolean mainContainsOutline(SimplePolygon simple)
    {
        if(main == null) return true;
        return main.areaContainsOutline(simple);
    }

    public double signedArea()
    {
        double area = 0;
        for (int i = 0; i < numSimplePolys(); i++)
        {
            area += getSimplePoly(i).signedArea();
        }
        return area;
    }

    public Containment areaContains(Point2D point)
    {
        for (int i = 0; i < numSimplePolys(); i++)
        {
            Containment tmp = getSimplePoly(i).areaContains(point);
            if (tmp != Containment.Inside) return tmp;
        }
        return Containment.Inside;
    }
    public boolean areaContainsFast(Point2D point)
    {
        for (int i = 0; i < numSimplePolys(); i++)
        {
            if (!getSimplePoly(i).areaContainsFast(point)) return false;
        }
        return true;
    }

    public void smooth()
    {
        if(main != null) main.smooth();
        for (int i = holes.size() - 1; i >= 0; i--)
        {
            holes.get(i).smooth();
        }
        assert(isSmooth());
    }
    public boolean isSmooth()
    {
        for (int i = 0; i < numSimplePolys(); i++)
        {
            if (!getSimplePoly(i).isSmooth()) return false;
        }
        return true;
    }
    public boolean hasLooseLines()
    {
        for (int i = 0; i < numSimplePolys(); i++)
        {
            if (getSimplePoly(i).hasLooseLines()) return true;
        }
        return false;
    }

    public boolean hasEdge(Point2D a, Point2D b)
    {
        for (int i = 0; i < numSimplePolys(); i++)
        {
            if (getSimplePoly(i).hasEdge(a, b)) return true;
        }
        return false;
    }

    public boolean hasPoint(Point2D p)
    {
        for (int i = 0; i < numSimplePolys(); i++)
        {
            if (getSimplePoly(i).hasPoint(p)) return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof HolePolygon)
        {
            return equals((HolePolygon)obj);
        }
        return false;
    }
    public boolean equals(HolePolygon poly)
    {
        if(main == null)
        {
            if(poly.main != null) return false;
        }
        else if(poly.main == null) return false;
        else if (!main.equals(poly.main)) return false;
        if (poly.holes.size() != holes.size()) return false;
        ArrayList<SimplePolygon> remaining = new ArrayList<SimplePolygon>(poly.holes);
        for (int i = 0; i < holes.size(); i++)
        {
            boolean found = false;
            for (int j = 0; j < remaining.size(); j++)
            {
                if (holes.get(i).equals(remaining.get(j)))
                {
                    found = true;
                    remaining.remove(j);
                }
            }
            if (!found)
            {
                assert(this != poly);
                return false;
            }
        }
        assert(remaining.size() == 0);
        assert(hashCode() == poly.hashCode());
        return true;

        //for (int i = 0; i < holes.size(); i++)
        //{
        //    var equal = true;
        //    for (int j = 0; j < holes.size(); j++)
        //    {
        //        var k = (i + j) % holes.size();
        //        if (!holes[j].equals(poly.holes[k]))
        //        {
        //            equal = false;
        //            break;
        //        }
        //    }
        //    if (equal) return true;
        //}
        //Debug.Assert(this != poly);
        //return false;
    }
    @Override
    public int hashCode()
    {
        int hash = main == null? 0: main.hashCode();
        for (int i = 0; i < holes.size(); i++)
        {
            hash ^= holes.get(i).hashCode();
        }
        return hash;
    }
    @Override
    public String toString()
    {
        String str = "Poly[";
        str += main == null? "Global": main.toString();
        for (int i = 0; i < holes.size(); i++)
        {
            str += ", " + holes.get(i).toString();
        }
        return str + "]";
    }
}
