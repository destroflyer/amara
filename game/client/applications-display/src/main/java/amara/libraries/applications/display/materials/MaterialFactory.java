/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.materials;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;
import com.jme3.math.Vector4f;

/**
 *
 * @author Carl
 */
public class MaterialFactory{

    public static final String DEFINITION_NAME_LIGHTING = "Common/MatDefs/Light/Lighting.j3md";
    public static final String DEFINITION_NAME_UNSHADED = "Common/MatDefs/Misc/Unshaded.j3md";
    private static AssetManager assetManager;
    
    public static Material generateUnshadedMaterial(ColorRGBA color){
        Material material = new Material(assetManager, DEFINITION_NAME_UNSHADED);
        material.setColor("Color",  color);
        return material;
    }

    public static Material generateLightingMaterial(ColorRGBA color){
        Material material = new Material(assetManager, DEFINITION_NAME_LIGHTING);
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Diffuse",  color);
        if(true){
            material.setColor("Ambient",  color);
            //material.setColor("Specular", ColorRGBA.White);
            //material.setFloat("Shininess", 15);
        }
        return material;
    }
    
    public static void generateAmbientColor(Material material, float ambient){
        if((material.getParam("Diffuse") != null) && (material.getParam("Ambient") != null)){
            ColorRGBA diffuseColor = (ColorRGBA) (material.getParam("Diffuse").getValue());
            Vector4f newAmbient = diffuseColor.toVector4f().multLocal(ambient, ambient, ambient, 1);
            material.setVector4("Ambient", newAmbient);
        }
    }

    public static Material generateLightingMaterial(String textureFilePath){
        return generateLightingMaterial(textureFilePath, null);
    }
    
    public static Material generateLightingMaterial(String textureFilePath, String normalMapFilePath){
        Material material = new Material(assetManager, DEFINITION_NAME_LIGHTING);
        Texture textureDiffuse = loadTexture(textureFilePath);
        textureDiffuse.setWrap(Texture.WrapMode.Repeat);
        material.setTexture("DiffuseMap", textureDiffuse);
        if(normalMapFilePath != null){
            Texture textureNormalMap = loadTexture(normalMapFilePath);
            material.setTexture("NormalMap", textureNormalMap);
        }
        material.setFloat("Shininess", 5);
        return material;
    }
    
    public static Material generateUnshadedMaterial(String textureFilePath){
        Material material = new Material(assetManager, DEFINITION_NAME_UNSHADED);
        Texture texture = loadTexture(textureFilePath);
        texture.setWrap(Texture.WrapMode.Repeat);
        material.setTexture("ColorMap", texture);
        return material;
    }
    
    public static Texture loadTexture(String filePath){
        return assetManager.loadTexture(new TextureKey(filePath, false));
    }

    public static void setFilter_Nearest(Material material){
        String textureParameterName = ((material.getParam("DiffuseMap") != null)?"DiffuseMap":"ColorMap");
        Texture texture = material.getTextureParam(textureParameterName).getTextureValue();
        texture.setMagFilter(Texture.MagFilter.Nearest);
        texture.setMinFilter(Texture.MinFilter.NearestNoMipMaps);
    }

    public static void setTransparent(Material material, boolean isTransparent){
        if (isTransparent) {
            material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            material.setFloat("AlphaDiscardThreshold", 0.05f);
        } else {
            material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Off);
            material.clearParam("AlphaDiscardThreshold");
        }
    }

    public static void setAssetManager(AssetManager assetManager){
        MaterialFactory.assetManager = assetManager;
    }

    public static AssetManager getAssetManager(){
        return assetManager;
    }
}
