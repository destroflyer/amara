/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.cinematics.actions;

import com.jme3.cinematic.PlayState;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.scene.CameraNode;
import amara.engine.applications.DisplayApplication;
import amara.engine.appstates.CinematicAppState;
import amara.engine.cinematics.CinematicAction;

/**
 *
 * @author Carl
 */
public class CameraPathAction extends CinematicAction{

    public CameraPathAction(MotionEvent motionEvent){
        this.motionEvent = motionEvent;
    }
    private MotionEvent motionEvent;
    private boolean hasPassedFirstWayPoint;

    @Override
    public void trigger(DisplayApplication displayApplication){
        CameraNode cameraNode = getCameraNode(displayApplication);
        cameraNode.setEnabled(true);
        motionEvent.setSpatial(cameraNode);
        cameraNode.addControl(motionEvent);
        motionEvent.play();
        hasPassedFirstWayPoint = false;
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
