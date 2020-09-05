/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.ModelSkin;
import amara.libraries.applications.display.models.RegisteredModel;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author Carl
 */
public class ModelModifier_DwarfWarrior_Weapons extends ModelModifier {

    public static final String NODE_NAME_HAMMER = "dwarfWarriorHammer";

    @Override
    public void modify(RegisteredModel registeredModel) {
        // Shield
        Node spineNode = registeredModel.requestBoneAttachmentsNode("RigSpine3");
        Node shield = ModelSkin.get("Models/dwarf_warrior_shield/skin.xml").load();
        shield.setLocalTranslation(17, -6, 0);
        shield.rotate(new Quaternion().fromAngleAxis(FastMath.HALF_PI, Vector3f.UNIT_Z));
        shield.rotate(new Quaternion().fromAngleAxis(-1.5f, Vector3f.UNIT_Y));
        shield.setLocalScale(100);
        spineNode.attachChild(shield);
        // Axe
        Node leftPalmNode = registeredModel.requestBoneAttachmentsNode("RigLPalm");
        Node axe = ModelSkin.get("Models/dwarf_warrior_axe/skin.xml").load();
        axe.setLocalTranslation(4, -11, 2);
        JMonkeyUtil.setLocalRotation(axe, new Vector3f(0, 0, -1));
        axe.setLocalScale(100);
        leftPalmNode.attachChild(axe);
        // Hammer
        Node rightPalmNode = registeredModel.requestBoneAttachmentsNode("RigRPalm");
        Node hammer = ModelSkin.get("Models/dwarf_warrior_hammer/skin.xml").load();
        hammer.setName(NODE_NAME_HAMMER);
        hammer.setLocalTranslation(4, -11, 2);
        JMonkeyUtil.setLocalRotation(hammer, new Vector3f(0, 0, 1));
        hammer.setLocalScale(100);
        rightPalmNode.attachChild(hammer);
    }
}
