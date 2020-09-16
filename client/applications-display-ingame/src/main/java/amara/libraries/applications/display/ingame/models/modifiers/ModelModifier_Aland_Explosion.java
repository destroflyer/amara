package amara.libraries.applications.display.ingame.models.modifiers;

import amara.core.files.FileAssets;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.RegisteredModel;
import com.destroflyer.jme3.effekseer.model.ParticleEffectSettings;
import com.destroflyer.jme3.effekseer.reader.EffekseerReader;
import com.destroflyer.jme3.effekseer.renderer.EffekseerControl;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;

public class ModelModifier_Aland_Explosion extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel) {
        Node particleNode = new Node();
        particleNode.setShadowMode(RenderQueue.ShadowMode.Off);
        particleNode.addControl(new EffekseerControl(
                new EffekseerReader().read(FileAssets.ROOT, FileAssets.ROOT + "Effekseer/explosion.efkproj"),
                ParticleEffectSettings.builder()
                        .loop(false)
                        .frameLength(1 / 502.5f)
                        .build(),
                MaterialFactory.getAssetManager())
        );
        registeredModel.getNode().attachChild(particleNode);
    }
}
