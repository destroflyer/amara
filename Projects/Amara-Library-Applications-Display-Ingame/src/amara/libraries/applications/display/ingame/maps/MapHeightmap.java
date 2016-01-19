/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.maps;

import com.jme3.math.Vector2f;
import amara.applications.ingame.shared.maps.MapPhysicsInformation;
import amara.libraries.applications.display.materials.PaintableImage;

/**
 *
 * @author Carl
 */
public class MapHeightmap{

    public MapHeightmap(String mapName, MapPhysicsInformation mapPhysicsInformation){
        image = new PaintableImage("Maps/" + mapName + "/heightmap.png");
        this.mapPhysicsInformation = mapPhysicsInformation;
    }
    private PaintableImage image;
    private MapPhysicsInformation mapPhysicsInformation;

    public float getHeight(Vector2f location){
        return getHeight(location.getX(), location.getY());
    }

    public float getHeight(float x, float y){
        int pixelX = (int) ((x / mapPhysicsInformation.getWidth()) * image.getWidth());
        int pixelY = (int) ((y / mapPhysicsInformation.getHeight()) * image.getHeight());
        float height = 0;
        try{
            height = image.getPixel_Red(pixelX, pixelY);
        }catch(ArrayIndexOutOfBoundsException ex){
        }
        return (height * mapPhysicsInformation.getHeightmapScale());
    }
}
