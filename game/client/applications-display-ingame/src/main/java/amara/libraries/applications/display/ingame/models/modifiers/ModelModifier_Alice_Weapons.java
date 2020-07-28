package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.ModelSkin;
import amara.libraries.applications.display.models.RegisteredModel;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class ModelModifier_Alice_Weapons extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel) {
        // Wand
        Node rightPalmNode = registeredModel.requestBoneAttachmentsNode("RigRPalm");
        Node wand = ModelSkin.get("Models/alice_wand/skin.xml").load();
        wand.setLocalTranslation(8, -3, 0);
        JMonkeyUtil.setLocalRotation(wand, new Vector3f(0, 0, 1));
        wand.setLocalScale(100);
        rightPalmNode.attachChild(wand);
    }
}
