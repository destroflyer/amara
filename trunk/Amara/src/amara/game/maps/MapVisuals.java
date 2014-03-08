/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.maps;

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

    public ArrayList<MapVisual> getMapVisuals(){
        return mapVisuals;
    }
}
