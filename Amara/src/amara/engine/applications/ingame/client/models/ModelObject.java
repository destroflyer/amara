/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.models;

import java.util.ArrayList;
import com.jme3.animation.*;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.engine.JMonkeyUtil;
import amara.engine.applications.DisplayApplication;
import amara.engine.settings.Settings;

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
    private Spatial modelSpatial;
    private ArrayList<AnimChannel> animationChannels = new ArrayList<AnimChannel>();
    
    private void loadSkin(ModelSkin skin){
        modelSpatial = skin.loadSpatial();
        for(ModelModifier modelModifier : skin.getModelModifiers()){
            modelModifier.modify(this);
        }
        attachChild(modelSpatial);
        registerModel(modelSpatial);
    }

    public void registerModel(Spatial spatial){
        AnimControl animationControl = spatial.getControl(AnimControl.class);
        if(animationControl != null){
            animationControl.addListener(this);
            AnimChannel animationChannel = animationControl.createChannel();
            if(animationChannels.size() > 0){
                JMonkeyUtil.copyAnimation(animationChannels.get(0), animationChannel);
            }
            animationChannels.add(animationChannel);
        }
        SkeletonControl skeletonControl = modelSpatial.getControl(SkeletonControl.class);
        if(skeletonControl != null){
            skeletonControl.setHardwareSkinningPreferred(Settings.getBoolean("hardware_skinning"));
        }
    }

    public void unregisterModel(Spatial spatial){
        AnimControl animationControl = spatial.getControl(AnimControl.class);
        if(animationControl != null){
            for(int i=0;i<animationControl.getNumChannels();i++){
                animationChannels.remove(animationControl.getChannel(i));
            }
        }
    }
    
    public void playAnimation(String animationName, float loopDuration){
        playAnimation(animationName, loopDuration, true);
    }
    
    public void playAnimation(String animationName, float loopDuration, boolean isLoop){
        for(AnimChannel animationChannel : animationChannels){
            try{
                if(!animationName.equals(animationChannel.getAnimationName())){
                    animationChannel.setAnim(animationName);
                }
                animationChannel.setSpeed(animationChannel.getAnimMaxTime() / loopDuration);
                animationChannel.setLoopMode(isLoop?LoopMode.Loop:LoopMode.DontLoop);
            }catch(IllegalArgumentException ex){
                stopAndRewindAnimation(animationChannel);
            }
        }
    }
    
    public void stopAndRewindAnimation(){
        for(AnimChannel animationChannel : animationChannels){
            stopAndRewindAnimation(animationChannel);
        }
    }
    
    public void stopAndRewindAnimation(final AnimChannel animationChannel){
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
    
    public Spatial getModelSpatial(){
        return modelSpatial;
    }
}
