/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.ModelSkin;
import amara.libraries.applications.display.models.RegisteredModel;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Carl
 */
public class ModelModifier_Scarlet_Gentleman extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel) {
        Node headNode = registeredModel.requestBoneAttachmentsNode("RigHead");
        // Moustache
        Spatial funnyMoustache = ModelSkin.get("Models/funny_moustache/skin.xml").load();
        funnyMoustache.setLocalTranslation(-12, -4, 0);
        funnyMoustache.setLocalScale(25, 25, 25);
        JMonkeyUtil.setLocalRotation(funnyMoustache, new Vector3f(-1, 0, 0));
        headNode.attachChild(funnyMoustache);
        // Hat
        Spatial gentlemanHat = ModelSkin.get("Models/gentleman_hat/skin.xml").load();
        gentlemanHat.setLocalTranslation(0, 20, 0);
        gentlemanHat.setLocalScale(60, 60, 60);
        JMonkeyUtil.setLocalRotation(gentlemanHat, new Vector3f(-1, 1, -1));
        headNode.attachChild(gentlemanHat);
    }
}
