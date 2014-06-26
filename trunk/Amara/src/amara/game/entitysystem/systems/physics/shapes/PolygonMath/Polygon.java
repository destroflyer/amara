/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath;

import com.jme3.network.serializing.Serializable;
import java.util.*;

/**
 *
 * @author Philipp
 */
@Serializable
public class Polygon
{
    private SetPolygon setPoly;

    public Polygon()
    {
    }
    
    Polygon(SetPolygon setPoly)
    {
        this.setPoly = setPoly;
        assert setPoly.isValid();
    }
    
    public Polygon transform(double scale, double rotate, double x, double y)
    {
        return new Polygon(SetPolygonUtil.transform(setPoly, new Transform2D(scale, rotate, x, y)));
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
    public Polygon inverse()
    {
        return new Polygon(SetPolygonUtil.inverse(setPoly));
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

    public static Polygon preSortedUnion(Collection<Polygon> polys)
    {
        if(polys.isEmpty()) return new Polygon(new SetPolygon());
        ArrayList<SetPolygon> setPolys = new ArrayList<SetPolygon>();
        for (Polygon poly : polys)
        {
            setPolys.add(poly.setPoly);
        }
        SetPolygonUtil.preSortedUnion(setPolys);
        return new Polygon(setPolys.get(0));
    }
    
    public ArrayList<Point2D> triangles()
    {
        return SetPolygonUtil.triangles(setPoly);
    }
    
    public HashSet<Point2D> points()
    {
        return SetPolygonUtil.points(setPoly);
    }
    
    public double signedArea()
    {
        return setPoly.signedArea();
    }

    public boolean isInfinite()
    {
        return setPoly.isInfinite();
    }
    
    public ArrayList<ArrayList<Point2D>> outlines()
    {
        return SetPolygonUtil.outlines(setPoly);
    }
    public ArrayList<Point2D> edges()
    {
        ArrayList<Point2D> edges = new ArrayList<Point2D>();
        SetPolygonUtil.edges(edges, setPoly);
        return edges;
    }
    
    public void writeToFile(String filename)
    {
        ArrayList<SetPolygon> list = new ArrayList<SetPolygon>();
        list.add(setPoly);
        SetPolygonUtil.writePolys(filename, list);
        System.out.println("wrote " + filename);
    }
    public static Polygon readFromFile(String filename)
    {
        ArrayList<SetPolygon> list = SetPolygonUtil.readPolys(filename);
        if(list == null) return null;
        System.out.println("read " + filename);
        return new Polygon(list.get(0));
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

    @Override
    public Polygon clone()
    {
        return new Polygon(setPoly);
    }
    
    
}
