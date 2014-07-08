/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.intersectionHelper;

import amara.game.entitysystem.systems.physics.shapes.PolygonMath.*;
import amara.game.entitysystem.systems.physics.shapes.*;
import java.util.ArrayList;

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
        ArrayList<Point2D> tris = poly.triangles();
        tris = new Triangulator().delaunayTris(tris);
//        graph = new TriangleGraph(tris, 2, 3);
        star = new TriangleStar(tris);
//        aStar = new AbstractTriangleStar(tris);
    }
    
    public ArrayList<Point2D> findPath(Point2D start, Point2D end, double radius)
    {
        start = closestValid(start);
        end = closestValid(end);
//        path = star.findChannel(start, end, 0);
        return star.findPath(start, end, radius);
//        return graph.findPath(start, end);
    }
    public TrianglePath findTriPath(Point2D start, Point2D end, double radius)
    {
        start = closestValid(start);
        end = closestValid(end);
//        path = star.findChannel(start, end, 0);
        return star.findTriPath(start, end, radius);
//        return graph.findPath(start, end);
    }
    public Point2D closestValid(Point2D p)
    {
        if(!poly.contains(p)) return p.add(poly.distanceToBorder(p).mult(1 + Util.Epsilon));
        return p;
    }
}
