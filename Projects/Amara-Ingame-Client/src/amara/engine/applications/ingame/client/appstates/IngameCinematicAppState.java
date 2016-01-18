/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.appstates;

import com.jme3.input.controls.ActionListener;
import amara.engine.applications.ingame.client.IngameClientApplication;
import amara.engine.applications.ingame.client.gui.*;
import amara.engine.applications.ingame.client.systems.filters.FogOfWarSystem;
import amara.engine.applications.ingame.client.systems.visualisation.HUDAttachmentsSystem;
import amara.libraries.applications.display.appstates.*;
import amara.libraries.applications.display.ingame.appstates.*;
import amara.libraries.entitysystem.EntitySystem;

/**
 *
 * @author Carl
 */
public class IngameCinematicAppState extends CinematicAppState<IngameClientApplication> implements ActionListener{

    public IngameCinematicAppState(){
        
    }
    private boolean tmpAreObstaclesDisplayed;
    private boolean tmpWasLockedCameraEnabled;
    private boolean tmpWasDisplayMapSight;
    
    @Override
    public void setCinematicModeEnabled(boolean isEnabled){
        super.setCinematicModeEnabled(isEnabled);
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
}
