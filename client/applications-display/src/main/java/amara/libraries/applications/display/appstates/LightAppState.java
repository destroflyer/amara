/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.appstates;

import java.util.ArrayList;
import com.jme3.light.Light;
import com.jme3.shadow.AbstractShadowFilter;
import amara.libraries.applications.display.DisplayApplication;

/**
 *
 * @author Carl
 */
public class LightAppState extends BaseDisplayAppState<DisplayApplication>{

    public LightAppState(){
        
    }
    private ArrayList<Light> lights = new ArrayList<Light>();
    private ArrayList<AbstractShadowFilter> shadowsFilters = new ArrayList<AbstractShadowFilter>();
    
    public void addLight(final Light light){
        lights.add(light);
        mainApplication.enqueueTask(new Runnable(){

            @Override
            public void run(){
                mainApplication.getRootNode().addLight(light);
            }
        });
    }
    
    public void removeLight(final Light light){
        lights.remove(light);
        mainApplication.enqueueTask(new Runnable(){

            @Override
            public void run(){
                mainApplication.getRootNode().removeLight(light);
            }
        });
    }
    
    public void addShadowFilter(AbstractShadowFilter abstractShadowFilter){
        shadowsFilters.add(abstractShadowFilter);
        PostFilterAppState postFilterAppState = getAppState(PostFilterAppState.class);
        postFilterAppState.addFilter(abstractShadowFilter);
    }
    
    public void removeShadowFilter(AbstractShadowFilter abstractShadowFilter){
        shadowsFilters.remove(abstractShadowFilter);
        PostFilterAppState postFilterAppState = getAppState(PostFilterAppState.class);
        postFilterAppState.removeFilter(abstractShadowFilter);
    }

    @Override
    public void cleanup(){
        super.cleanup();
        removeAll();
    }
    
    public void removeAll(){
        for(final Light light : lights){
            mainApplication.enqueueTask(new Runnable(){

                @Override
                public void run(){
                    mainApplication.getRootNode().removeLight(light);
                }
            });
        }
        lights.clear();
        final PostFilterAppState postFilterAppState = getAppState(PostFilterAppState.class);
        for(final AbstractShadowFilter abstractShadowFilter : shadowsFilters){
            mainApplication.enqueueTask(new Runnable(){

                @Override
                public void run(){
                    postFilterAppState.removeFilter(abstractShadowFilter);
                }
            });
        }
        shadowsFilters.clear();
    }
}
