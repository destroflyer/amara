/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.maps;

/**
 *
 * @author Carl
 */
public class MapCamera_Zoom{

    public MapCamera_Zoom(float interval, float minimumDistance, float maximumDistance, float initialDistance){
        this.interval = interval;
        this.minimumDistance = minimumDistance;
        this.maximumDistance = maximumDistance;
        this.initialDistance = initialDistance;
    }
    private float interval;
    private float minimumDistance;
    private float maximumDistance;
    private float initialDistance;

    public float getInterval(){
        return interval;
    }

    public float getMinimumDistance(){
        return minimumDistance;
    }

    public float getMaximumDistance(){
        return maximumDistance;
    }

    public float getInitialDistance(){
        return initialDistance;
    }
}
