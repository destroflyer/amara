package amara.libraries.applications.display.appstates;

import amara.core.settings.Settings;
import amara.core.settings.SettingsSubscriptions;
import amara.libraries.applications.display.DisplayApplication;
import amara.libraries.applications.display.JMonkeyUtil;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.system.AppSettings;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SettingsAppState extends BaseDisplayAppState<DisplayApplication> implements SettingsSubscriptions {

    public SettingsAppState() {
        listeners = new HashMap<>();
    }
    private HashMap<String, List<Consumer<?>>> listeners;
    private boolean needsContextRestart;

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);
        subscribeBoolean("fullscreen", fullscreen -> {
            getSettings().setFullscreen(fullscreen);
            needsContextRestart = true;
        });
        subscribeBoolean("resolution_width", fullscreen -> {
            getSettings().setWidth(Settings.getInteger("resolution_width"));
            needsContextRestart = true;
        });
        subscribeBoolean("resolution_height", fullscreen -> {
            getSettings().setHeight(Settings.getInteger("resolution_height"));
            needsContextRestart = true;
        });
        subscribeInteger("antialiasing", samples -> {
            getSettings().setSamples(samples);
            needsContextRestart = true;
        });
        subscribeBoolean("vsync", vsync -> {
            getSettings().setVSync(vsync);
            needsContextRestart = true;
        });
        subscribeBoolean("hardware_skinning", hardwareSkinning -> {
            mainApplication.getRootNode().depthFirstTraversal(spatial -> {
                if (spatial.getUserData("hardwareSkinnable") != null) {
                    JMonkeyUtil.setHardwareSkinningPreferred(spatial, hardwareSkinning);
                }
            });
        });
        // The initial settings don't need a context restart, as they are already active
        needsContextRestart = false;
    }

    @Override
    public void subscribeInteger(String key, Consumer<Integer> listener) {
        saveListener(key, listener);
        Settings.subscribeInteger(key, listener);
    }

    @Override
    public void subscribeFloat(String key, Consumer<Float> listener) {
        saveListener(key, listener);
        Settings.subscribeFloat(key, listener);
    }

    @Override
    public void subscribeBoolean(String key, Consumer<Boolean> listener) {
        saveListener(key, listener);
        Settings.subscribeBoolean(key, listener);
    }

    @Override
    public void subscribeString(String key, Consumer<String> listener) {
        saveListener(key, listener);
        Settings.subscribeString(key, listener);
    }

    private <T> void saveListener(String key, Consumer<?> listener) {
        listeners.computeIfAbsent(key, k -> new LinkedList<>()).add(listener);
    }

    private AppSettings getSettings() {
        return mainApplication.getContext().getSettings();
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        if (needsContextRestart) {
            mainApplication.restart();
            needsContextRestart = false;
        }
    }

    @Override
    public void cleanup() {
        super.cleanup();
        for (Map.Entry<String, List<Consumer<?>>> entry : listeners.entrySet()) {
            for (Consumer<?> listener : entry.getValue()) {
                Settings.unsubscribe(entry.getKey(), listener);
            }
        }
    }
}
