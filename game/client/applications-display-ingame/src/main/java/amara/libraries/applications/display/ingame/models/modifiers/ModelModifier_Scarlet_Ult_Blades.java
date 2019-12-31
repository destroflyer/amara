/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.ModelObject;
import amara.libraries.applications.display.models.RegisteredModel;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author Carl
 */
public class ModelModifier_Scarlet_Ult_Blades extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel) {
        Node node = registeredModel.getNode();
        for (int i = 0; i < 3; i++) {
            ModelObject blade = new ModelObject(null, "Models/scarlet_blade/skin.xml");
            blade.setLocalTranslation((i - 1) * 1.5f, 7, -0.5f);
            JMonkeyUtil.setLocalRotation(blade, new Vector3f(0, 1, -1.2f));
            blade.setLocalScale(3.5f);
            node.attachChild(blade);
        }
    }
}
