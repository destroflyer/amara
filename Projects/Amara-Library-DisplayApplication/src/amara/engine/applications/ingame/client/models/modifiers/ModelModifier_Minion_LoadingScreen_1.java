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

/**
 *
 * @author Carl
 */
public class ModelModifier_Minion_LoadingScreen_1 extends ModelModifier{

    @Override
    public void modify(ModelObject modelObject){
        SkeletonControl skeletonControl = modelObject.getModelSpatial().getControl(SkeletonControl.class);
        Node headNode = skeletonControl.getAttachmentsNode("head");
        Spatial funnyMoustache = new ModelSkin("Models/funny_moustache/skin.xml").loadSpatial();
        funnyMoustache.setLocalTranslation(0, 0.05f, -0.65f);
        JMonkeyUtil.setLocalRotation(funnyMoustache, new Vector3f(0, 0, -1));
        headNode.attachChild(funnyMoustache);
        Spatial gentlemanHat = new ModelSkin("Models/gentleman_hat/skin.xml").loadSpatial();
        gentlemanHat.setLocalTranslation(0.2f, 0.95f, 0.3f);
        JMonkeyUtil.setLocalRotation(gentlemanHat, new Vector3f(-1, 1, -1));
        headNode.attachChild(gentlemanHat);
        Node rightHandNode = skeletonControl.getAttachmentsNode("hand.R");
        Spatial gentlemanHat2 = new ModelSkin("Models/gentleman_hat/skin.xml").loadSpatial();
        gentlemanHat2.setLocalTranslation(-0.45f, 0.65f, 0.25f);
        JMonkeyUtil.setLocalRotation(gentlemanHat2, new Vector3f(0, 8, -1));
        rightHandNode.attachChild(gentlemanHat2);
    }
}
