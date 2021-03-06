/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.cinematics.actions;

import com.jme3.cinematic.PlayState;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.scene.CameraNode;
import amara.libraries.applications.display.DisplayApplication;
import amara.libraries.applications.display.appstates.CinematicAppState;
import amara.libraries.applications.display.cinematics.CinematicAction;

/**
 *
 * @author Carl
 */
public class CameraPathAction extends CinematicAction{

    public CameraPathAction(MotionEvent motionEvent){
        this.motionEvent = motionEvent;
    }
    private MotionEvent motionEvent;

    @Override
    public void trigger(DisplayApplication displayApplication){
        CameraNode cameraNode = getCameraNode(displayApplication);
        cameraNode.setEnabled(true);
        motionEvent.setSpatial(cameraNode);
        cameraNode.addControl(motionEvent);
        motionEvent.play();
    }

    @Override
    public void finish(DisplayApplication displayApplication){
        super.finish(displayApplication);
        CameraNode cameraNode = getCameraNode(displayApplication);
        cameraNode.setEnabled(false);
        motionEvent.stop();
    }
    
    private CameraNode getCameraNode(DisplayApplication displayApplication){
        CinematicAppState cinematicAppState = displayApplication.getStateManager().getState(CinematicAppState.class);
        return cinematicAppState.getCameraNode();
    }

    @Override
    protected boolean isFinished(){
        return (motionEvent.getPlayState() == PlayState.Stopped);
    }
}
