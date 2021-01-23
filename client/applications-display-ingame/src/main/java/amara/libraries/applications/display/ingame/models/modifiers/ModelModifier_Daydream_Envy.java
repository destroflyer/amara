package amara.libraries.applications.display.ingame.models.modifiers;

import com.jme3.animation.Bone;
import com.jme3.animation.SkeletonControl;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.Geometry;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.models.*;

public class ModelModifier_Daydream_Envy extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel, AssetManager assetManager) {
        for (Geometry geometry : JMonkeyUtil.getAllGeometryChilds(registeredModel.getNode())) {
            geometry.setCullHint(Spatial.CullHint.Always);
        }
        SkeletonControl skeletonControl = registeredModel.getNode().getControl(SkeletonControl.class);
        Bone rootBone = skeletonControl.getSkeleton().getBone("root");
        addAttachment(registeredModel, assetManager, rootBone);
    }

    private void addAttachment(RegisteredModel registeredModel, AssetManager assetManager, Bone bone) {
        Spatial minion = ModelSkin.get("Models/minion/skin_default.xml").load(assetManager);
        minion.setLocalScale(0.6f);
        Node node = registeredModel.requestBoneAttachmentsNode(bone.getName());
        node.attachChild(minion);
        for (Bone childBone : bone.getChildren()) {
            addAttachment(registeredModel, assetManager, childBone);
        }
    }
}
