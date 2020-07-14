package amara.applications.ingame.shared.maps;

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
    }
    private float width;
    private float height;
    private float heightmapScale;
    private float groundHeight;
    private ArrayList<MapObstacle> obstacles;

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
}
