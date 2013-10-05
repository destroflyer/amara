/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.models;

import com.jme3.animation.*;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Carl
 */
public class ModelObject extends Node implements AnimEventListener{

    public ModelObject(String skinResourcePath){
        loadSkin(new ModelSkin(skinResourcePath));
    }
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
    }
    
    public void playAnimation(String animationName){
        playAnimation(animationName, 1);
    }
    
    public void playAnimation(final String animationName, final float speed){
        if(animationChannel != null){
            if(!animationName.equals(animationChannel.getAnimationName())){
                try{
                    animationChannel.setAnim(animationName);
                    animationChannel.setSpeed(speed);
                }catch(IllegalArgumentException ex){
                    stopAndRewindAnimation();
                }
            }
        }
    }
    
    public void stopAndRewindAnimation(){
        animationChannel.reset(true);
    }

    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animationName){
        
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animationName){
        
    }
}
