/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.maps.obstacles;

import amara.applications.ingame.shared.maps.MapObstacle;
import amara.core.Util;
import amara.libraries.physics.shapes.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Carl
 */
public class MapObstacle_Polygon extends MapObstacle {

    public MapObstacle_Polygon(ArrayList<Vector2D> outline) {
        List<ConvexShape> convexShapes = generateConvexShapes(outline);
        setConvexedOutline(new ConvexedOutline(outline, convexShapes));
    }
    private Triangulator triangulator = new Triangulator();

    private List<ConvexShape> generateConvexShapes(List<Vector2D> outline) {
        List<ConvexShape> convexShapes = new LinkedList<>();
        if (triangulator.isConvex(outline)) {
            Vector2D[] points = Util.toArray(outline, Vector2D.class);
            convexShapes.add(new SimpleConvexPolygon(points));
        } else if (triangulator.canTriangulate(outline)) {
            convexShapes.addAll(triangulator.createDelaunayTrisFromPoly(outline));
        } else {
            System.err.println("Can't triangulate polygon.");
        }
        return convexShapes;
    }
}
