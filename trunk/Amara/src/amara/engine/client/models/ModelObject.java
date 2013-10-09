/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.models;

import com.jme3.animation.*;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.engine.client.MainApplication;

/**
 *
 * @author Carl
 */
public class ModelObject extends Node implements AnimEventListener{

    public ModelObject(MainApplication mainApplication, String skinResourcePath){
        this.mainApplication = mainApplication;
        loadSkin(new ModelSkin(skinResourcePath));
    }
    private MainApplication mainApplication;
    protected Spatial modelSpatial;
    private AnimChannel animationChannel;
    private boolean isPlayingLoopedAnimation;
    
    private void loadSkin(ModelSkin skin){
        modelSpatial = skin.loadSpatial();
        attachChild(modelSpatial);
        AnimControl animationControl = modelSpatial.getControl(AnimControl.class);
        if(animationControl != null){
            animationControl.addListener(this);
            animationChannel = animationControl.createChannel();
        }
    }
    
    public void playAnimation(String animationName, float loopDuration, boolean isLooped){
        if(animationChannel != null){
            if(!animationName.equals(animationChannel.getAnimationName())){
                try{
                    animationChannel.setAnim(animationName);
                    animationChannel.setSpeed(animationChannel.getAnimMaxTime() / loopDuration);
                    isPlayingLoopedAnimation = isLooped;
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
        if(!isPlayingLoopedAnimation){
            stopAndRewindAnimation();
        }
    }

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animationName){
        
    }
}
