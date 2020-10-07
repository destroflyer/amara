package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.ingame.models.util.FadeOutControl;
import amara.libraries.applications.display.ingame.models.util.GroundTextures;
import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.RegisteredModel;
import com.jme3.material.RenderState;
import com.jme3.scene.Geometry;

public class ModelModifier_Tristan_Ult extends ModelModifier {

    public ModelModifier_Tristan_Ult(float radius) {
        this.radius = radius;
    }
    private float radius;

    @Override
    public void modify(RegisteredModel registeredModel) {
        Geometry geometry = GroundTextures.create("Models/tristan_ult/resources/diffuse.png", (-1 * radius), radius, (2 * radius), radius, RenderState.BlendMode.AlphaAdditive);
        geometry.addControl(new FadeOutControl(1));
        registeredModel.getNode().attachChild(geometry);
    }
}
