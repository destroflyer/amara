/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.cinematics.actions;

import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import amara.engine.applications.DisplayApplication;
import amara.engine.appstates.CinematicAppState;
import amara.engine.cinematics.CinematicAction;

/**
 *
 * @author Carl
 */
public class CameraPathAction extends CinematicAction{

    public CameraPathAction(MotionPath motionPath, float speed){
        this.motionPath = motionPath;
        this.speed = speed;
    }
    private MotionPath motionPath;
    private float speed;
    private MotionEvent motionEvent;
    private boolean hasPassedFirstWayPoint;

    @Override
    public void trigger(DisplayApplication displayApplication){
        CameraNode cameraNode = getCameraNode(displayApplication);
        cameraNode.setEnabled(true);
        motionEvent = new MotionEvent(cameraNode, motionPath);
        motionEvent.setSpeed(speed);
        motionEvent.setLookAt(new Vector3f(30, 10, 0), Vector3f.UNIT_Y);
        motionEvent.setDirectionType(MotionEvent.Direction.LookAt);
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
        int currentWayPoint = motionEvent.getCurrentWayPoint();
        if(currentWayPoint != 0){
            hasPassedFirstWayPoint = true;
        }
        return (hasPassedFirstWayPoint && (currentWayPoint == 0));
    }
}
