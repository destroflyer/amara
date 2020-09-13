/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.appstates;

import java.io.File;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import amara.core.files.FileAssets;
import amara.libraries.applications.display.appstates.BaseDisplayAppState;

/**
 *
 * @author Carl
 */
public class PreloadAssetsAppState extends BaseDisplayAppState{

    public PreloadAssetsAppState(){
        
    }
    private static final String[] textureFileExtensions = new String[]{
        "png","jpg"
    };

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        preloadDirectory("Effekseer/Texture/");
        preloadDirectory("Textures/effects/");
        preloadDirectory("Textures/spell_indicators/");
    }
    
    public void preloadDirectory(String directoryPath){
        File directory = new File(FileAssets.ROOT + directoryPath);
        for(File file : directory.listFiles()){
            String assetPath = (directoryPath + file.getName());
            if(file.isDirectory()){
                preloadDirectory(assetPath + "/");
            }
            else{
                preloadAsset(assetPath);
            }
        }
    }
    
    public void preloadAsset(String assetPath){
        if(isTextureAsset(assetPath)){
            mainApplication.getAssetManager().loadTexture(assetPath);
        }
    }
    
    private static boolean isTextureAsset(String assetPath){
        String lowerPath = assetPath.toLowerCase();
        for(String textureFileExtension : textureFileExtensions){
            if(lowerPath.endsWith("." + textureFileExtension)){
                return true;
            }
        }
        return false;
    }
}
