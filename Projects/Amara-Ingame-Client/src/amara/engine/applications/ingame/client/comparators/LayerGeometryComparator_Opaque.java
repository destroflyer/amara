/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.comparators;

import com.jme3.renderer.queue.OpaqueComparator;
import com.jme3.scene.Geometry;

/**
 *
 * @author Carl
 */
public class LayerGeometryComparator_Opaque extends OpaqueComparator{
    
    @Override
    public int compare(Geometry geometry1, Geometry geometry2){
        int layer1 = getLayer(geometry1);
        int layer2 = getLayer(geometry2);
        if(layer1 != layer2){
            return ((layer1 > layer2)?1:-1);
        }
        return super.compare(geometry1, geometry2);
    }
    
    private int getLayer(Geometry geometry){
        Integer layer = geometry.getUserData("layer");
        if(layer != null){
            return layer;
        }
        return 0;
    }
}
