/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.appstates;

import java.util.ArrayList;
import com.jme3.light.Light;
import com.jme3.shadow.AbstractShadowRenderer;

/**
 *
 * @author Carl
 */
public class LightAppState extends BaseDisplayAppState{

    public LightAppState(){
        
    }
    private ArrayList<Light> lights = new ArrayList<Light>();
    private ArrayList<AbstractShadowRenderer> shadowsRenderers = new ArrayList<AbstractShadowRenderer>();
    
    public void addLight(Light light){
        lights.add(light);
        mainApplication.getRootNode().addLight(light);
    }
    
    public void removeLight(Light light){
        lights.remove(light);
        mainApplication.getRootNode().removeLight(light);
    }
    
    public void addShadowRenderer(AbstractShadowRenderer abstractShadowRenderer){
        shadowsRenderers.add(abstractShadowRenderer);
        mainApplication.getViewPort().addProcessor(abstractShadowRenderer);
    }
    
    public void removeShadowRenderer(AbstractShadowRenderer abstractShadowRenderer){
        shadowsRenderers.remove(abstractShadowRenderer);
        mainApplication.getViewPort().removeProcessor(abstractShadowRenderer);
    }

    @Override
    public void cleanup(){
        super.cleanup();
        removeAll();
    }
    
    public void removeAll(){
        for(Light light : lights){
            mainApplication.getRootNode().removeLight(light);
        }
        for(AbstractShadowRenderer abstractShadowRenderer : shadowsRenderers){
            mainApplication.getViewPort().removeProcessor(abstractShadowRenderer);
        }
    }
}
