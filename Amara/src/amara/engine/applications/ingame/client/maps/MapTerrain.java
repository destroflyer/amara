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
import amara.game.maps.Map;
import amara.game.maps.MapPhysicsInformation;

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
        AbstractHeightMap heightmap = new ImageBasedHeightMap(heightMapImage.getImage(), physicsInformation.getHeightmapScale());
        heightmap.load();
        terrain = new TerrainQuad("terrain", 20, heightmap.getSize() + 1, heightmap.getHeightMap());
        float scaleX = (((float) physicsInformation.getWidth()) / terrain.getTotalSize());
        float scaleZ = (((float) physicsInformation.getHeight()) / terrain.getTotalSize());
        terrain.setLocalScale(scaleX, 1, scaleZ);
        terrain.setLocalTranslation((physicsInformation.getWidth() / 2), 0, (physicsInformation.getHeight() / 2));
        terrain.setShadowMode(RenderQueue.ShadowMode.Receive);
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
