package amara.libraries.applications.display.ingame.models.modifiers;

import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import amara.libraries.applications.display.models.*;

public class ModelModifier_Maria_Ult extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel, AssetManager assetManager) {
        ParticleEmitter particleEmitter = (ParticleEmitter) registeredModel.getNode().getChild(0);
        particleEmitter.updateLogicalState(10);
    }
}
