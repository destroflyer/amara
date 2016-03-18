/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.appstates;

import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.audio.Listener;
import amara.core.settings.Settings;

/**
 *
 * @author Carl
 */
public class AudioAppState extends BaseDisplayAppState{

    private Node audioRootNode = new Node("audio_manager_node");
    private Listener listener = new Listener();

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        mainApplication.getRootNode().attachChild(audioRootNode);
        mainApplication.getAudioRenderer().setListener(listener);
    }
    
    public AudioNode createAudioNode(String filePath){
        AudioNode audioNode = new AudioNode(mainApplication.getAssetManager(), filePath);
        audioNode.setPositional(false);
        audioNode.setVolume(Settings.getFloat("audio_volume"));
        audioRootNode.attachChild(audioNode);
        return audioNode;
    }
    
    public void removeAudioNode(AudioNode audioNode){
        audioRootNode.detachChild(audioNode);
    }

    public Listener getListener(){
        return listener;
    }
}
