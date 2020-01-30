package amara.libraries.physics.shapes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class ConvexedOutline {

    private static final int CIRCLE_EDGES = 10;
    private static final Transform2D CIRCLE_NEXT_POINT_TRANSFORM = new Transform2D(1, ((Math.PI * 2) / CIRCLE_EDGES), 0, 0);

    public ConvexedOutline(SimpleConvexPolygon simpleConvexPolygon) {
        // SimpleConvexPolygon is already counterclockwise
        ccwOutline = Util.toList(simpleConvexPolygon.getGlobalPoints());
        convexShapes = new LinkedList<>();
        convexShapes.add(simpleConvexPolygon);
    }

    public ConvexedOutline(ArrayList<Vector2D> outline, List<ConvexShape> convexShapes) {
        this.ccwOutline = new ArrayList<>(outline);
        if (Util.isClockwise(ccwOutline)) {
            Util.reverse(ccwOutline);
        }
        this.convexShapes = convexShapes;
    }
    private ArrayList<Vector2D> ccwOutline;
    private List<ConvexShape> convexShapes;

    public boolean contains(Vector2D point) {
        return matches(convexShape -> convexShape.contains(point));
    }

    public boolean intersects(Shape shape) {
        return matches(convexShape -> convexShape.intersects(shape));
    }

    private boolean matches(Predicate<ConvexShape> predicate) {
        return convexShapes.stream().anyMatch(predicate);
    }

    public ArrayList<Vector2D> getCcwOutline() {
        return ccwOutline;
    }

    public List<ConvexShape> getConvexShapes() {
        return convexShapes;
    }

    public static ArrayList<Vector2D> generateCcwOutline_Circle(Vector2D position, double radius) {
        ArrayList<Vector2D> outline = new ArrayList<>(CIRCLE_EDGES);
        Vector2D relativePosition = new Vector2D(0, radius);
        for (int i = 0; i < CIRCLE_EDGES; i++) {
            relativePosition = CIRCLE_NEXT_POINT_TRANSFORM.transform(relativePosition);
            Vector2D point = position.add(relativePosition);
            outline.add(point);
        }
        return outline;
    }
}
