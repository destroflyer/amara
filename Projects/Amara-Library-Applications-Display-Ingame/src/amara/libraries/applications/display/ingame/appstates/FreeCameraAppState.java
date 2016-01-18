/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import amara.libraries.applications.display.DisplayApplication;
import amara.libraries.applications.display.appstates.*;

/**
 *
 * @author Carl
 */
public class FreeCameraAppState extends BaseDisplayAppState<DisplayApplication> implements ActionListener{

    public FreeCameraAppState(){
        
    }

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        mainApplication.getFlyByCamera().setMoveSpeed(20);
        mainApplication.getFlyByCamera().setZoomSpeed(0);
        mainApplication.getInputManager().addMapping("toggle_free_camera", new KeyTrigger(KeyInput.KEY_O));
        mainApplication.getInputManager().addListener(this, new String[]{
            "toggle_free_camera"
        });
    }

    @Override
    public void cleanup(){
        super.cleanup();
        mainApplication.getInputManager().removeListener(this);
    }

    @Override
    public void onAction(String name, boolean isPressed, float lastTimePerFrame){
        if(name.equals("toggle_free_camera") && isPressed){
            boolean activateFreeCamera = (!mainApplication.getFlyByCamera().isEnabled());
            getAppState(CinematicAppState.class).setCinematicModeEnabled(activateFreeCamera);
            mainApplication.getFlyByCamera().setEnabled(activateFreeCamera);
            mainApplication.getInputManager().setCursorVisible(!activateFreeCamera);
            if(!activateFreeCamera){
                getAppState(MapAppState.class).initializeCamera();
            }
        }
    }
}
