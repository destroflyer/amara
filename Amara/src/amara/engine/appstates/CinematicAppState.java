/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.KeyInput;
import com.jme3.scene.CameraNode;
import com.jme3.scene.control.CameraControl;
import amara.engine.cinematics.*;
import amara.engine.applications.ingame.client.appstates.*;
import amara.engine.applications.ingame.client.gui.*;
import amara.engine.applications.ingame.client.systems.filters.FogOfWarSystem;
import amara.engine.applications.ingame.client.systems.visualisation.HUDAttachmentsSystem;
import amara.game.entitysystem.EntitySystem;

/**
 *
 * @author Carl
 */
public class CinematicAppState extends BaseDisplayAppState implements ActionListener{

    public CinematicAppState(){
        
    }
    private Cinematic currentCinematic;
    private CameraNode cameraNode;
    private boolean tmpAreObstaclesDisplayed;
    private boolean tmpWasLockedCameraEnabled;
    private boolean tmpWasDisplayMapSight;

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        cameraNode = new CameraNode("cinematics_camera_node", mainApplication.getCamera());
        cameraNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        cameraNode.setEnabled(false);
        mainApplication.getRootNode().attachChild(cameraNode);
        mainApplication.getInputManager().addMapping("stop_cinematic", new KeyTrigger(KeyInput.KEY_ESCAPE));
        mainApplication.getInputManager().addListener(this, new String[]{
            "stop_cinematic"
        });
    }

    @Override
    public void cleanup(){
        super.cleanup();
        mainApplication.getInputManager().removeListener(this);
    }
    
    public void playCinematic(Cinematic cinematic){
        stopCinematic();
        cinematic.reset();
        currentCinematic = cinematic;
        setCinematicModeEnabled(true);
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        if(currentCinematic != null){
            //Avoid (initial) lag influencing the cinematic
            if(lastTimePerFrame < 1){
                currentCinematic.update(lastTimePerFrame, mainApplication);
                if(currentCinematic.isFinished()){
                    onCinematicStop();
                }
            }
        }
    }
    
    public void stopCinematic(){
        if(currentCinematic != null){
            currentCinematic.stop(mainApplication);
            onCinematicStop();
        }
    }
    
    private void onCinematicStop(){
        currentCinematic = null;
        setCinematicModeEnabled(false);
    }
    
    public void setCinematicModeEnabled(boolean isEnabled){
        getAppState(IngameCameraAppState.class).setEnabled(!isEnabled);
        setHUDAttachmentSystemsEnabled(!isEnabled);
        getAppState(SendPlayerCommandsAppState.class).setEnabled(!isEnabled);
        FogOfWarSystem fogOfWarSystem = getAppState(PlayerAppState.class).getFogOfWarSystem();
        if(isEnabled){
            getAppState(IngameCameraAppState.class).saveState();
            tmpAreObstaclesDisplayed = getAppState(MapObstaclesAppState.class).areObstaclesDisplayed();
            getAppState(MapObstaclesAppState.class).setDisplayObstacles(false);
            tmpWasLockedCameraEnabled = getAppState(PlayerAppState.class).getLockedCameraSystem().isEnabled();
            getAppState(PlayerAppState.class).getLockedCameraSystem().setEnabled(false);
            if(fogOfWarSystem != null){
                tmpWasDisplayMapSight = fogOfWarSystem.isDisplayMapSight();
                fogOfWarSystem.setDisplayMapSight(true);
            }
            getAppState(NiftyAppState.class).goToScreen(ScreenController_HUD.class, "cinematic");
            getAppState(NiftyAppState.class).goToScreen(ScreenController_Shop.class, "cinematic");
        }
        else{
            getAppState(IngameCameraAppState.class).restoreState();
            getAppState(MapObstaclesAppState.class).setDisplayObstacles(tmpAreObstaclesDisplayed);
            getAppState(PlayerAppState.class).getLockedCameraSystem().setEnabled(tmpWasLockedCameraEnabled);
            if(fogOfWarSystem != null){
                fogOfWarSystem.setDisplayMapSight(tmpWasDisplayMapSight);
            }
            getAppState(NiftyAppState.class).goToScreen(ScreenController_Cinematic.class, "start");
        }
    }
    
    private void setHUDAttachmentSystemsEnabled(boolean isEnabled){
        for(EntitySystem entitySystem : getAppState(LocalEntitySystemAppState.class).getEntitySystems()){
            if(entitySystem instanceof HUDAttachmentsSystem){
                HUDAttachmentsSystem hudAttachmentsSystem = (HUDAttachmentsSystem) entitySystem;
                hudAttachmentsSystem.setEnabled(isEnabled);
                break;
            }
        }
    }

    @Override
    public void onAction(String name, boolean isPressed, float lastTimePerFrame){
        if(name.equals("stop_cinematic") && isPressed){
            stopCinematic();
        }
    }

    public Cinematic getCurrentCinematic(){
        return currentCinematic;
    }

    public CameraNode getCameraNode(){
        return cameraNode;
    }
}
