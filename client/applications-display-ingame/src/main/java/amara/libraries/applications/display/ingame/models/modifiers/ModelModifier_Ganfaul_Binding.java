package amara.libraries.applications.display.ingame.models.modifiers;

import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.libraries.applications.display.models.*;

public class ModelModifier_Ganfaul_Binding extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel, AssetManager assetManager) {
        Node node = registeredModel.getNode();
        Spatial particles = new ModelObject(assetManager, "Models/ganfaul_binding_particles/skin.xml");
        node.attachChild(particles);
        ParticleEmitter particleEmitter = (ParticleEmitter) node.getChild(0);
        particleEmitter.updateLogicalState(10);
    }
}
