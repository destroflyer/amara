/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.panels.connectscreens;

/**
 *
 * @author Carl
 */
public class KeyframeAnimation{

    public KeyframeAnimation(Keyframe... keyframes){
        this.keyframes = keyframes;
    }
    private Keyframe[] keyframes;
    
    public float getValue(float time){
        Keyframe keyframe1 = null;
        Keyframe keyframe2 = null;
        for(Keyframe keyframe : keyframes){
            if(keyframe.getTime() <= time){
                keyframe1 = keyframe;
            }
            else{
                keyframe2 = keyframe;
                break;
            }
        }
        if(keyframe1 != null){
            if(keyframe1 == keyframe2){
                return keyframe1.getValue();
            }
            else if(keyframe2 == null){
                return keyframe1.getValue();
            }
            float progress = ((time - keyframe1.getTime()) / (keyframe2.getTime() - keyframe1.getTime()));
            return (keyframe1.getValue() + (progress * (keyframe2.getValue() - keyframe1.getValue())));
        }
        return -1;
    }
}
