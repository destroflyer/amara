/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.models.modifiers;

import com.jme3.animation.SkeletonControl;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.engine.applications.ingame.client.models.*;
import amara.engine.materials.MaterialFactory;

/**
 *
 * @author Carl
 */
public class ModelModifier_Daydream_Fire extends ModelModifier{

    @Override
    public void modify(ModelObject modelObject){
        SkeletonControl skeletonControl = modelObject.getModelSpatial().getControl(SkeletonControl.class);
        Node swordNode = skeletonControl.getAttachmentsNode("sword");
        Spatial fire = MaterialFactory.getAssetManager().loadModel("Models/fireball/fireball.j3o");
        fire.setLocalTranslation(0, 2.5f, 0);
        fire.setLocalScale(0.8f, 2, 1);
        swordNode.attachChild(fire);
    }
}
