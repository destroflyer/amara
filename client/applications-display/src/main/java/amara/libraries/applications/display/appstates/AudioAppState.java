package amara.libraries.applications.display.appstates;

import amara.core.settings.Settings;
import amara.libraries.applications.display.DisplayApplication;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.audio.Listener;
import com.jme3.scene.Spatial;

public class AudioAppState extends BaseDisplayAppState<DisplayApplication> {

    private Node audioRootNode = new Node("audio_manager_node");
    private Listener listener = new Listener();

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);
        mainApplication.getRootNode().attachChild(audioRootNode);
        mainApplication.getAudioRenderer().setListener(listener);
        getAppState(SettingsAppState.class).subscribeFloat("audio_volume", audioVolume -> {
            for (Spatial child : audioRootNode.getChildren()) {
                AudioNode audioNode = (AudioNode) child;
                float effectiveVolume = audioVolume;
                Float customVolume = audioNode.getUserData("audio_custom_volume");
                if (customVolume != null) {
                    effectiveVolume *= customVolume;
                }
                audioNode.setVolume(effectiveVolume);
            }
        });
    }

    public AudioNode createAudioNode(String filePath) {
        AudioNode audioNode = new AudioNode(mainApplication.getAssetManager(), filePath);
        audioNode.setPositional(false);
        audioNode.setVolume(Settings.getFloat("audio_volume"));
        audioRootNode.attachChild(audioNode);
        return audioNode;
    }

    public void removeAudioNode(AudioNode audioNode) {
        audioRootNode.detachChild(audioNode);
    }

    public Listener getListener() {
        return listener;
    }
}
