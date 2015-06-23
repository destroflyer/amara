/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import java.util.HashMap;
import com.jme3.math.Vector3f;
import amara.engine.JMonkeyUtil;
import amara.engine.applications.ingame.client.models.ModelObject;

/**
 *
 * @author Carl
 */
public class EntityHeightMap{

    public EntityHeightMap(EntitySceneMap entitySceneMap){
        this.entitySceneMap = entitySceneMap;
    }
    private EntitySceneMap entitySceneMap;
    private HashMap<Integer, Vector3f> worldOffsets = new HashMap<Integer, Vector3f>();
    
    public Vector3f getWorldOffset(int entity){
        Vector3f worldOffset = worldOffsets.get(entity);
        if(worldOffset == null){
            ModelObject modelObject = (ModelObject) entitySceneMap.requestNode(entity).getChild(ModelSystem.NODE_NAME_MODEL);
            if(modelObject != null){
                Vector3f spatialDimension = JMonkeyUtil.getSpatialDimension(modelObject);
                worldOffset = new Vector3f(0, spatialDimension.getY() + 1, 0);
            }
            else{
                worldOffset = new Vector3f();
            }
            worldOffsets.put(entity, worldOffset);
        }
        return worldOffset;
    }
}
