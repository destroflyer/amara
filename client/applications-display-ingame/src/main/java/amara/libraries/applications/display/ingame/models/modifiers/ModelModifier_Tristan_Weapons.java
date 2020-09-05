package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.ModelSkin;
import amara.libraries.applications.display.models.RegisteredModel;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class ModelModifier_Tristan_Weapons extends ModelModifier {

    public static final String NODE_NAME_SWORD = "tristanSword";

    @Override
    public void modify(RegisteredModel registeredModel) {
        // Shield
        Node leftPalmNode = registeredModel.requestBoneAttachmentsNode("RigLPalm");
        Node shield = ModelSkin.get("Models/tristan_shield/skin.xml").load();
        shield.setLocalTranslation(8, 3, 2);
        shield.rotate(new Quaternion().fromAngleAxis(FastMath.PI, Vector3f.UNIT_Y));
        shield.rotate(new Quaternion().fromAngleAxis(0.3f, Vector3f.UNIT_Z));
        shield.setLocalScale(100);
        leftPalmNode.attachChild(shield);
        // Sword
        Node rightPalmNode = registeredModel.requestBoneAttachmentsNode("RigRPalm");
        Node sword = ModelSkin.get("Models/tristan_sword/skin.xml").load();
        sword.setName(NODE_NAME_SWORD);
        sword.setLocalTranslation(8, -8, 2);
        JMonkeyUtil.setLocalRotation(sword, new Vector3f(0, 0, 1));
        sword.setLocalScale(100);
        rightPalmNode.attachChild(sword);
    }
}
