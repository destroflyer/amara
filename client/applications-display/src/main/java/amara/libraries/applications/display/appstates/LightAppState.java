package amara.libraries.applications.display.appstates;

import java.util.ArrayList;
import com.jme3.light.Light;
import com.jme3.shadow.AbstractShadowFilter;
import amara.libraries.applications.display.DisplayApplication;

public class LightAppState extends BaseDisplayAppState<DisplayApplication> {

    private ArrayList<Light> lights = new ArrayList<>();
    private ArrayList<AbstractShadowFilter> shadowsFilters = new ArrayList<>();
    
    public void addLight(final Light light) {
        lights.add(light);
        mainApplication.enqueue(() -> mainApplication.getRootNode().addLight(light));
    }

    public void removeLight(final Light light) {
        lights.remove(light);
        mainApplication.enqueue(() -> mainApplication.getRootNode().removeLight(light));
    }

    public void addShadowFilter(AbstractShadowFilter abstractShadowFilter) {
        shadowsFilters.add(abstractShadowFilter);
        PostFilterAppState postFilterAppState = getAppState(PostFilterAppState.class);
        postFilterAppState.addFilter(abstractShadowFilter);
    }

    public void removeShadowFilter(AbstractShadowFilter abstractShadowFilter) {
        shadowsFilters.remove(abstractShadowFilter);
        PostFilterAppState postFilterAppState = getAppState(PostFilterAppState.class);
        postFilterAppState.removeFilter(abstractShadowFilter);
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
        final PostFilterAppState postFilterAppState = getAppState(PostFilterAppState.class);
        for (AbstractShadowFilter abstractShadowFilter : shadowsFilters) {
            mainApplication.enqueue(() -> postFilterAppState.removeFilter(abstractShadowFilter));
        }
        shadowsFilters.clear();
    }
}
