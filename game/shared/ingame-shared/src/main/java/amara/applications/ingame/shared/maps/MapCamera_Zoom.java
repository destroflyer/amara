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

    public MapCamera_Zoom(float interval, float initialDistance, float minimumDistance, float maximumDistance){
        this.interval = interval;
        this.initialDistance = initialDistance;
        this.minimumDistance = minimumDistance;
        this.maximumDistance = maximumDistance;
    }
    private float interval;
    private float initialDistance;
    private float minimumDistance;
    private float maximumDistance;

    public float getInterval(){
        return interval;
    }

    public float getInitialDistance(){
        return initialDistance;
    }

    public float getMinimumDistance(){
        return minimumDistance;
    }

    public float getMaximumDistance(){
        return maximumDistance;
    }
}
