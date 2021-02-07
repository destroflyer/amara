package amara.libraries.applications.display.appstates;

import java.util.ArrayList;
import com.jme3.light.Light;
import amara.libraries.applications.display.DisplayApplication;

public class LightAppState extends BaseDisplayAppState<DisplayApplication> {

    private ArrayList<Light> lights = new ArrayList<>();
    
    public void addLight(final Light light) {
        lights.add(light);
        mainApplication.enqueue(() -> mainApplication.getRootNode().addLight(light));
    }

    public void removeLight(final Light light) {
        lights.remove(light);
        mainApplication.enqueue(() -> mainApplication.getRootNode().removeLight(light));
    }

    @Override
    public void cleanup() {
        super.cleanup();
        removeAll();
    }

    public void removeAll() {
        for (Light light : lights) {
            mainApplication.enqueue(() -> mainApplication.getRootNode().removeLight(light));
        }
        lights.clear();
    }
}
