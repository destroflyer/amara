package amara.libraries.applications.display.ingame.models.modifiers;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.libraries.applications.display.models.*;

public class ModelModifier_Daydream_Fire extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel, AssetManager assetManager) {
        Node swordNode = registeredModel.requestBoneAttachmentsNode("sword");
        Spatial fire = assetManager.loadModel("Models/fireball/fireball.j3o");
        fire.setLocalTranslation(0, 2.5f, 0);
        fire.setLocalScale(0.8f, 2, 1);
        swordNode.attachChild(fire);
    }
}
