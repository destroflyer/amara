/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.models.modifiers;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.applications.display.models.*;

/**
 *
 * @author Carl
 */
public class ModelModifier_Daydream_Fire extends ModelModifier{

    @Override
    public void modify(RegisteredModel registeredModel){
        Node swordNode = registeredModel.requestBoneAttachmentsNode("sword");
        Spatial fire = MaterialFactory.getAssetManager().loadModel("Models/fireball/fireball.j3o");
        fire.setLocalTranslation(0, 2.5f, 0);
        fire.setLocalScale(0.8f, 2, 1);
        swordNode.attachChild(fire);
    }
}
