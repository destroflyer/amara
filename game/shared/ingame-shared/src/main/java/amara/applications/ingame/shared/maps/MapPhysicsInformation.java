package amara.applications.ingame.shared.maps;

import amara.libraries.physics.PolyHelper;
import amara.libraries.physics.intersectionHelper.PolyMapManager;
import amara.libraries.physics.shapes.ConvexShape;
import amara.libraries.physics.shapes.PolygonMath.Polygon;
import amara.libraries.physics.shapes.vision.VisionObstacle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MapPhysicsInformation {

    public MapPhysicsInformation(float width, float height, float heightmapScale, float groundHeight, ArrayList<MapObstacle> obstacles) {
        this.width = width;
        this.height = height;
        this.heightmapScale = heightmapScale;
        this.groundHeight = groundHeight;
        this.obstacles = obstacles;
        System.out.println("Creating poly map manager... (" + width + "x" + height + " with " + obstacles.size() + " obstacles)");
        polyMapManager = createPolyMapManager();
        System.out.println("Finished creating poly map manager.");
    }
    private float width;
    private float height;
    private float heightmapScale;
    private float groundHeight;
    private ArrayList<MapObstacle> obstacles;
    private PolyMapManager polyMapManager;

    private PolyMapManager createPolyMapManager(){
        ArrayList<ConvexShape> convexShapes = new ArrayList<>();
        for (MapObstacle obstacle : obstacles) {
            convexShapes.addAll(obstacle.getConvexedOutline().getConvexShapes());
        }
        Polygon polygon = PolyHelper.fromConvexShapes(convexShapes);
        return new PolyMapManager(polygon, width, height);
    }

    public List<VisionObstacle> generateVisionObstacles() {
        return obstacles.stream()
                .map(MapObstacle::getConvexedOutline)
                .map(convexedOutline -> new VisionObstacle(convexedOutline, false))
                .collect(Collectors.toList());
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getGroundHeight() {
        return groundHeight;
    }

    public float getHeightmapScale() {
        return heightmapScale;
    }

    public ArrayList<MapObstacle> getObstacles() {
        return obstacles;
    }

    public PolyMapManager getPolyMapManager() {
        return polyMapManager;
    }
}
