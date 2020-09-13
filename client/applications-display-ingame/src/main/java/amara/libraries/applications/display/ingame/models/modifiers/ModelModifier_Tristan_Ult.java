package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.ingame.models.util.FadeOutControl;
import amara.libraries.applications.display.ingame.models.util.GroundTextures;
import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.RegisteredModel;
import com.jme3.material.RenderState;
import com.jme3.scene.Geometry;

public class ModelModifier_Tristan_Ult extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel) {
        Geometry geometry = GroundTextures.create("Models/tristan_ult/resources/diffuse.png", -6, 6, 12, 6, RenderState.BlendMode.AlphaAdditive);
        geometry.addControl(new FadeOutControl(1));
        registeredModel.getNode().attachChild(geometry);
    }
}
