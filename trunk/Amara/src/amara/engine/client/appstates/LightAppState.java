/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.shadow.DirectionalLightShadowRenderer;

/**
 *
 * @author Carl
 */
public class LightAppState extends BaseAppState{

    public LightAppState(){
        ambientLight = new AmbientLight();
        directionalLight = new DirectionalLight();
    }
    private AmbientLight ambientLight;
    private DirectionalLight directionalLight;
    private DirectionalLightShadowRenderer shadowRenderer;
    private float shadowsIntensity;

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        mainApplication.getRootNode().addLight(ambientLight);
        mainApplication.getRootNode().addLight(directionalLight);
        shadowRenderer = new DirectionalLightShadowRenderer(mainApplication.getAssetManager(), 2048, 3);
        shadowRenderer.setLight(directionalLight);
        shadowRenderer.setShadowIntensity(shadowsIntensity);
        mainApplication.getViewPort().addProcessor(shadowRenderer);
    }

    @Override
    public void cleanup(){
        super.cleanup();
        mainApplication.getRootNode().removeLight(ambientLight);
        mainApplication.getRootNode().removeLight(directionalLight);
        mainApplication.getViewPort().removeProcessor(shadowRenderer);
    }
    
    public void setAmbientLightColor(ColorRGBA color){
        ambientLight.setColor(color);
    }
    
    public void setDirectionalLightColor(ColorRGBA color){
        directionalLight.setColor(color);
    }
    
    public void setLightDirection(Vector3f direction){
        directionalLight.setDirection(direction);
    }
    
    public void setShadowsIntensity(float intenstiy){
        shadowsIntensity = intenstiy;
        if(shadowRenderer != null){
            shadowRenderer.setShadowIntensity(intenstiy);
        }
    }
}
