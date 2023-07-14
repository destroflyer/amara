package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.RegisteredModel;
import com.destroflyer.jme3.effekseer.nativ.EffekseerControl;
import com.jme3.asset.AssetManager;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;

public class ModelModifier_Aland_Explosion extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel, AssetManager assetManager) {
        Node particleNode = new Node();
        particleNode.setShadowMode(RenderQueue.ShadowMode.Off);
        EffekseerControl effect = new EffekseerControl(assetManager, "Effekseer/explosion.efkefc");
        effect.setSpeed(10);
        particleNode.addControl(effect);
        registeredModel.getNode().attachChild(particleNode);
    }
}
