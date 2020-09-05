/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.appstates;

import com.jme3.app.Application;
import com.jme3.app.DetailedProfilerState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;

/**
 *
 * @author Carl
 */
public class ProfilerAppState extends BaseDisplayAppState implements ActionListener {

    private DetailedProfilerState detailedProfilerState;

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);
        mainApplication.getInputManager().addMapping("toggle_profiler", new KeyTrigger(KeyInput.KEY_L));
        mainApplication.getInputManager().addListener(this, "toggle_profiler");
    }

    @Override
    public void cleanup() {
        super.cleanup();
        mainApplication.getInputManager().removeListener(this);
        if (detailedProfilerState != null) {
            detachProfiler();
        }
    }

    @Override
    public void onAction(String name, boolean isPressed, float lastTimePerFrame) {
        if (name.equals("toggle_profiler") && isPressed) {
            if (detailedProfilerState == null) {
                attachProfiler();
            } else {
                detachProfiler();
            }
        }
    }

    private void attachProfiler() {
        detailedProfilerState = new DetailedProfilerState();
        mainApplication.getStateManager().attach(detailedProfilerState);
    }

    private void detachProfiler() {
        mainApplication.getStateManager().detach(detailedProfilerState);
        detailedProfilerState = null;
    }
}
