package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.RegisteredModel;
import com.jme3.asset.AssetManager;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

public class ModelModifier_Maw_Rock extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel, AssetManager assetManager) {
        registeredModel.getNode().addControl(new AbstractControl() {

            @Override
            protected void controlUpdate(float lastTimePerFrame) {
                getSpatial().rotate(JMonkeyUtil.getQuaternion_Y(180 * lastTimePerFrame));
            }

            @Override
            protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

            }
        });
    }
}
