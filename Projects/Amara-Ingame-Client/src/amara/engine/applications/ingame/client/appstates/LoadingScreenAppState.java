/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import amara.engine.applications.ingame.client.IngameClientApplication;
import amara.engine.applications.ingame.client.gui.ScreenController_LoadingScreen;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.appstates.*;
import amara.libraries.applications.display.models.ModelObject;
import amara.libraries.applications.display.ingame.appstates.IngameCameraAppState;

/**
 *
 * @author Carl
 */
public class LoadingScreenAppState extends OverlayViewportAppState<IngameClientApplication>{

    private static final Quaternion MODEL_OBJECT_YAW = JMonkeyUtil.getQuaternion_Y(-90);
    private ModelObject modelObject1;
    private ModelObject modelObject2;
    private float animationProgress;
    private float animationRadius = 10;
    
    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        getAppState(IngameCameraAppState.class).setEnabled(false);
        //Lights
        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(new ColorRGBA(1.5f, 1.5f, 1.5f, 1));
        DirectionalLight directionalLight = new DirectionalLight();
        directionalLight.setColor(new ColorRGBA(1, 1, 1, 1));
        directionalLight.setDirection(new Vector3f(-0.5f, -1.75f, 1));
        rootNode.addLight(directionalLight);
        //Models
        modelObject1 = new ModelObject(mainApplication, "Models/minion/skin_loadingscreen_1.xml");
        modelObject1.playAnimation("walk", 0.38f);
        modelObject2 = new ModelObject(mainApplication, "Models/minion/skin_loadingscreen_2.xml");
        modelObject2.playAnimation("walk", 0.38f);
        rootNode.attachChild(modelObject1);
        rootNode.attachChild(modelObject2);
        //Camera
        viewPort.getCamera().setLocation(new Vector3f(0, 10, -26));
        viewPort.getCamera().lookAtDirection(new Vector3f(0, -0.5f, 1), Vector3f.UNIT_Y);
    }

    @Override
    public void cleanup(){
        super.cleanup();
        getAppState(IngameCameraAppState.class).setEnabled(true);
        getAppState(NiftyAppState.class).goToScreen(ScreenController_LoadingScreen.class, "ingame");
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        animationProgress += (1.1f * lastTimePerFrame);
        updateModelObject(modelObject1, animationProgress);
        updateModelObject(modelObject2, animationProgress - 0.5f);
    }
    
    private void updateModelObject(ModelObject modelObject, float progress){
        modelObject.setLocalTranslation(FastMath.cos(progress) * animationRadius, 0, FastMath.sin(progress) * animationRadius);
        JMonkeyUtil.setLocalRotation(modelObject, MODEL_OBJECT_YAW.mult(modelObject.getLocalTranslation()));
    }
}
