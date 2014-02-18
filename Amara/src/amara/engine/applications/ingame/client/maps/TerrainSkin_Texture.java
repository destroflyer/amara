/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.maps;

import com.jme3.texture.Texture.WrapMode;

/**
 *
 * @author Carl
 */
public class TerrainSkin_Texture{

    public TerrainSkin_Texture(String filePath, float scale){
        this(filePath, scale, WrapMode.Repeat);
    }

    public TerrainSkin_Texture(String filePath, float scale, WrapMode wrapMode){
        this.filePath = filePath;
        this.scale = scale;
        this.wrapMode = wrapMode;
    }
    private String filePath;
    private float scale;
    private WrapMode wrapMode;

    public String getFilePath(){
        return filePath;
    }

    public float getScale(){
        return scale;
    }

    public WrapMode getWrapMode(){
        return wrapMode;
    }
}
