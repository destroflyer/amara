/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.models;

import com.jme3.animation.*;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.engine.applications.DisplayApplication;

/**
 *
 * @author Carl
 */
public class ModelObject extends Node implements AnimEventListener{

    public ModelObject(DisplayApplication mainApplication, String skinResourcePath){
        this.mainApplication = mainApplication;
        loadSkin(new ModelSkin(skinResourcePath));
    }
    private DisplayApplication mainApplication;
    protected Spatial modelSpatial;
    private AnimChannel animationChannel;
    
    private void loadSkin(ModelSkin skin){
        modelSpatial = skin.loadSpatial();
        attachChild(modelSpatial);
        AnimControl animationControl = modelSpatial.getControl(AnimControl.class);
        if(animationControl != null){
            animationControl.addListener(this);
            animationChannel = animationControl.createChannel();
        }
        SkeletonControl skeletonControl = modelSpatial.getControl(SkeletonControl.class);
        if(skeletonControl != null){
            //skeletonControl.setHardwareSkinningPreferred(true);
        }
    }
    
    public void playAnimation(String animationName, float loopDuration){
        if(animationChannel != null){
            if(!animationName.equals(animationChannel.getAnimationName())){
                try{
                    animationChannel.setAnim(animationName);
                    animationChannel.setSpeed(animationChannel.getAnimMaxTime() / loopDuration);
                }catch(IllegalArgumentException ex){
                    stopAndRewindAnimation();
                }
            }
        }
    }
    
    public void stopAndRewindAnimation(){
        mainApplication.enqueueTask(new Runnable(){

            @Override
            public void run(){
                animationChannel.reset(true);
            }
        });
    }

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animationName){
        
    }

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animationName){
        
    }
}
