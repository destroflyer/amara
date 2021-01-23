package amara.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.applications.ingame.client.systems.visualisation.*;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.models.ModelObject;
import amara.libraries.applications.display.models.RegisteredModel;
import amara.libraries.entitysystem.EntityWorld;

public class BuffVisualisationSystem_Electrified extends BuffVisualisationSystem {

    public BuffVisualisationSystem_Electrified(EntitySceneMap entitySceneMap, AssetManager assetManager) {
        super(entitySceneMap, assetManager, "electrified");
        scaleVisualisation = false;
    }
    private final static String NODE_NAME_CLONED_MODEL = "electrifiedModel";

    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity) {
        ModelObject modelObject = getModelObject(targetEntity);
        RegisteredModel clonedModel = modelObject.loadAndRegisterModel();
        clonedModel.getNode().setName(NODE_NAME_CLONED_MODEL);
        Material material = assetManager.loadMaterial("Shaders/electricity/materials/electricity2.j3m");
        for(Geometry geometry : JMonkeyUtil.getAllGeometryChilds(clonedModel.getNode())) {
            geometry.setMaterial(material);
            geometry.setQueueBucket(RenderQueue.Bucket.Transparent);
        }
        return null;
    }

    @Override
    protected void removeVisualAttachment(int entity) {
        ModelObject modelObject = getModelObject(entity);
        Spatial clonedModel = modelObject.getChild(NODE_NAME_CLONED_MODEL);
        modelObject.unregisterModel(clonedModel);
    }

    private ModelObject getModelObject(int entity) {
        Node node = entitySceneMap.requestNode(entity);
        return (ModelObject) node.getChild(ModelSystem.NODE_NAME_MODEL);
    }
}
