/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.models.modifiers;

import com.jme3.animation.Bone;
import com.jme3.animation.SkeletonControl;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.Geometry;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.models.*;

/**
 *
 * @author Carl
 */
public class ModelModifier_Daydream_Envy extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel) {
        for (Geometry geometry : JMonkeyUtil.getAllGeometryChilds(registeredModel.getNode())) {
            geometry.setCullHint(Spatial.CullHint.Always);
        }
        SkeletonControl skeletonControl = registeredModel.getNode().getControl(SkeletonControl.class);
        Bone rootBone = skeletonControl.getSkeleton().getBone("root");
        addAttachment(registeredModel, rootBone);
    }

    private void addAttachment(RegisteredModel registeredModel, Bone bone) {
        Spatial minion = ModelSkin.get("Models/minion/skin_default.xml").load();
        minion.setLocalScale(0.6f);
        Node node = registeredModel.requestBoneAttachmentsNode(bone.getName());
        node.attachChild(minion);
        for (Bone childBone : bone.getChildren()) {
            addAttachment(registeredModel, childBone);
        }
    }
}
