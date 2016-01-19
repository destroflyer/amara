/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.maps;

import com.jme3.math.Vector2f;

/**
 *
 * @author Carl
 */
public class MapCamera_Limit{

    public MapCamera_Limit(Vector2f minimum, Vector2f maximum){
        this.minimum = minimum;
        this.maximum = maximum;
    }
    private Vector2f minimum;
    private Vector2f maximum;

    public Vector2f getMinimum(){
        return minimum;
    }

    public Vector2f getMaximum(){
        return maximum;
    }
}
