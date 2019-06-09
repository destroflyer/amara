/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.post.SceneProcessor;
import com.jme3.profile.AppProfiler;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.texture.FrameBuffer;

/**
 *
 * @author Carl
 */
public class WireframeAppState extends BaseDisplayAppState implements ActionListener{

    private WireframeProcessor wireframeProcessor;

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        setColor(ColorRGBA.Blue);
        mainApplication.getInputManager().addMapping("toggle_wireframe_processor", new KeyTrigger(KeyInput.KEY_I));
        mainApplication.getInputManager().addListener(this, "toggle_wireframe_processor");
    }

    @Override
    public void cleanup(){
        super.cleanup();
        mainApplication.getInputManager().removeListener(this);
    }
    
    public void setColor(ColorRGBA color){
        wireframeProcessor = new WireframeProcessor(color);
    }

    @Override
    public void onAction(String name, boolean isPressed, float lastTimePerFrame){
        if(name.equals("toggle_wireframe_processor") && isPressed){
            boolean deactivateWireframe = mainApplication.getViewPort().getProcessors().contains(wireframeProcessor);
            if(deactivateWireframe){
                mainApplication.getViewPort().removeProcessor(wireframeProcessor);
            }
            else{
                mainApplication.getViewPort().addProcessor(wireframeProcessor);
            }
            getAppState(PostFilterAppState.class).setEnabled(deactivateWireframe);
        }
    }
    
    private class WireframeProcessor implements SceneProcessor{    
 
        public WireframeProcessor(ColorRGBA color){
            AssetManager assetManager = WireframeAppState.this.mainApplication.getAssetManager();
            wireMaterial = new Material(assetManager, "/Common/MatDefs/Misc/Unshaded.j3md");
            wireMaterial.setColor("Color", color);
            wireMaterial.getAdditionalRenderState().setWireframe(true);
        }
        private RenderManager renderManager;
        private Material wireMaterial;

        @Override
        public void initialize(RenderManager rm, ViewPort vp){
            renderManager = rm;
        }

        @Override
        public void reshape(ViewPort vp, int w, int h){
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isInitialized(){
            return (renderManager != null);
        }

        @Override
        public void preFrame(float tpf){   

        }

        @Override
        public void postQueue(RenderQueue rq){
            renderManager.setForcedMaterial(wireMaterial);
        }

        @Override
        public void postFrame(FrameBuffer out){
            renderManager.setForcedMaterial(null);
        }

        @Override
        public void cleanup(){
            renderManager.setForcedMaterial(null);
        }

        @Override
        public void setProfiler(AppProfiler profiler) {
            
        }
    }
}
