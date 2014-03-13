/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.water.SimpleWaterProcessor;

/**
 *
 * @author Carl
 */
public class WaterAppState extends BaseDisplayAppState{
    
    private SimpleWaterProcessor waterProcessor;

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        waterProcessor = new SimpleWaterProcessor(mainApplication.getAssetManager());
        waterProcessor.setReflectionScene(mainApplication.getRootNode());
        waterProcessor.setLightPosition(new Vector3f(30, 10, -30));
        waterProcessor.setDistortionScale(0.1f);
        waterProcessor.setWaveSpeed(0.015f);
        mainApplication.getViewPort().addProcessor(waterProcessor);
    }

    @Override
    public void cleanup(){
        super.cleanup();
        mainApplication.getViewPort().removeProcessor(waterProcessor);
    }
    
    public Material getMaterial(){
        return waterProcessor.getMaterial();
    }
}
