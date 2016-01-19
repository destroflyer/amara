/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.maps;

import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.math.Vector3f;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.cinematics.*;
import amara.libraries.applications.display.cinematics.actions.*;

/**
 *
 * @author Carl
 */
public class Map_Destroforest_CinematicIntro extends Cinematic{

    public Map_Destroforest_CinematicIntro(){
        super(new CinematicPart[]{
            //Jaime
            new CinematicPart(0, new CameraPathAction(new MotionEvent(){{
                setPath(new MotionPath(){{
                    addWayPoint(new Vector3f(98, 6, 78));
                    addWayPoint(new Vector3f(98, 6, 72));
                }});
                setRotation(JMonkeyUtil.getQuaternion_Y(0));
                setDirectionType(MotionEvent.Direction.Rotation);
                setSpeed(3.6f);
            }})),
            //Beetle Golems
            new CinematicPart(2.85f, new CameraPathAction(new MotionEvent(){{
                setPath(new MotionPath(){{
                    addWayPoint(new Vector3f(149, 8, 93));
                    addWayPoint(new Vector3f(153, 8, 103));
                }});
                setRotation(JMonkeyUtil.getQuaternion_Y(110));
                setDirectionType(Direction.Rotation);
                setSpeed(2.2f);
            }})),
            //Earth Elementals
            new CinematicPart(7.45f, new CameraPathAction(new MotionEvent(){{
                setPath(new MotionPath(){{
                    addWayPoint(new Vector3f(170, 8, 142));
                    addWayPoint(new Vector3f(130, 8, 136));
                }});
                setRotation(JMonkeyUtil.getQuaternion_Y(260));
                setDirectionType(Direction.Rotation);
                setSpeed(2);
            }})),
            //Wizards
            new CinematicPart(12.5f, new FixedCameraAction(new Vector3f(114, 12, 182), new Vector3f(1, -0.15f, 0))),
            //Dragon
            new CinematicPart(14.85f, new CameraPathAction(new MotionEvent(){{
                setPath(new MotionPath(){{
                    addWayPoint(new Vector3f(255-23, 14, 213-17));
                    addWayPoint(new Vector3f(255+0, 14, 213-25));
                    setCurveTension(0.83f);
                }});
                setDirectionType(MotionEvent.Direction.LookAt);
                setLookAt(new Vector3f(255, 14, 213), Vector3f.UNIT_Y);
                setSpeed(1.8f);
            }}))
        });
    }
}
