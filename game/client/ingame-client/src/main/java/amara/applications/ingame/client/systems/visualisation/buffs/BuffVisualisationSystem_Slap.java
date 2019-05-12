/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.animation.SkeletonControl;
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
    
    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity){
        ModelObject modelObject = getModelObject(entitySceneMap.requestNode(targetEntity));
        SkeletonControl skeletonControl = modelObject.getModelSpatial().getControl(SkeletonControl.class);
        attachVisualisation(skeletonControl.getAttachmentsNode("hand.R"), targetEntity);
        attachVisualisation(skeletonControl.getAttachmentsNode("hand.L"), targetEntity);
        return null;
    }
    
    private void attachVisualisation(Node boneAttachmentNode, int targetEntity){
        Node node = new Node();
        Spatial fire = MaterialFactory.getAssetManager().loadModel("Models/fireball/fireball.j3o");
        fire.setLocalScale(0.3f);
        node.attachChild(fire);
        boneAttachmentNode.attachChild(node);
        prepareVisualAttachment(targetEntity, node);
    }
    
    private ModelObject getModelObject(Node node){
        return (ModelObject) node.getChild(ModelSystem.NODE_NAME_MODEL);
    }
}
