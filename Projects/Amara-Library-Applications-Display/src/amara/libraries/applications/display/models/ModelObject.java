/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.models;

import java.util.ArrayList;
import com.jme3.animation.*;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.core.settings.Settings;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.DisplayApplication;

/**
 *
 * @author Carl
 */
public class ModelObject extends Node implements AnimEventListener{

    public ModelObject(DisplayApplication mainApplication, String skinPath){
        this.mainApplication = mainApplication;
        skin = new ModelSkin(skinPath);
        loadSkin();
    }
    private DisplayApplication mainApplication;
    private ModelSkin skin;
    private Spatial modelSpatial;
    private ArrayList<AnimChannel> animationChannels = new ArrayList<AnimChannel>();
    
    private void loadSkin(){
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
            AnimChannel animationChannel = copyAnimation(spatial);
            animationChannels.add(animationChannel);
        }
        boolean isHardwareSkinningPreferred = Settings.getBoolean("hardware_skinning");
        String rigType = skin.getRigType();
        if(rigType != null){
            String forceHardwareSkinningValue = Settings.get("hardware_skinning_rig_type_" + rigType);
            if(forceHardwareSkinningValue != null){
                isHardwareSkinningPreferred = Settings.toBoolean(forceHardwareSkinningValue);
            }
        }
        JMonkeyUtil.setHardwareSkinningPreferred(spatial, isHardwareSkinningPreferred);
    }

    public AnimChannel copyAnimation(Spatial spatial){
        AnimControl animationControl = spatial.getControl(AnimControl.class);
        if(animationControl != null){
            AnimChannel animationChannel = animationControl.createChannel();
            if(animationChannels.size() > 0){
                JMonkeyUtil.copyAnimation(animationChannels.get(0), animationChannel);
            }
            return animationChannel;
        }
        return null;
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
        setAnimationName(animationName);
        setAnimationProperties(loopDuration, isLoop);
    }
    
    public void setAnimationName(String animationName){
        for(AnimChannel animationChannel : animationChannels){
            try{
                animationChannel.setAnim(animationName);
            }catch(IllegalArgumentException ex){
                stopAndRewindAnimation(animationChannel);
            }
        }
    }
    
    public void setAnimationProperties(float loopDuration, boolean isLoop){
        for(AnimChannel animationChannel : animationChannels){
            if(animationChannel.getAnimationName() != null){
                animationChannel.setSpeed(animationChannel.getAnimMaxTime() / loopDuration);
                animationChannel.setLoopMode(isLoop?LoopMode.Loop:LoopMode.DontLoop);
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

    public ModelSkin getSkin(){
        return skin;
    }
    
    public Spatial getModelSpatial(){
        return modelSpatial;
    }
}
