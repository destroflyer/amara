/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.shapes.PolygonMath;

import java.awt.*;
import java.util.*;
import com.jme3.network.serializing.Serializable;
import amara.libraries.physics.shapes.*;

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
    
    public Polygon transform(Transform2D transform)
    {
        return new Polygon(SetPolygonUtil.transform(setPoly, transform));
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

    public boolean contains(Vector2D point)
    {
        //return setPoly.areaContainsFast(point);
        return setPoly.areaContains(point) == Containment.Inside;
    }

    public Vector2D distanceToBorder(Vector2D point)
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
    
    public ArrayList<Vector2D> triangles()
    {
        return SetPolygonUtil.triangles(setPoly);
    }
    
    public HashSet<Vector2D> points()
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
    
    public ArrayList<ArrayList<Vector2D>> outlines()
    {
        return SetPolygonUtil.outlines(setPoly);
    }
    public ArrayList<ArrayList<Vector2D>> cutPolys()
    {
        ArrayList<ArrayList<Vector2D>> list = new ArrayList<ArrayList<Vector2D>>();
        for (int i = 0; i < setPoly.numPolygons(); i++)
        {
            SimplePolygon simple = HolePolygonUtil.cutToSimple(setPoly.getPolygon(i));
            ArrayList<Vector2D> points = new ArrayList<Vector2D>();
            for (int j = 0; j < simple.numPoints(); j++)
            {
                points.add(simple.getPoint(j));
            }
            list.add(points);
        }
        return list;
    }
    public ArrayList<Vector2D> edges()
    {
        ArrayList<Vector2D> edges = new ArrayList<Vector2D>();
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
    
    public void rasterize(RasterMap raster, Vector2D source, double radius)
    {
        SetPolygonUtil.rasterize(setPoly, raster, source, radius * radius);
    }
    public void rasterize(RasterMap raster)
    {
        SetPolygonUtil.rasterize(setPoly, raster);
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
                    Vector2D p = simple.getPoint(k);
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
    
    public void draw(Graphics graphics)
    {
        for (ArrayList<Vector2D> poly : outlines())
        {
            int[] xPoints = new int[poly.size()];
            int[] yPoints = new int[poly.size()];
            toDrawPoly(xPoints, yPoints, poly);
            graphics.drawPolygon(xPoints, yPoints, poly.size());
        }
    }
    private void toDrawPoly(int[] xPoints, int[] yPoints, ArrayList<Vector2D> poly)
    {
        for (int i = 0; i < poly.size(); i++)
        {
            xPoints[i] = (int)poly.get(i).getX();
            yPoints[i] = (int)poly.get(i).getY();
        }
    }
    private void toDrawPoly(int[] xPoints, int[] yPoints, SimplePolygon poly)
    {
        for (int i = 0; i < poly.numPoints(); i++)
        {
            xPoints[i] = (int)poly.getPoint(i).getX();
            yPoints[i] = (int)poly.getPoint(i).getY();
        }
    }
    public void fill(Graphics graphics)
    {
        for (int i = 0; i < setPoly.numPolygons(); i++)
        {
            HolePolygon hole = setPoly.getPolygon(i);
            fill(graphics, HolePolygonUtil.cutToSimple(hole));
        }
    }
    private  void fill(Graphics graphics, SimplePolygon simple)
    {
        int[] xPoints = new int[simple.numPoints()];
        int[] yPoints = new int[simple.numPoints()];
        toDrawPoly(xPoints, yPoints, simple);
        graphics.fillPolygon(xPoints, yPoints, simple.numPoints());
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
