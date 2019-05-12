/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.maps.lights;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import amara.applications.ingame.shared.maps.MapLight;

/**
 *
 * @author Carl
 */
public class MapLight_Directional extends MapLight{

    public MapLight_Directional(ColorRGBA color, Vector3f direction){
        super(color);
        this.direction = direction;
    }
    private Vector3f direction;
    private MapLight_Directional_Shadows shadows;

    public Vector3f getDirection(){
        return direction;
    }

    public void setShadows(MapLight_Directional_Shadows shadows){
        this.shadows = shadows;
    }

    public MapLight_Directional_Shadows getShadows(){
        return shadows;
    }
}
