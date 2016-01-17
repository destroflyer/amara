/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.shapes.PolygonMath;

import java.util.ArrayList;
import amara.libraries.physics.shapes.Vector2D;

/**
 *
 * @author Philipp
 */
class MinkowskiUtil
{
    
    public static SetPolygon flip(SetPolygon set)
    {
        return scaleTranslate(set, Vector2D.Zero, -1);
    }
    public static SetPolygon add(SetPolygon a, SetPolygon b)
    {
        if (a.numPolygons() == 0 || b.numPolygons() == 0) return new SetPolygon();
        for (int i = 0; i < a.numPolygons(); i++)
        {
            if (a.getPolygon(i).numSimplePolys() == 0) return new SetPolygon(new HolePolygon());
        }
        for (int i = 0; i < b.numPolygons(); i++)
        {
            if (b.getPolygon(i).numSimplePolys() == 0) return new SetPolygon(new HolePolygon());
        }
        ArrayList<SetPolygon> list = new ArrayList<SetPolygon>();
        ArrayList<SetPolygon> list2 = new ArrayList<SetPolygon>();

        for (int i = 0; i < b.numPolygons(); i++)
        {
            Vector2D translation = b.getPolygon(i).getSimplePoly(0).getPoint(0);
            list2.add(scaleTranslate(a, translation, 1));
        }
        for (int i = 0; i < a.numPolygons(); i++)
        {
            Vector2D translation = a.getPolygon(i).getSimplePoly(0).getPoint(0);
            list2.add(scaleTranslate(b, translation, 1));
        }

        for (int o = 0; o < a.numPolygons(); o++)
        {
            HolePolygon polyA = a.getPolygon(o);
            for (int p = 0; p < polyA.numSimplePolys(); p++)
            {
                SimplePolygon simpleA = polyA.getSimplePoly(p);
                for (int i = 0; i < simpleA.numPoints(); i++)
                {
                    int j = (i + 1) % simpleA.numPoints();
                    for (int m = 0; m < b.numPolygons(); m++)
                    {
                        HolePolygon polyB = b.getPolygon(m);
                        for (int n = 0; n < polyB.numSimplePolys(); n++)
                        {
                            SimplePolygon simpleB = polyB.getSimplePoly(n);
                            for (int k = 0; k < simpleB.numPoints(); k++)
                            {
                                int l = (k + 1) % simpleB.numPoints();
                                ArrayList<Vector2D> points = new ArrayList<Vector2D>();

                                points.add(simpleA.getPoint(i).add(simpleB.getPoint(l)));
                                points.add(simpleA.getPoint(j).add(simpleB.getPoint(l)));
                                points.add(simpleA.getPoint(j).add(simpleB.getPoint(k)));
                                points.add(simpleA.getPoint(i).add(simpleB.getPoint(k)));

                                list.add(fromPoints(points));
                            }
                        }
                    }
                }
            }
        }

        SetPolygonUtil.preSortedUnion(list);
        list.addAll(list2);
        SetPolygonUtil.preSortedUnion(list);
        return list.get(0);
    }

    private static SetPolygon scaleTranslate(SetPolygon set, Vector2D translation, double scale)
    {
        SetPolygon result = new SetPolygon();
        for (int i = 0; i < set.numPolygons(); i++)
        {
            HolePolygon poly = set.getPolygon(i);
            result.add(scaleTranslate(poly, translation, scale));
        }
        
        for (int i = 0; i < set.numPolygons(); i++)
        {
            HolePolygon poly = set.getPolygon(i);
            for (int j = 0; j < set.numPolygons(); j++)
            {
                if(i == j) continue;
                HolePolygon holePoly = set.getPolygon(j);
                assert(!HolePolygonUtil.haveOverlappingAreas(holePoly, poly));
                assert(!HolePolygonUtil.haveTouchingEdge(holePoly, poly));
            }
        }
        return result;
    }
    private static HolePolygon scaleTranslate(HolePolygon holePoly, Vector2D translation, double scale)
    {
        HolePolygon result = new HolePolygon(scaleTranslate(holePoly.mainPoly(), translation, scale));
        for (int i = 0; i < holePoly.numHolePolys(); i++)
        {
            SimplePolygon simple = holePoly.getHolePoly(i);
            result.add(scaleTranslate(simple, translation, scale));
        }
        return result;
    }
    private static SimplePolygon scaleTranslate(SimplePolygon simple, Vector2D translation, double scale)
    {
        if(simple == null) return null;
        SimplePolygon result = new SimplePolygon();
        for (int i = 0; i < simple.numPoints(); i++)
        {
            Vector2D point = simple.getPoint(i);
            result.add(point.mult(scale).add(translation));
        }
        return result;
    }

    private static SetPolygon fromPoints(ArrayList<Vector2D> points)
    {
        SimplePolygon simple = new SimplePolygon(points);
        if (!simple.hasArea()) return new SetPolygon();
        if (simple.isHole()) simple.invert();
        return new SetPolygon(simple);
    }
}
