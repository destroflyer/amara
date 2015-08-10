/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.models.modifiers;

import com.jme3.animation.SkeletonControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.engine.JMonkeyUtil;
import amara.engine.applications.ingame.client.models.*;
import amara.engine.materials.MaterialFactory;

/**
 *
 * @author Carl
 */
public class ModelModifier_Nathalya_Fire extends ModelModifier{

    @Override
    public void modify(ModelObject modelObject){
        SkeletonControl skeletonControl = modelObject.getModelSpatial().getControl(SkeletonControl.class);
        Node swordNode = skeletonControl.getAttachmentsNode("Bip01_R_Hand");
        Spatial fire = MaterialFactory.getAssetManager().loadModel("Models/fireball/fireball.j3o");
        fire.setLocalTranslation(12, 2, 87);
        fire.setLocalScale(10, 10, 16);
        JMonkeyUtil.setLocalRotation(fire, new Vector3f(1, 1, 4));
        swordNode.attachChild(fire);
    }
}
