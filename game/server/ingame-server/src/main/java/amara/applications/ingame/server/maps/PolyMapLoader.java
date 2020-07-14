package amara.applications.ingame.server.maps;

import amara.applications.ingame.shared.maps.Map;
import amara.applications.ingame.shared.maps.MapObstacle;
import amara.applications.ingame.shared.maps.MapPhysicsInformation;
import amara.core.files.FileAssets;
import amara.libraries.physics.PolyHelper;
import amara.libraries.physics.intersectionHelper.PolyMapManager;
import amara.libraries.physics.shapes.ConvexShape;
import amara.libraries.physics.shapes.PolygonMath.Polygon;
import amara.libraries.physics.shapes.Vector2D;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class PolyMapLoader {

    public static PolyMapManager createPolyMapManager(Map map) {
        MapPhysicsInformation mapPhysicsInformation = map.getPhysicsInformation();
        Polygon boundedMapPolygon = createBoundedMapPolygon(mapPhysicsInformation);
        PolyMapManager polyMapManager = new PolyMapManager(boundedMapPolygon, mapPhysicsInformation.getWidth(), mapPhysicsInformation.getHeight());

        File navigationTrianglesFile = getNavigationTrianglesFile(map.getName());
        HashMap<Double, ArrayList<Vector2D>> navigationTrianglesMap = PolyMapFileHandler.read(navigationTrianglesFile);

        System.out.println("Loading navigation maps of '" + map.getName() + "'...");
        for (java.util.Map.Entry<Double, ArrayList<Vector2D>> entry : navigationTrianglesMap.entrySet()) {
            double radius = entry.getKey();
            ArrayList<Vector2D> navigationTriangles = entry.getValue();
            Polygon navigationPolygon = generateNavigationPolygon(polyMapManager.getMapPolygon(), radius);
            polyMapManager.addNavigationMap(navigationPolygon, navigationTriangles, radius);
        }
        System.out.println("Finished loading navigation maps of '" + map.getName() + "'.");

        return polyMapManager;
    }

    static Polygon createBoundedMapPolygon(MapPhysicsInformation mapPhysicsInformation) {
        // Obstacles
        ArrayList<ConvexShape> convexShapes = new ArrayList<>();
        for (MapObstacle obstacle : mapPhysicsInformation.getObstacles()) {
            convexShapes.addAll(obstacle.getConvexedOutline().getConvexShapes());
        }
        Polygon polygon = PolyHelper.fromConvexShapes(convexShapes);
        // Bounds
        double width = mapPhysicsInformation.getWidth();
        double height = mapPhysicsInformation.getHeight();
        return polygon.union(PolyHelper.rectangle(0, 0, width, height).inverse());
    }

    static Polygon generateNavigationPolygon(Polygon mapPolygon, double radius) {
        Polygon navigationPolygon = mapPolygon;
        if (radius != 0) {
            int edges = 8;
            Polygon cyclic = PolyHelper.regularCyclic(0, 0, radius, edges);
            navigationPolygon = navigationPolygon.minkowskiAdd(cyclic);
        }
        navigationPolygon = navigationPolygon.inverse();
        return navigationPolygon;
    }

    static File getNavigationTrianglesFile(String mapName) {
        return new File(FileAssets.ROOT + "Maps/" + mapName + "/map.nav");
    }
}
