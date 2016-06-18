/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.animation.AnimChannel;
import com.jme3.renderer.queue.RenderQueue;
import amara.applications.ingame.client.systems.visualisation.*;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.applications.display.models.ModelObject;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class BuffVisualisationSystem_Golden_Eagle extends BuffVisualisationSystem{

    public BuffVisualisationSystem_Golden_Eagle(EntitySceneMap entitySceneMap){
        super(entitySceneMap, "golden_eagle");
        scaleVisualisation = false;
    }
    
    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity){
        ModelObject modelObject = getModelObject(entitySceneMap.requestNode(targetEntity));
        Spatial clonedModel = modelObject.getModelSpatial().deepClone();
        Material material = MaterialFactory.getAssetManager().loadMaterial("Shaders/glass/materials/glass_3.j3m");
        for(Geometry geometry : JMonkeyUtil.getAllGeometryChilds(clonedModel)){
            geometry.setMaterial(material);
            geometry.setQueueBucket(RenderQueue.Bucket.Transparent);
        }
        AnimChannel animationChannel = modelObject.copyAnimation(clonedModel);
        animationChannel.setSpeed(0);
        modelObject.setCullHint(Spatial.CullHint.Always);
        return clonedModel;
    }

    @Override
    protected void removeVisualAttachment(int entity, Node entityNode, Spatial visualAttachment){
        super.removeVisualAttachment(entity, entityNode, visualAttachment);
        ModelObject modelObject = getModelObject(entityNode);
        modelObject.setCullHint(Spatial.CullHint.Inherit);
    }
    
    private ModelObject getModelObject(Node entityNode){
        return (ModelObject) entityNode.getChild(ModelSystem.NODE_NAME_MODEL);
    }
}
