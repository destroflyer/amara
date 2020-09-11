package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.ingame.models.util.GroundTextures;
import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.RegisteredModel;
import com.jme3.material.RenderState;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;

public class ModelModifier_Tristan_Spin extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel) {
        Geometry geometry = GroundTextures.create("Models/tristan_spin/resources/diffuse.png", -6, 6, 12, 12, RenderState.BlendMode.AlphaAdditive);
        Node geometryNode = new Node();
        geometryNode.addControl(new AbstractControl() {

            private float angle = FastMath.TWO_PI;

            @Override
            protected void controlUpdate(float tpf) {
                angle -= ((FastMath.TWO_PI / 0.3f) * tpf);
                getSpatial().setLocalRotation(new Quaternion().fromAngleAxis(angle, Vector3f.UNIT_Y));
            }

            @Override
            protected void controlRender(RenderManager rm, ViewPort vp) {

            }
        });
        geometryNode.attachChild(geometry);
        registeredModel.getNode().attachChild(geometryNode);
    }
}
