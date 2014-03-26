/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.maps;

import com.jme3.material.Material;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import amara.engine.materials.MaterialFactory;
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
    }
    
    private void loadMaterial(String mapName){
        Material material = new Material(MaterialFactory.getAssetManager(), "Common/MatDefs/Terrain/TerrainLighting.j3md");
        Texture alphaMapTexture = MaterialFactory.getAssetManager().loadTexture("Maps/" + mapName + "/terrain_alphamap.png");
        material.setTexture("AlphaMap", alphaMapTexture);
        TerrainSkin skin = TerrainSkin.getSkin("grass");
        for(int i=0;i<skin.getTextures().length;i++){
            TerrainSkin_Texture terrainTexture = skin.getTextures()[i];
            Texture texture = MaterialFactory.getAssetManager().loadTexture(terrainTexture.getFilePath());
            texture.setWrap(terrainTexture.getWrapMode());
            material.setTexture("DiffuseMap" + ((i == 0)?"":"_" + i), texture);
            material.setFloat("DiffuseMap_" + i + "_scale", terrainTexture.getScale());
        }
        terrain.setMaterial(material);
    }

    public TerrainQuad getTerrain(){
        return terrain;
    }
}
