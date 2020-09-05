package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.ModelSkin;
import amara.libraries.applications.display.models.RegisteredModel;
import com.jme3.math.FastMath;
import com.jme3.scene.Node;

public class ModelModifier_Scarlet_Weapons extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel) {
        addKunai(registeredModel, "RigLPalm");
        addKunai(registeredModel, "RigRPalm");
    }

    private void addKunai(RegisteredModel registeredModel, String boneName) {
        Node palmNode = registeredModel.requestBoneAttachmentsNode(boneName);
        Node kunai = ModelSkin.get("Models/scarlet_kunai/skin.xml").load();
        kunai.setLocalTranslation(-3, -8, -8);
        kunai.rotate(FastMath.PI, 0, FastMath.HALF_PI);
        kunai.setLocalScale(150);
        palmNode.attachChild(kunai);
    }
}
