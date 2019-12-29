/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.models.modifiers;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.applications.display.models.*;

/**
 *
 * @author Carl
 */
public class ModelModifier_Nathalya_Fire extends ModelModifier{

    @Override
    public void modify(RegisteredModel registeredModel){
        Node swordNode = registeredModel.requestBoneAttachmentsNode("Bip01_R_Hand");
        Spatial fire = MaterialFactory.getAssetManager().loadModel("Models/fireball/fireball.j3o");
        fire.setLocalTranslation(12, 2, 87);
        fire.setLocalScale(10, 10, 16);
        JMonkeyUtil.setLocalRotation(fire, new Vector3f(1, 1, 4));
        swordNode.attachChild(fire);
    }
}
