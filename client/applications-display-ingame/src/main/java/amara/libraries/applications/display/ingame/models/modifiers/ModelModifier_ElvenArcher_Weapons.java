package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.ModelSkin;
import amara.libraries.applications.display.models.RegisteredModel;
import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class ModelModifier_ElvenArcher_Weapons extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel, AssetManager assetManager) {
        // Quiver
        Node spineNode = registeredModel.requestBoneAttachmentsNode("RigSpine3");
        Node quiver = ModelSkin.get("Models/elven_archer_quiver/skin.xml").load(assetManager);
        quiver.setLocalTranslation(-2, 2, 0);
        JMonkeyUtil.setLocalRotation(quiver, new Vector3f(0, 0, -1));
        quiver.rotate(new Quaternion().fromAngleAxis(0.3f, Vector3f.UNIT_Z));
        quiver.setLocalScale(100);
        spineNode.attachChild(quiver);
        // Bow
        Node leftPalmNode = registeredModel.requestBoneAttachmentsNode("RigLPalm");
        Node bow = ModelSkin.get("Models/elven_archer_bow/skin.xml").load(assetManager);
        bow.setLocalTranslation(5, -2, 0);
        JMonkeyUtil.setLocalRotation(bow, new Vector3f(0, 0, -1));
        bow.setLocalScale(100);
        leftPalmNode.attachChild(bow);
    }
}
