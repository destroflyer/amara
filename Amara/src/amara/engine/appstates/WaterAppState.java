/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.jme3.water.SimpleWaterProcessor;
import com.jme3.water.WaterFilter;

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

    public WaterFilter createWaterFilter(Vector3f position, Vector2f size){
        WaterFilter waterFilter  = new WaterFilter(mainApplication.getRootNode(), new Vector3f(1, -4, 1).normalizeLocal());
        waterFilter.setCenter(position.add((size.getX() / 2), 0, (size.getY() / 2)));
        waterFilter.setRadius(Math.max((size.getX() / 2), (size.getY() / 2)));
        waterFilter.setShapeType(WaterFilter.AreaShape.Square);
        waterFilter.setFoamIntensity(0.2f);
        waterFilter.setFoamHardness(0.8f);
        waterFilter.setRefractionStrength(0.2f);
        waterFilter.setShininess(0.5f);
        waterFilter.setSpeed(0.5f);
        waterFilter.setUseRipples(false);
        waterFilter.setWaterTransparency(0.2f);
        waterFilter.setWaterColor(new ColorRGBA(0, 0.2f, 0.8f, 1));
        waterFilter.setWaterHeight(position.getY());
        return waterFilter;
    }
}
