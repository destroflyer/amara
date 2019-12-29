/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.models.modifiers;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.models.*;

/**
 *
 * @author Carl
 */
public class ModelModifier_Minion_LoadingScreen_1 extends ModelModifier{

    @Override
    public void modify(RegisteredModel registeredModel){
        Node headNode = registeredModel.requestBoneAttachmentsNode("head");
        // Moustache
        Spatial funnyMoustache = ModelSkin.get("Models/funny_moustache/skin.xml").load();
        funnyMoustache.setLocalTranslation(0, 0.05f, -0.65f);
        JMonkeyUtil.setLocalRotation(funnyMoustache, new Vector3f(0, 0, -1));
        headNode.attachChild(funnyMoustache);
        // Hat 1
        Spatial gentlemanHat = ModelSkin.get("Models/gentleman_hat/skin.xml").load();
        gentlemanHat.setLocalTranslation(0.2f, 0.95f, 0.3f);
        JMonkeyUtil.setLocalRotation(gentlemanHat, new Vector3f(-1, 1, -1));
        headNode.attachChild(gentlemanHat);
        // Hat 2
        Node rightHandNode = registeredModel.requestBoneAttachmentsNode("hand.R");
        Spatial gentlemanHat2 = ModelSkin.get("Models/gentleman_hat/skin.xml").load();
        gentlemanHat2.setLocalTranslation(-0.45f, 0.65f, 0.25f);
        JMonkeyUtil.setLocalRotation(gentlemanHat2, new Vector3f(0, 8, -1));
        rightHandNode.attachChild(gentlemanHat2);
    }
}
