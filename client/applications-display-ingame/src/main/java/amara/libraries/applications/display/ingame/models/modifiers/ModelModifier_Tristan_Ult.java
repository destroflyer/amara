package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.ingame.models.util.GroundTextures;
import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.RegisteredModel;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.control.AbstractControl;

public class ModelModifier_Tristan_Ult extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel) {
        Geometry geometry = GroundTextures.create("Models/tristan_ult/resources/diffuse.png", -6, 6, 12, 6, RenderState.BlendMode.AlphaAdditive);
        geometry.addControl(new AbstractControl() {

            float alpha = 1;

            @Override
            protected void controlUpdate(float tpf) {
                alpha -= tpf;
                Geometry geometry = (Geometry) getSpatial();
                Material material = geometry.getMaterial();
                if (alpha > 0) {
                    material.setColor("Color", new ColorRGBA(1, 1, 1, alpha));
                } else {
                    geometry.getParent().detachChild(geometry);
                }
            }

            @Override
            protected void controlRender(RenderManager rm, ViewPort vp) {

            }
        });
        registeredModel.getNode().attachChild(geometry);
    }
}
