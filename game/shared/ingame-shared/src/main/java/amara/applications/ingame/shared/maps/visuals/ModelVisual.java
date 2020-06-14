/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.maps.visuals;

import amara.applications.ingame.shared.maps.MapVisual;
import com.jme3.math.Vector3f;

/**
 *
 * @author Carl
 */
public class ModelVisual extends MapVisual {

    public ModelVisual(String modelSkinPath, Vector3f position, Vector3f direction, float scale){
        this.modelSkinPath = modelSkinPath;
        this.position = position;
        this.direction = direction;
        this.scale = scale;
    }
    private String modelSkinPath;
    private Vector3f position;
    private Vector3f direction;
    private float scale;

    public String getModelSkinPath(){
        return modelSkinPath;
    }

    public Vector3f getPosition(){
        return position;
    }

    public Vector3f getDirection(){
        return direction;
    }

    public float getScale(){
        return scale;
    }
}
