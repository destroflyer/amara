/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.maps;

import com.jme3.material.Material;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import amara.engine.materials.*;
import amara.game.maps.MapPhysicsInformation;

/**
 *
 * @author Carl
 */
public class MapTerrain{

    public MapTerrain(String mapName, MapPhysicsInformation mapPhysicsInformation){
        this.mapPhysicsInformation = mapPhysicsInformation;
        loadHeightmap(mapName);
        loadMaterial(mapName);
    }
    private TerrainQuad terrain;
    private MapPhysicsInformation mapPhysicsInformation;

    private void loadHeightmap(String mapName){
        Texture heightMapImage = MaterialFactory.getAssetManager().loadTexture("Maps/" + mapName + "/terrain_heightmap.png");
        AbstractHeightMap heightmap = new ImageBasedHeightMap(heightMapImage.getImage(), mapPhysicsInformation.getHeightmapScale());
        heightmap.load();
        terrain = new TerrainQuad("terrain", 20, heightmap.getSize() + 1, heightmap.getHeightMap());
        float scaleX = (((float) mapPhysicsInformation.getWidth()) / terrain.getTotalSize());
        float scaleZ = (((float) mapPhysicsInformation.getHeight()) / terrain.getTotalSize());
        terrain.setLocalScale(scaleX, 1, scaleZ);
        terrain.setLocalTranslation((mapPhysicsInformation.getWidth() / 2), 0, (mapPhysicsInformation.getHeight() / 2));
        terrain.setShadowMode(RenderQueue.ShadowMode.Receive);
    }
    
    private void loadMaterial(String mapName){
        Material material = new Material(MaterialFactory.getAssetManager(), "Common/MatDefs/Terrain/TerrainLighting.j3md");
        material.setTexture("AlphaMap", loadAlphaMap("Maps/" + mapName + "/terrain_alphamap_0.png"));
        material.setTexture("AlphaMap_1", loadAlphaMap("Maps/" + mapName + "/terrain_alphamap_1.png"));
        material.setTexture("AlphaMap_2", loadAlphaMap("Maps/" + mapName + "/terrain_alphamap_2.png"));
        TerrainSkin skin = TerrainSkin.getSkin("cartoon_forest");
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
        PaintableImage paintableImage = new PaintableImage("/" + imagePath, true);
        paintableImage.setBackground_Alpha(0);
        Texture2D texture2D = new Texture2D();
        texture2D.setImage(paintableImage.getImage());
        return texture2D;
    }

    public TerrainQuad getTerrain(){
        return terrain;
    }
}
