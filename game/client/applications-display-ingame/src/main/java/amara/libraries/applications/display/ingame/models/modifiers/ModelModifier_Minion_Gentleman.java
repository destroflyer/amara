/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.models.modifiers;

import com.jme3.animation.SkeletonControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.models.*;

/**
 *
 * @author Carl
 */
public class ModelModifier_Minion_Gentleman extends ModelModifier{

    @Override
    public void modify(ModelObject modelObject){
        SkeletonControl skeletonControl = modelObject.getModelSpatial().getControl(SkeletonControl.class);
        Node headNode = skeletonControl.getAttachmentsNode("head");
        Spatial funnyMoustache = ModelSkin.get("Models/funny_moustache/skin.xml").loadSpatial();
        funnyMoustache.setLocalTranslation(0, 0.05f, -0.65f);
        JMonkeyUtil.setLocalRotation(funnyMoustache, new Vector3f(0, 0, -1));
        headNode.attachChild(funnyMoustache);
        Spatial gentlemanHat = ModelSkin.get("Models/gentleman_hat/skin.xml").loadSpatial();
        gentlemanHat.setLocalTranslation(0.2f, 0.95f, 0.3f);
        JMonkeyUtil.setLocalRotation(gentlemanHat, new Vector3f(-1, 1, -1));
        headNode.attachChild(gentlemanHat);
    }
}
