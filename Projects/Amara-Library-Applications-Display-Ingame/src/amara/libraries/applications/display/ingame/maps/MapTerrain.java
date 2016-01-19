/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.maps;

import com.jme3.material.Material;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.HeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import amara.applications.ingame.shared.maps.*;
import amara.applications.ingame.shared.maps.terrain.*;
import amara.core.settings.Settings;
import amara.libraries.applications.display.materials.*;

/**
 *
 * @author Carl
 */
public class MapTerrain{

    public MapTerrain(Map map){
        this.map = map;
        loadHeightmap();
        loadMaterial();
    }
    private Map map;
    private TerrainQuad terrain;

    private void loadHeightmap(){
        MapPhysicsInformation physicsInformation = map.getPhysicsInformation();
        Texture heightMapImage = MaterialFactory.getAssetManager().loadTexture("Maps/" + map.getName() + "/terrain_heightmap.png");
        ImageBasedHeightMap heightmap = new ImageBasedHeightMap(heightMapImage.getImage());
        heightmap.load();
        int scaledHeightmapSize = (int) (heightmap.getSize() / Math.pow(2, Settings.getFloat("terrain_quality")));
        terrain = new TerrainQuad("terrain", 150, scaledHeightmapSize + 1, scaleHeightmap(heightmap, scaledHeightmapSize));
        float scaleX = (((float) physicsInformation.getWidth()) / terrain.getTotalSize());
        float scaleZ = (((float) physicsInformation.getHeight()) / terrain.getTotalSize());
        terrain.setLocalScale(scaleX, 1, scaleZ);
        terrain.setLocalTranslation((physicsInformation.getWidth() / 2), 0, (physicsInformation.getHeight() / 2));
        terrain.setShadowMode(RenderQueue.ShadowMode.Receive);
    }
    
    private float[] scaleHeightmap(HeightMap heightmap, int scaledHeightmapSize){
        float[] scaledHeightmap = new float[scaledHeightmapSize * scaledHeightmapSize];
        float scaleFactor = (((float) scaledHeightmapSize) / heightmap.getSize());
        for(int x=0;x<scaledHeightmapSize;x++){
            for(int y=0;y<scaledHeightmapSize;y++){
                scaledHeightmap[x + (y * scaledHeightmapSize)] = (heightmap.getInterpolatedHeight(x / scaleFactor, y / scaleFactor) * map.getPhysicsInformation().getHeightmapScale());
            }
        }
        return scaledHeightmap;
    }
    
    private void loadMaterial(){
        Material material = new Material(MaterialFactory.getAssetManager(), "Common/MatDefs/Terrain/TerrainLighting.j3md");
        material.setTexture("AlphaMap", loadAlphaMap("Maps/" + map.getName() + "/terrain_alphamap_0.png"));
        material.setTexture("AlphaMap_1", loadAlphaMap("Maps/" + map.getName() + "/terrain_alphamap_1.png"));
        material.setTexture("AlphaMap_2", loadAlphaMap("Maps/" + map.getName() + "/terrain_alphamap_2.png"));
        TerrainSkin skin = map.getTerrainSkin();
        for(int i=0;i<skin.getTextures().length;i++){
            TerrainSkin_Texture terrainTexture = skin.getTextures()[i];
            Texture texture = MaterialFactory.getAssetManager().loadTexture(terrainTexture.getFilePath());
            texture.setWrap(terrainTexture.getWrapMode());
            material.setTexture("DiffuseMap" + ((i == 0)?"":"_" + i), texture);
            material.setFloat("DiffuseMap_" + i + "_scale", terrainTexture.getScale());
        }
        terrain.setMaterial(material);
    }
    
    private Texture2D loadAlphaMap(String imagePath){
        PaintableImage paintableImage = new PaintableImage(imagePath, true);
        paintableImage.setBackground_Alpha(0);
        Texture2D texture2D = new Texture2D();
        texture2D.setImage(paintableImage.getImage());
        return texture2D;
    }

    public TerrainQuad getTerrain(){
        return terrain;
    }
}
