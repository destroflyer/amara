/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
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

    public Geometry createWaterPlane(Vector3f position, Vector2f size){
        Quad quad = new Quad(size.getX(), size.getY());
        quad.scaleTextureCoordinates(new Vector2f((quad.getWidth() / 20), (quad.getHeight() / 20)));
        Geometry waterPlane = new Geometry("", quad);
        waterPlane.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X));
        waterPlane.setMaterial(getMaterial());
        float z = (position.getZ() + size.getY());
        waterPlane.setLocalTranslation(position.getX(), position.getY(), z);
        return waterPlane;
    }
    
    public Material getMaterial(){
        return waterProcessor.getMaterial();
    }
}
