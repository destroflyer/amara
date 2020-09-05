/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.maps.lights;

/**
 *
 * @author Carl
 */
public class MapLight_Directional_Shadows{

    public MapLight_Directional_Shadows(float intensity){
        this.intensity = intensity;
    }
    private float intensity;

    public float getIntensity(){
        return intensity;
    }
}
