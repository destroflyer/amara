/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.maps.obstacles;

import amara.applications.ingame.shared.maps.MapObstacle;
import amara.libraries.physics.shapes.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Carl
 */
public class MapObstacle_Circle extends MapObstacle {

    public MapObstacle_Circle(Vector2D position, double radius) {
        ArrayList<Vector2D> outline = ConvexedOutline.generateCcwOutline_Circle(position, radius);
        List<ConvexShape> convexShapes = generateConvexShapes(position, radius);
        setConvexedOutline(new ConvexedOutline(outline, convexShapes));
    }

    private static List<ConvexShape> generateConvexShapes(Vector2D position, double radius) {
        List<ConvexShape> convexShapes = new LinkedList<>();
        Circle circle = new Circle(new Vector2D(), radius);
        circle.setTransform(new Transform2D(1, 0, position.getX(), position.getY()));
        convexShapes.add(circle);
        return convexShapes;
    }
}
