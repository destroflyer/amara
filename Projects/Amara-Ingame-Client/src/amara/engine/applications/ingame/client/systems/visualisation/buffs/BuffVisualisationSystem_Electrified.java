/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.material.Material;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.engine.applications.ingame.client.systems.visualisation.*;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.applications.display.models.ModelObject;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class BuffVisualisationSystem_Electrified extends BuffVisualisationSystem{

    public BuffVisualisationSystem_Electrified(EntitySceneMap entitySceneMap){
        super(entitySceneMap, "electrified");
        scaleVisualisation = false;
    }
    
    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int buffStatusEntity, int targetEntity){
        ModelObject modelObject = getModelObject(entitySceneMap.requestNode(targetEntity));
        Spatial clonedModel = modelObject.getModelSpatial().deepClone();
        Material material = MaterialFactory.getAssetManager().loadMaterial("Shaders/electricity/materials/electricity2.j3m");
        for(Geometry geometry : JMonkeyUtil.getAllGeometryChilds(clonedModel)){
            geometry.setMaterial(material);
            geometry.setQueueBucket(RenderQueue.Bucket.Transparent);
        }
        modelObject.registerModel(clonedModel);
        return clonedModel;
    }

    @Override
    protected void removeVisualAttachment(int entity, Node entityNode, Spatial visualAttachment){
        super.removeVisualAttachment(entity, entityNode, visualAttachment);
        ModelObject modelObject = getModelObject(entityNode);
        modelObject.unregisterModel(visualAttachment);
    }
    
    private ModelObject getModelObject(Node node){
        return (ModelObject) node.getChild(ModelSystem.NODE_NAME_MODEL);
    }
}
