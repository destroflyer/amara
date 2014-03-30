/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.material.Material;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.Node;
import amara.engine.JMonkeyUtil;
import amara.engine.applications.ingame.client.models.ModelObject;
import amara.engine.applications.ingame.client.systems.visualisation.*;
import amara.engine.materials.MaterialFactory;
import amara.game.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class BuffVisualisationSystem_Electrified extends BuffVisualisationSystem{

    public BuffVisualisationSystem_Electrified(EntitySceneMap entitySceneMap){
        super(entitySceneMap, "electrified");
    }
    
    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int entity){
        Node node = new Node();
        ModelObject modelObject = (ModelObject) entitySceneMap.requestNode(entity).getChild(ModelSystem.NODE_NAME_MODEL);
        modelObject.applyModelTransformTo(node);
        Material material = MaterialFactory.getAssetManager().loadMaterial("Shaders/electricity/materials/electricity2.j3m");
        for(Geometry geometry : JMonkeyUtil.getAllGeometryChilds(modelObject)){
            Geometry electricity = new Geometry("", geometry.getMesh());
            electricity.setMaterial(material);
            electricity.setQueueBucket(RenderQueue.Bucket.Transparent);
            node.attachChild(electricity);
        }
        return node;
    }
}
