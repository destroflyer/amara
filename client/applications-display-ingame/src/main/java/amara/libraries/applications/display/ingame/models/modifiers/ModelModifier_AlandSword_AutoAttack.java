
package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.RegisteredModel;
import com.jme3.math.FastMath;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

public class ModelModifier_AlandSword_AutoAttack extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel) {
        Spatial sword = registeredModel.getNode().getChild(0);
        sword.rotate(0, 0, FastMath.HALF_PI);
        registeredModel.getNode().addControl(new AbstractControl() {

            @Override
            protected void controlUpdate(float lastTimePerFrame) {
                getSpatial().rotate(lastTimePerFrame * (3 * FastMath.TWO_PI), 0, 0);
            }

            @Override
            protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

            }
        });
    }
}
