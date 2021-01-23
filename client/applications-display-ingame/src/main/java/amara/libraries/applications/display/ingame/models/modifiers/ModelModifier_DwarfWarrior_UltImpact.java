package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.ingame.models.util.FadeOutControl;
import amara.libraries.applications.display.ingame.models.util.GroundTextures;
import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.RegisteredModel;
import com.jme3.asset.AssetManager;
import com.jme3.material.RenderState;
import com.jme3.scene.Geometry;

public class ModelModifier_DwarfWarrior_UltImpact extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel, AssetManager assetManager) {
        Geometry geometry = GroundTextures.create(assetManager, "Models/dwarf_warrior_ult_impact/resources/diffuse.png", -6, 6, 12, 12, RenderState.BlendMode.Alpha);
        geometry.addControl(new FadeOutControl(1.5f));
        registeredModel.getNode().attachChild(geometry);
    }
}
