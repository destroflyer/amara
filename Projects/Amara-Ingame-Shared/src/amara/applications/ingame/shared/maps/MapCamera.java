/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.maps;

import com.jme3.math.Vector3f;

/**
 *
 * @author Carl
 */
public class MapCamera{

    public MapCamera(Vector3f initialPosition, Vector3f initialDirection){
        this.initialPosition = initialPosition;
        this.initialDirection = initialDirection;
    }
    private Vector3f initialPosition;
    private Vector3f initialDirection;
    private MapCamera_Limit limit;
    private MapCamera_Zoom zoom;

    public Vector3f getInitialPosition(){
        return initialPosition;
    }

    public Vector3f getInitialDirection(){
        return initialDirection;
    }

    public void setLimit(MapCamera_Limit limit){
        this.limit = limit;
    }

    public MapCamera_Limit getLimit(){
        return limit;
    }

    public void setZoom(MapCamera_Zoom zoom){
        this.zoom = zoom;
    }

    public MapCamera_Zoom getZoom(){
        return zoom;
    }
}
