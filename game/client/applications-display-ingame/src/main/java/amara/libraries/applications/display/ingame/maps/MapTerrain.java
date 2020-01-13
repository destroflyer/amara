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
    private TerrainAlphamap[] alphamaps;

    private void loadHeightmap(){
        MapPhysicsInformation physicsInformation = map.getPhysicsInformation();
        Texture heightMapImage = MaterialFactory.getAssetManager().loadTexture("Maps/" + map.getName() + "/terrain_heightmap.png");
        ImageBasedHeightMap heightmap = new ImageBasedHeightMap(heightMapImage.getImage());
        heightmap.load();
        int scaledHeightmapSize = (int) ((heightmap.getSize() / Math.pow(2, Settings.getFloat("terrain_quality"))) + 1);
        terrain = new TerrainQuad("terrain", 129, scaledHeightmapSize, scaleHeightmap(heightmap, scaledHeightmapSize));
        float scaleX = (physicsInformation.getWidth() / terrain.getTotalSize());
        float scaleZ = (physicsInformation.getHeight() / terrain.getTotalSize());
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
        TerrainSkin skin = map.getTerrainSkin();
        alphamaps = new TerrainAlphamap[(int) Math.ceil(skin.getTextures().length / 4.0)];
        for(int i=0;i<alphamaps.length;i++){
            alphamaps[i] = new TerrainAlphamap("Maps/" + map.getName() + "/terrain_alphamap_" + i + ".png");
        }
        boolean triPlanarMapping = Settings.getBoolean("terrain_triplanar_mapping");
        material.setBoolean("useTriPlanarMapping", triPlanarMapping);
        int textureIndex = 0;
        for(int i=0;i<skin.getTextures().length;i++){
            TerrainSkin_Texture terrainTexture = skin.getTextures()[i];
            Texture texture = MaterialFactory.getAssetManager().loadTexture(terrainTexture.getFilePath());
            texture.setWrap(terrainTexture.getWrapMode());
            material.setTexture("DiffuseMap" + ((textureIndex == 0)?"":"_" + textureIndex), texture);
            float textureScale = terrainTexture.getScale();
            if(triPlanarMapping){
                textureScale = (1 / (terrain.getTotalSize() / textureScale));
            }
            material.setFloat("DiffuseMap_" + textureIndex + "_scale", textureScale);
            textureIndex++;
            //Alpha channel not used
            if(((textureIndex + 1) % 4) == 0){
                textureIndex++;
            }
        }
        terrain.setMaterial(material);
        for(int i=0;i<alphamaps.length;i++){
            updateAlphamap(i);
        }
    }
    
    public void updateAlphamap(int index){
        TerrainAlphamap alphamap = alphamaps[index];
        alphamap.updateTexture();
        Material material = terrain.getMaterial();
        material.setTexture("AlphaMap" + ((index == 0)?"":"_" + index), alphamap.getTexture2D());
    }

    public TerrainQuad getTerrain(){
        return terrain;
    }

    public TerrainAlphamap[] getAlphamaps(){
        return alphamaps;
    }
}
