/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.maps;

import amara.engine.client.MaterialFactory;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;

/**
 *
 * @author Carl
 */
public class MapTerrain{

    public MapTerrain(String mapName){
        this.mapName = mapName;
        loadHeightmap();
        loadMaterial();
    }
    private String mapName;
    private TerrainQuad terrain;

    private void loadHeightmap(){
        Texture heightMapImage = MaterialFactory.getAssetManager().loadTexture("Maps/" + mapName + "/heightmap.png");
        AbstractHeightMap heightmap = new ImageBasedHeightMap(heightMapImage.getImage(), 0.8f);
        heightmap.load();
        terrain = new TerrainQuad("terrain", 65, heightmap.getSize() + 1, heightmap.getScaledHeightMap());
        terrain.setLocalTranslation((terrain.getTotalSize() / 2), 0, (terrain.getTotalSize() / 2));
    }
    
    private void loadMaterial(){
        Material material = new Material(MaterialFactory.getAssetManager(), "Common/MatDefs/Terrain/TerrainLighting.j3md");
        Texture alphaMapTexture = MaterialFactory.getAssetManager().loadTexture("Maps/" + mapName + "/alphamap.png");
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

    public void addTerrainHeightToVector3f(Vector3f vector3f){
        vector3f.addLocal(0, getHeight(vector3f.getX(), vector3f.getZ()), 0);
    }

    public float getHeight(float x, float z){
        return getHeight(new Vector2f(x, z));
    }

    public float getHeight(Vector2f location){
        if(terrain != null){
            return terrain.getHeight(location);
        }
        return 0;
    }

    public TerrainQuad getTerrain(){
        return terrain;
    }
}
