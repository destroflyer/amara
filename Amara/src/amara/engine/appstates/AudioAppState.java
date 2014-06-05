/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.appstates;

import java.util.HashMap;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import amara.engine.settings.Settings;

/**
 *
 * @author Carl
 */
public class AudioAppState extends BaseDisplayAppState{

    private Node audioRootNode = new Node("audio_manager_node");
    private AudioNode backgroundMusicNode;
    private String currentBackgroundMusicFilePath;
    private HashMap<String, AudioNode> soundNodes = new HashMap<String, AudioNode>();

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        mainApplication.getRootNode().attachChild(audioRootNode);
    }
    
    public void playBackgroundMusic(String filePath){
        if(!filePath.equals(currentBackgroundMusicFilePath)){
            stopBackgroundMusic();
            backgroundMusicNode = new AudioNode(mainApplication.getAssetManager(), filePath);
            backgroundMusicNode.setPositional(false);
            backgroundMusicNode.setLooping(true);
            backgroundMusicNode.setVolume(Settings.getFloat("audio_volume_music"));
            audioRootNode.attachChild(backgroundMusicNode);
            backgroundMusicNode.play();
            currentBackgroundMusicFilePath = filePath;
        }
    }
    
    public void stopBackgroundMusic(){
        if(backgroundMusicNode != null){
            backgroundMusicNode.stop();
            audioRootNode.detachChild(backgroundMusicNode);
            currentBackgroundMusicFilePath = null;
        }
    }
    
    public void playSound(String filePath){
        AudioNode soundNode = getSoundNode(filePath);
        soundNode.playInstance();
    }
    
    private AudioNode getSoundNode(String filePath){
        AudioNode soundNode = soundNodes.get(filePath);
        if(soundNode == null){
            soundNode = new AudioNode(mainApplication.getAssetManager(), filePath);
            soundNode.setPositional(false);
            soundNode.setLooping(false);
            soundNode.setVolume(Settings.getFloat("audio_volume_sounds"));
            audioRootNode.attachChild(soundNode);
            soundNodes.put(filePath, soundNode);
        }
        return soundNode;
    }
    
    public AudioNode createAudioNode(String filePath){
        AudioNode audioNode = new AudioNode(mainApplication.getAssetManager(), filePath);
        audioNode.setPositional(false);
        audioNode.setVolume(Settings.getFloat("audio_volume_sounds"));
        audioRootNode.attachChild(audioNode);
        return audioNode;
    }
    
    public void removeAudioNode(AudioNode audioNode){
        audioRootNode.detachChild(audioNode);
    }
}
