/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath.Public;

import amara.game.entitysystem.systems.physics.shapes.PolygonMath.*;

/**
 *
 * @author Philipp
 */
public class Polygon
{
    private SetPolygon setPoly;

    Polygon(SetPolygon setPoly) {
        this.setPoly = setPoly;
    }
    
    public Polygon union(Polygon poly)
    {
        return new Polygon(SetPolygonUtil.union(setPoly, poly.setPoly));
    }
    public Polygon intersection(Polygon poly)
    {
        return new Polygon(SetPolygonUtil.intersection(setPoly, poly.setPoly));
    }
    public Polygon exclude(Polygon poly)
    {
        return new Polygon(SetPolygonUtil.exclude(setPoly, poly.setPoly));
    }
    public boolean intersects(Polygon poly)
    {
        return SetPolygonUtil.intersect(setPoly, poly.setPoly);
    }
    public Polygon flip()
    {
        return new Polygon(MinkowskiUtil.flip(setPoly));
    }
    public Polygon minkowskiAdd(Polygon poly)
    {
        return new Polygon(MinkowskiUtil.add(setPoly, poly.setPoly));
    }

    public boolean contains(Point2D point)
    {
        //return setPoly.areaContainsFast(point);
        return setPoly.areaContains(point) == Containment.Inside;
    }

    public Point2D distanceToBorder(Point2D point)
    {
        return setPoly.minBorderDistance(point);
    }

//    public ArrayList<ArrayList<Point2D>> lines()
//    {
//        return setPoly.extractLines();
//    }
//
//    public ArrayList<ArrayList<Point2D>> cutPolys()
//    {
//        return setPoly.extractCutPolys();
//    }

    public boolean isInfinite()
    {
        return setPoly.isInfinite();
    }
    
    public BoundRectangle boundRectangle()
    {
        double minX , minY, maxX, maxY;
        minX = minY = Double.POSITIVE_INFINITY;
        maxX = maxY = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < setPoly.numPolygons(); i++) {
            HolePolygon poly = setPoly.getPolygon(i);
            for (int j = 0; j < poly.numSimplePolys(); j++) {
                SimplePolygon simple = poly.getSimplePoly(j);
                for (int k = 0; k < simple.numPoints(); k++) {
                    Point2D p = simple.getPoint(k);
                    double x = p.getX();
                    double y = p.getY();
                    if(x < minX) minX = x;
                    else if(maxX < x) maxX = x;
                    if(y < minY) minY = y;
                    else if(maxY < y) maxY = y;
                }
            }
        }
        return new BoundRectangle(minX, minY, maxX, maxY);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Polygon) return equals((Polygon)obj);
        return false;
    }
    public boolean equals(Polygon poly)
    {
        return setPoly.equals(poly.setPoly);
    }

    @Override
    public int hashCode()
    {
        return setPoly.hashCode() ^ 0x02468ace;
    }

    @Override
    public String toString()
    {
        return setPoly.toString();
    }
}
