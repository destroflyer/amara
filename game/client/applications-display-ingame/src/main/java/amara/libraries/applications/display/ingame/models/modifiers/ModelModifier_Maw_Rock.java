/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.ModelObject;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author Carl
 */
public class ModelModifier_Maw_Rock extends ModelModifier {

    @Override
    public void modify(ModelObject modelObject) {
        Spatial model = modelObject.getModelSpatial();
        model.addControl(new AbstractControl() {

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
