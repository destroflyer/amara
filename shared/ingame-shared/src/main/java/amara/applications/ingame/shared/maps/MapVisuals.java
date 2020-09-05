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
public class MapVisuals{
    
    private ArrayList<MapVisual> mapVisuals = new ArrayList<MapVisual>();
    
    public void addVisual(MapVisual mapVisual){
        mapVisuals.add(mapVisual);
    }
    
    public void removeVisual(MapVisual mapVisual){
        mapVisuals.remove(mapVisual);
    }

    public ArrayList<MapVisual> getMapVisuals(){
        return mapVisuals;
    }
}
