package amara.libraries.applications.display.appstates;

import java.util.LinkedList;

import amara.libraries.applications.display.DisplayApplication;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.post.*;

public class PostFilterAppState extends BaseDisplayAppState<DisplayApplication> {

    private FilterPostProcessor filterPostProcessor;
    private LinkedList<Filter> queuedFilters = new LinkedList<>();

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);
        filterPostProcessor = new FilterPostProcessor(application.getAssetManager());
        for (Filter filter : queuedFilters) {
            filterPostProcessor.addFilter(filter);
        }
        setEnabled(true);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            mainApplication.getViewPort().addProcessor(filterPostProcessor);
        } else {
            mainApplication.getViewPort().removeProcessor(filterPostProcessor);
        }
    }

    @Override
    public void cleanup() {
        super.cleanup();
        mainApplication.getViewPort().removeProcessor(filterPostProcessor);
    }

    public void addFilter(final Filter filter) {
        if (isInitialized()) {
            filterPostProcessor.addFilter(filter);
        } else {
            queuedFilters.add(filter);
        }
    }

    public void removeFilter(final Filter filter) {
        if (isInitialized()) {
            filterPostProcessor.removeFilter(filter);
        } else {
            queuedFilters.remove(filter);
        }
    }
}
