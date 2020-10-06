package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.ingame.models.util.FadeOutControl;
import amara.libraries.applications.display.ingame.models.util.GroundTextures;
import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.RegisteredModel;
import com.jme3.material.RenderState;
import com.jme3.scene.Geometry;

public class ModelModifier_Garmon_Ult_Impact extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel) {
        Geometry geometry = GroundTextures.create("Models/garmon_ult_impact/resources/diffuse.png", -3, 3, 6, 6, RenderState.BlendMode.AlphaAdditive);
        geometry.addControl(new FadeOutControl(1));
        registeredModel.getNode().attachChild(geometry);
    }
}
