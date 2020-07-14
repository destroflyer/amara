package amara.applications.ingame.server.maps;

import amara.libraries.physics.shapes.Vector2D;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class PolyMapFileHandler {

    static void write(HashMap<Double, ArrayList<Vector2D>> navigationTrianglesMap, File file) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
            for (Map.Entry<Double, ArrayList<Vector2D>> entry : navigationTrianglesMap.entrySet()) {
                double radius = entry.getKey();
                ArrayList<Vector2D> navigationTriangles = entry.getValue();
                dataOutputStream.writeDouble(radius);
                dataOutputStream.writeInt(navigationTriangles.size());
                for (Vector2D position : navigationTriangles) {
                    dataOutputStream.writeDouble(position.getX());
                    dataOutputStream.writeDouble(position.getY());
                }
            }
            dataOutputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static HashMap<Double, ArrayList<Vector2D>> read(File file) {
        HashMap<Double, ArrayList<Vector2D>> navigationTrianglesMap = new HashMap<>();
        try {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
            while (dataInputStream.available() > 0) {
                double radius = dataInputStream.readDouble();
                double navigationTrianglesSize = dataInputStream.readInt();
                ArrayList<Vector2D> navigationTriangles = new ArrayList<>();
                for (int i = 0; i < navigationTrianglesSize; i++) {
                    double x = dataInputStream.readDouble();
                    double y = dataInputStream.readDouble();
                    navigationTriangles.add(new Vector2D(x, y));
                }
                navigationTrianglesMap.put(radius, navigationTriangles);
            }
            dataInputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return navigationTrianglesMap;
    }
}
