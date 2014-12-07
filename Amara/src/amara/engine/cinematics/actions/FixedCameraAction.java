/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.cinematics.actions;

import com.jme3.math.Vector3f;
import amara.engine.applications.DisplayApplication;
import amara.engine.cinematics.CinematicAction;

/**
 *
 * @author Carl
 */
public class FixedCameraAction extends CinematicAction{

    public FixedCameraAction(Vector3f location, Vector3f direction){
        this.location = location;
        this.direction = direction;
    }
    private Vector3f location;
    private Vector3f direction;
    
    @Override
    public void trigger(DisplayApplication displayApplication){
        displayApplication.getCamera().setLocation(location);
        displayApplication.getCamera().lookAtDirection(direction, Vector3f.UNIT_Y);
    }
}
