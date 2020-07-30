package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.ingame.models.util.GroundTextures;
import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.RegisteredModel;
import com.jme3.material.RenderState;

public class ModelModifier_Tristan_Spin extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel) {
        registeredModel.getNode().attachChild(GroundTextures.create("Models/tristan_spin/resources/diffuse.png", -6, 6, 12, 12, RenderState.BlendMode.AlphaAdditive));
    }
}
