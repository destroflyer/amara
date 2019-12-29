/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.applications.ingame.client.systems.visualisation.*;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.applications.display.models.ModelObject;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class BuffVisualisationSystem_Slap extends BuffVisualisationSystem{

    public BuffVisualisationSystem_Slap(EntitySceneMap entitySceneMap){
        super(entitySceneMap, "slap");
    }
    private final static String NODE_NAME_ATTACHMENT = "slapEffect";
    
    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity){
        attachVisualisation(targetEntity, "hand.R");
        attachVisualisation(targetEntity, "hand.L");
        return null;
    }

    private void attachVisualisation(int entity, String boneName){
        Spatial fire = MaterialFactory.getAssetManager().loadModel("Models/fireball/fireball.j3o");
        fire.setName(NODE_NAME_ATTACHMENT);
        fire.setLocalScale(0.3f);
        requestBoneAttachmentsNode(entity, boneName).attachChild(fire);
    }

    @Override
    protected void removeVisualAttachment(int targetEntity) {
        detachVisualisation(targetEntity, "hand.R");
        detachVisualisation(targetEntity, "hand.L");
    }

    private void detachVisualisation(int entity, String boneName){
        requestBoneAttachmentsNode(entity, boneName).detachChildNamed(NODE_NAME_ATTACHMENT);
    }

    private Node requestBoneAttachmentsNode(int entity, String boneName) {
        ModelObject modelObject = getModelObject(entity);
        return modelObject.getOriginalRegisteredModel().requestBoneAttachmentsNode(boneName);
    }

    private ModelObject getModelObject(int entity) {
        Node node = entitySceneMap.requestNode(entity);
        return (ModelObject) node.getChild(ModelSystem.NODE_NAME_MODEL);
    }
}
