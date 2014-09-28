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
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int entity, int buffStatusEntity){
        ModelObject modelObject = getModelObject(entitySceneMap.requestNode(entity));
        SkeletonControl skeletonControl = modelObject.getModelSpatial().getControl(SkeletonControl.class);
        attachVisualisation(skeletonControl.getAttachmentsNode("hand.R"), buffStatusEntity);
        attachVisualisation(skeletonControl.getAttachmentsNode("hand.L"), buffStatusEntity);
        return null;
    }
    
    private void attachVisualisation(Node boneAttachmentNode, int buffStatusEntity){
        Spatial fire = MaterialFactory.getAssetManager().loadModel("Models/fireball/fireball.j3o");
        fire.setLocalTranslation(0, 0.3f, 0);
        fire.setLocalScale(0.25f);
        boneAttachmentNode.attachChild(fire);
        prepareVisualAttachment(fire, buffStatusEntity);
    }
    
    private ModelObject getModelObject(Node node){
        return (ModelObject) node.getChild(ModelSystem.NODE_NAME_MODEL);
    }
}
