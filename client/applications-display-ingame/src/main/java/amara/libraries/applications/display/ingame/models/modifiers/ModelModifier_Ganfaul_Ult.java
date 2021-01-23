package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.RegisteredModel;
import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.scene.Node;

public class ModelModifier_Ganfaul_Ult extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel, AssetManager assetManager) {
        Node node = registeredModel.getNode();
        ParticleEmitter particleEmitter1 = (ParticleEmitter) node.getChild(0);
        ParticleEmitter particleEmitter2 = (ParticleEmitter) node.getChild(1);
        particleEmitter1.updateLogicalState(10);
        particleEmitter2.updateLogicalState(10);
    }
}
