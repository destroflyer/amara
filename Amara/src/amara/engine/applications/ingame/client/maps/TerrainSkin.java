/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.maps;

/**
 *
 * @author Carl
 */
public class TerrainSkin{

    public TerrainSkin(TerrainSkin_Texture[] textures){
        this.textures = textures;
    }
    private TerrainSkin_Texture[] textures;
    
    public TerrainSkin_Texture[] getTextures(){
        return textures;
    }
}
