/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.intersectionHelper;

import java.util.ArrayList;
import amara.libraries.physics.shapes.*;
import amara.libraries.physics.shapes.PolygonMath.*;

/**
 *
 * @author Philipp
 */
public class NavigationMap
{
    private Polygon poly;
//    private TriangleGraph graph;
    private TriangleStar star;
//    private AbstractTriangleStar aStar;

    public NavigationMap(Polygon poly)
    {
        this.poly = poly;
        ArrayList<Vector2D> tris = poly.triangles();
        tris = new Triangulator().delaunayTris(tris);
//        graph = new TriangleGraph(tris, 2, 3);
        star = new TriangleStar(tris);
//        aStar = new AbstractTriangleStar(tris);
    }

    public Polygon getPoly()
    {
        return poly;
    }
    
    public ArrayList<Vector2D> findPath(Vector2D start, Vector2D end, double radius)
    {
        start = closestValid(start);
        end = closestValid(end);
//        path = star.findChannel(start, end, 0);
        return star.findPath(start, end, radius);
//        return graph.findPath(start, end);
    }
    public TrianglePath findTriPath(Vector2D start, Vector2D end, double radius)
    {
        start = closestValid(start);
        end = closestValid(end);
//        path = star.findChannel(start, end, 0);
        return star.findTriPath(start, end, radius);
//        return graph.findPath(start, end);
    }
    public Vector2D closestValid(Vector2D p)
    {
        if(!poly.contains(p)) return p.add(poly.distanceToBorder(p).mult(1 + Util.Epsilon));
        return p;
    }
}
