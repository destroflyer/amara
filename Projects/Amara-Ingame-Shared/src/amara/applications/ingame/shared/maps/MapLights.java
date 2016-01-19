/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.maps;

import java.util.ArrayList;

/**
 *
 * @author Carl
 */
public class MapLights{
    
    private ArrayList<MapLight> mapLights = new ArrayList<MapLight>();
    
    public void addLight(MapLight mapLight){
        mapLights.add(mapLight);
    }
    
    public void removeVisual(MapLight mapLight){
        mapLights.remove(mapLight);
    }

    public ArrayList<MapLight> getMapLights(){
        return mapLights;
    }
}
