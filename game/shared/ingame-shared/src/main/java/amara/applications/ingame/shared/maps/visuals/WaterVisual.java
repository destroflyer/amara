/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.maps.visuals;

import amara.applications.ingame.shared.maps.MapVisual;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector2f;

/**
 *
 * @author Carl
 */
public class WaterVisual extends MapVisual {

    public WaterVisual(Vector3f position, Vector2f size){
        this.position = position;
        this.size = size;
    }
    private Vector3f position;
    private Vector2f size;

    public Vector3f getPosition(){
        return position;
    }

    public Vector2f getSize(){
        return size;
    }
}
