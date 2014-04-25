/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.maps;

/**
 *
 * @author Carl
 */
public class MapCamera_Zoom{

    public MapCamera_Zoom(float interval, int initialLevel, int maximumLevel){
        this.interval = interval;
        this.initialLevel = initialLevel;
        this.maximumLevel = maximumLevel;
    }
    private float interval;
    private int initialLevel;
    private int maximumLevel;

    public float getInterval(){
        return interval;
    }

    public int getInitialLevel(){
        return initialLevel;
    }

    public int getMaximumLevel(){
        return maximumLevel;
    }
}
