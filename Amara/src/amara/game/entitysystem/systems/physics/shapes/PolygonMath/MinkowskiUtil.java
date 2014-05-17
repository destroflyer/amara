/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath;

import amara.game.entitysystem.systems.physics.shapes.PolygonMath.Public.*;
import java.util.ArrayList;

/**
 *
 * @author Philipp
 */
public class MinkowskiUtil
{
    
    public static SetPolygon flip(SetPolygon set)
    {
        return scaleTranslate(set, Point2D.Zero, -1);
    }
    public static SetPolygon add(SetPolygon a, SetPolygon b)
    {
        SetPolygon result = new SetPolygon();
        if (a.numPolygons() == 0 || b.numPolygons() == 0) return result;

        for (int i = 0; i < a.numPolygons(); i++)
        {
            HolePolygon polyA = a.getPolygon(i);
            if (b.getPolygon(0).numSimplePolys() == 0) return new SetPolygon(new HolePolygon());
            SimplePolygon simple = b.getPolygon(0).getSimplePoly(0);
            Point2D translation = simple.getPoint(0);
            result = union(result, scaleTranslate(new SetPolygon(polyA), translation, 1));
        }
        for (int i = 0; i < b.numPolygons(); i++)
        {
            HolePolygon polyB = b.getPolygon(i);
            if (a.getPolygon(0).numSimplePolys() == 0) return new SetPolygon(new HolePolygon());
            SimplePolygon simple = a.getPolygon(0).getSimplePoly(0);
            Point2D translation = simple.getPoint(0);
            result = union(result, scaleTranslate(new SetPolygon(polyB), translation, 1));
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
                                ArrayList<Point2D> points = new ArrayList<Point2D>();

                                points.add(simpleA.getPoint(i).add(simpleB.getPoint(l)));
                                points.add(simpleA.getPoint(j).add(simpleB.getPoint(l)));
                                points.add(simpleA.getPoint(j).add(simpleB.getPoint(k)));
                                points.add(simpleA.getPoint(i).add(simpleB.getPoint(k)));

                                result = union(result, fromPoints(points));
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    private static SetPolygon union(SetPolygon a, SetPolygon b)
    {
        return SetPolygonUtil.union(a, b);
    }

    private static SetPolygon scaleTranslate(SetPolygon set, Point2D translation, double scale)
    {
        SetPolygon result = new SetPolygon();
        for (int i = 0; i < set.numPolygons(); i++)
        {
            HolePolygon poly = set.getPolygon(i);
            result.add(scaleTranslate(poly, translation, scale));
        }
        return result;
    }
    private static HolePolygon scaleTranslate(HolePolygon holePoly, Point2D translation, double scale)
    {
        HolePolygon result = new HolePolygon(scaleTranslate(holePoly.mainPoly(), translation, scale));
        for (int i = 0; i < holePoly.numHolePolys(); i++)
        {
            SimplePolygon simple = holePoly.getHolePoly(i);
            result.add(scaleTranslate(simple, translation, scale));
        }
        return result;
    }
    private static SimplePolygon scaleTranslate(SimplePolygon simple, Point2D translation, double scale)
    {
        if(simple == null) return null;
        SimplePolygon result = new SimplePolygon();
        for (int i = 0; i < simple.numPoints(); i++)
        {
            Point2D point = simple.getPoint(i);
            result.add(point.mult(scale).add(translation));
        }
        return result;
    }

    private static SetPolygon fromPoints(ArrayList<Point2D> points)
    {
        SimplePolygon simple = new SimplePolygon(points);
        if (!simple.hasArea()) return new SetPolygon();
        if (simple.isHole()) simple.invert();
        return new SetPolygon(simple);
    }
}
