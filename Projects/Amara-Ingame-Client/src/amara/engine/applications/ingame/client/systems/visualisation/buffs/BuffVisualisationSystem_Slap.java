/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.animation.SkeletonControl;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.engine.applications.ingame.client.models.ModelObject;
import amara.engine.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.engine.applications.ingame.client.systems.visualisation.ModelSystem;
import amara.engine.materials.MaterialFactory;
import amara.game.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class BuffVisualisationSystem_Slap extends BuffVisualisationSystem{

    public BuffVisualisationSystem_Slap(EntitySceneMap entitySceneMap){
        super(entitySceneMap, "slap");
    }
    
    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int buffStatusEntity, int targetEntity){
        ModelObject modelObject = getModelObject(entitySceneMap.requestNode(targetEntity));
        SkeletonControl skeletonControl = modelObject.getModelSpatial().getControl(SkeletonControl.class);
        attachVisualisation(skeletonControl.getAttachmentsNode("hand.R"), buffStatusEntity, targetEntity);
        attachVisualisation(skeletonControl.getAttachmentsNode("hand.L"), buffStatusEntity, targetEntity);
        return null;
    }
    
    private void attachVisualisation(Node boneAttachmentNode, int buffStatusEntity, int targetEntity){
        Node node = new Node();
        Spatial fire = MaterialFactory.getAssetManager().loadModel("Models/fireball/fireball.j3o");
        fire.setLocalScale(0.3f);
        node.attachChild(fire);
        boneAttachmentNode.attachChild(node);
        prepareVisualAttachment(buffStatusEntity, targetEntity, node);
    }
    
    private ModelObject getModelObject(Node node){
        return (ModelObject) node.getChild(ModelSystem.NODE_NAME_MODEL);
    }
}
