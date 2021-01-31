package amara.libraries.physics.shapes.vision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import amara.libraries.physics.shapes.*;

public class MergedVision {

    public MergedVision(List<VisionObstacle> fixedObstacles) {
        this(new ArrayList<>(), fixedObstacles);
    }

    public MergedVision(ArrayList<Vector2D> ccwBorderOutline, List<VisionObstacle> fixedObstacles) {
        this.ccwBorderOutline = ccwBorderOutline;
        this.fixedObstacles = fixedObstacles;
    }
    private ArrayList<Vector2D> ccwBorderOutline;
    private List<VisionObstacle> fixedObstacles;
    private HashMap<Integer, VisionObstacle> dynamicObstacles = new HashMap<>();
    private Vision vision = new Vision();
    private ArrayList<Vector2D> visionEdges = new ArrayList<>();
    private HashMap<Double, Circle> visionCircleShapes = new HashMap<>();
    private HashMap<Integer, SightResult> sightResults = new HashMap<>();
    private boolean reverseSightInsideObstacles;

    public boolean isVisible(Vector2D position) {
        for (SightResult sightResult : sightResults.values()) {
            if (sightResult.contains(position)) {
                return true;
            }
        }
        return false;
    }

    public void setObstacle(int id, VisionObstacle visionObstacle) {
        dynamicObstacles.put(id, visionObstacle);
    }

    public void removeObstacle(int id) {
        dynamicObstacles.remove(id);
    }

    public void setVision(int id, Vector2D position, double sightRange) {
        ArrayList<Vector2D> sightOutline = getSightOutline(position, sightRange);
        sightResults.put(id, new SightResult(position, sightOutline));
    }

    public void removeVision(int id) {
        sightResults.remove(id);
    }

    public ArrayList<Vector2D> getSightOutline(Vector2D position, double sightRange) {
        visionEdges.clear();
        addEdges(visionEdges, ccwBorderOutline, false);
        ArrayList<Vector2D> sightRangeCcwOutline = ConvexedOutline.generateCcwOutline_Circle(position, sightRange);
        addEdges(visionEdges, sightRangeCcwOutline, false);
        Circle visionCircleShape = getVisionCircleShape(sightRange);
        visionCircleShape.setTransform(new Transform2D(1, 0, position.getX(), position.getY()));
        addObstacles(visionEdges, position, visionCircleShape, fixedObstacles);
        addObstacles(visionEdges, position, visionCircleShape, dynamicObstacles.values());
        return vision.sightPolyOutline(position, visionEdges);
    }

    private void addObstacles(List<Vector2D> edges, Vector2D position, Circle visionCircleShape, Iterable<VisionObstacle> obstacles) {
        for (VisionObstacle obstacle : obstacles){
            ConvexedOutline convexedOutline = obstacle.getConvexedOutline();
            if (convexedOutline.intersects(visionCircleShape)) {
                boolean blocksVisionInside = obstacle.isBlockingInside();
                if (reverseSightInsideObstacles && convexedOutline.contains(position)) {
                    blocksVisionInside = !blocksVisionInside;
                }
                addEdges(edges, convexedOutline.getCcwOutline(), blocksVisionInside);
            }
        }
    }

    private static void addEdges(List<Vector2D> edges, ArrayList<Vector2D> ccwOutline, boolean isBlockingInside) {
        if (ccwOutline.size() > 0){
            if (isBlockingInside) {
                Vector2D lastPoint = ccwOutline.get(ccwOutline.size() - 1);
                for (Vector2D point : ccwOutline) {
                    edges.add(lastPoint);
                    edges.add(point);
                    lastPoint = point;
                }
            } else {
                Vector2D lastPoint = ccwOutline.get(0);
                for (int i = (ccwOutline.size() - 1); i >= 0; i--) {
                    edges.add(lastPoint);
                    edges.add(ccwOutline.get(i));
                    lastPoint = ccwOutline.get(i);
                }
            }
        }
    }

    private Circle getVisionCircleShape(double radius) {
        Circle circle = visionCircleShapes.get(radius);
        if (circle == null) {
            circle = new Circle(radius);
            visionCircleShapes.put(radius, circle);
        }
        return circle;
    }

    public void setReverseSightInsideObstacles(boolean reverseSightInsideObstacles) {
        this.reverseSightInsideObstacles = reverseSightInsideObstacles;
    }
}
