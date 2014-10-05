/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.animation.AnimChannel;
import com.jme3.renderer.queue.RenderQueue;
import amara.engine.JMonkeyUtil;
import amara.engine.applications.ingame.client.models.ModelObject;
import amara.engine.applications.ingame.client.systems.visualisation.*;
import amara.engine.materials.MaterialFactory;
import amara.game.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class BuffVisualisationSystem_Zhonyas extends BuffVisualisationSystem{

    public BuffVisualisationSystem_Zhonyas(EntitySceneMap entitySceneMap){
        super(entitySceneMap, "zhonyas");
    }
    
    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int entity, int buffStatusEntity){
        ModelObject modelObject = getModelObject(entitySceneMap.requestNode(entity));
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
