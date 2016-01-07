/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.models.modifiers;

import com.jme3.animation.Bone;
import com.jme3.animation.SkeletonControl;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.Geometry;
import amara.engine.JMonkeyUtil;
import amara.engine.applications.ingame.client.models.*;

/**
 *
 * @author Carl
 */
public class ModelModifier_Daydream_Envy extends ModelModifier{

    @Override
    public void modify(ModelObject modelObject){
        for(Geometry geometry : JMonkeyUtil.getAllGeometryChilds(modelObject.getModelSpatial())){
            geometry.setCullHint(Spatial.CullHint.Always);
        }
        skeletonControl = modelObject.getModelSpatial().getControl(SkeletonControl.class);
        addAttachment(skeletonControl.getSkeleton().getBone("root"));
    }
    private SkeletonControl skeletonControl;
    
    private void addAttachment(Bone bone){
        Spatial minion = new ModelSkin("/Models/minion/skin_default.xml").loadSpatial();
        minion.setLocalScale(0.6f);
        Node node = skeletonControl.getAttachmentsNode(bone.getName());
        node.attachChild(minion);
        for(Bone childBone : bone.getChildren()){
            addAttachment(childBone);
        }
    }
}
