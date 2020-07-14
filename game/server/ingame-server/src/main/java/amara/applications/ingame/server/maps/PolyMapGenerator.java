package amara.applications.ingame.server.maps;

import amara.applications.ingame.shared.maps.Map;
import amara.applications.ingame.shared.maps.MapFileHandler;
import amara.applications.ingame.shared.maps.MapPhysicsInformation;
import amara.core.files.FileAssets;
import amara.libraries.physics.shapes.PolygonMath.Polygon;
import amara.libraries.physics.shapes.Triangulator;
import amara.libraries.physics.shapes.Vector2D;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class PolyMapGenerator {

    private PolyMapGenerator(double... radiuse) {
        this.radiuse = radiuse;
    }
    private double[] radiuse;

    private HashMap<Double, ArrayList<Vector2D>> precomputeNavigationTriangles(MapPhysicsInformation mapPhysicsInformation) {
        Polygon mapPolygon = PolyMapLoader.createBoundedMapPolygon(mapPhysicsInformation);
        HashMap<Double, ArrayList<Vector2D>> navigationPolygons = new HashMap<>();
        for (double radius : radiuse) {
            Polygon navigationPolygon = PolyMapLoader.generateNavigationPolygon(mapPolygon, radius);
            ArrayList<Vector2D> navigationTriangles = triangulateNavigationPolygon(navigationPolygon);
            navigationPolygons.put(radius, navigationTriangles);
        }
        return navigationPolygons;
    }

    private ArrayList<Vector2D> triangulateNavigationPolygon(Polygon navigationPolygon) {
        ArrayList<Vector2D> triangles = navigationPolygon.triangles();
        return new Triangulator().delaunayTris(triangles);
    }

    public static void main(String[] args) {
        FileAssets.readRootFile();
        PolyMapGenerator polyMapGenerator = new PolyMapGenerator(0, 1.5);
        String[] mapNames = { "arama", "astrudan", "destroforest", "empty", "etherdesert", "techtest", "testmap" };
        for (String mapName : mapNames) {
            System.out.println("Calculating navigation triangles for '" + mapName + "'...");
            Map map = MapFileHandler.load(mapName);
            HashMap<Double, ArrayList<Vector2D>> navigationTriangles = polyMapGenerator.precomputeNavigationTriangles(map.getPhysicsInformation());
            File file = PolyMapLoader.getNavigationTrianglesFile(mapName);
            PolyMapFileHandler.write(navigationTriangles, file);
        }
    }
}
