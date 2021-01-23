package amara.libraries.applications.display.ingame.models.modifiers;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.models.*;

public class ModelModifier_Nathalya_Fire extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel, AssetManager assetManager) {
        Node swordNode = registeredModel.requestBoneAttachmentsNode("Bip01_R_Hand");
        Spatial fire = assetManager.loadModel("Models/fireball/fireball.j3o");
        fire.setLocalTranslation(12, 2, 87);
        fire.setLocalScale(10, 10, 16);
        JMonkeyUtil.setLocalRotation(fire, new Vector3f(1, 1, 4));
        swordNode.attachChild(fire);
    }
}
