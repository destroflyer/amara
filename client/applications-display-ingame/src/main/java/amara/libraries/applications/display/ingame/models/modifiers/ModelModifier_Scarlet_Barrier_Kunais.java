/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.ModelObject;
import amara.libraries.applications.display.models.RegisteredModel;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author Carl
 */
public class ModelModifier_Scarlet_Barrier_Kunais extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel) {
        Node node = registeredModel.getNode();
        int kunaisCount = 6;
        for (int i = 0; i < kunaisCount; i++) {
            ModelObject kunai = new ModelObject(null, "Models/scarlet_kunai/skin.xml");
            final float speed = (2 * FastMath.PI);
            final float startTime = (i * ((FastMath.TWO_PI / speed) / kunaisCount));
            kunai.addControl(new AbstractControl() {

                private float time = startTime;
                private float x;
                private float z;
                private float radius = 2.5f;
                private Vector3f tmpRotation = new Vector3f();

                @Override
                protected void controlUpdate(float lastTimePerFrame) {
                    time += lastTimePerFrame;
                    x = (FastMath.sin(time * speed) * radius);
                    z = (FastMath.cos(time * speed) * radius);
                    spatial.setLocalTranslation(x, 0, z);
                    tmpRotation.set(x, 0, z);
                    spatial.setLocalRotation(new Quaternion().fromAngleAxis((time - FastMath.QUARTER_PI) * speed, Vector3f.UNIT_Y));
                }

                @Override
                protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

                }
            });
            node.attachChild(kunai);
        }
    }
}
