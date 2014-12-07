/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.maps;

import com.jme3.cinematic.MotionPath;
import com.jme3.math.Vector3f;
import amara.engine.cinematics.*;
import amara.engine.cinematics.actions.*;

/**
 *
 * @author Carl
 */
public class TestMap_TestCinematic extends Cinematic{

    public TestMap_TestCinematic(){
        super(new CinematicPart[]{
            new CinematicPart(0, new FixedCameraAction(new Vector3f(30, 10, 0), new Vector3f(-0.5f, -0.3f, 0.6f))),
            new CinematicPart(2, new FixedCameraAction(new Vector3f(30, 10, 0), new Vector3f(-0.3f, -0.3f, 1))),
            new CinematicPart(4, new CameraPathAction(new MotionPath(){{
                addWayPoint(new Vector3f(20, 3, 0));
                addWayPoint(new Vector3f(0, 3, 20));
                addWayPoint(new Vector3f(-20, 3, 0));
                addWayPoint(new Vector3f(0, 3, -20));
                setCurveTension(0.83f);
            }}, 1))
        });
    }
}
