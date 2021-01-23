package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.ModelObject;
import amara.libraries.applications.display.models.RegisteredModel;
import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;

public class ModelModifier_Scarlet_Ult_Blades extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel, AssetManager assetManager) {
        Node node = registeredModel.getNode();
        Node bladesNodes = new Node();
        for (int i = 0; i < 3; i++) {
            ModelObject blade = new ModelObject(assetManager, "Models/scarlet_blade/skin.xml");
            blade.setLocalTranslation((i - 1) * 1.5f, 7, -0.5f);
            JMonkeyUtil.setLocalRotation(blade, new Vector3f(0, 1, -1.2f));
            blade.setLocalScale(3.5f);
            bladesNodes.attachChild(blade);
        }
        bladesNodes.addControl(new AbstractControl() {

            private float zAngle = -1;

            @Override
            protected void controlUpdate(float lastTimePerFrame) {
                zAngle += (10 * lastTimePerFrame);
                if (zAngle < 0) {
                    spatial.setLocalRotation(new Quaternion().fromAngleAxis(zAngle, Vector3f.UNIT_X));
                } else {
                    spatial.removeControl(this);
                }
            }

            @Override
            protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

            }
        });
        node.attachChild(bladesNodes);
    }
}
