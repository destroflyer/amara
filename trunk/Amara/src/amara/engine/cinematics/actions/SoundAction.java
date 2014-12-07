/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.cinematics.actions;

import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioSource;
import amara.engine.applications.DisplayApplication;
import amara.engine.appstates.AudioAppState;
import amara.engine.cinematics.CinematicAction;

/**
 *
 * @author Carl
 */
public class SoundAction extends CinematicAction{

    public SoundAction(String soundPath){
        this.soundPath = soundPath;
    }
    private String soundPath;
    private AudioNode audioNode;
    
    @Override
    public void trigger(DisplayApplication displayApplication){
        AudioAppState audioAppState = displayApplication.getStateManager().getState(AudioAppState.class);
        audioNode = audioAppState.createAudioNode(soundPath);
        audioNode.play();
    }

    @Override
    public boolean isFinished(){
        return (audioNode.getStatus() != AudioSource.Status.Playing);
    }
}
