
package amara.libraries.applications.display.ingame.models.modifiers;

import amara.core.files.FileAssets;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.RegisteredModel;
import com.destroflyer.jme3.effekseer.model.ParticleEffectSettings;
import com.destroflyer.jme3.effekseer.reader.EffekseerReader;
import com.destroflyer.jme3.effekseer.renderer.EffekseerControl;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;

public class ModelModifier_Bubble_Glitter extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel) {
        // Explode
        Node particleNodeExplode = new Node();
        particleNodeExplode.setLocalTranslation(0, 2.5f, 0);
        particleNodeExplode.setLocalScale(0.5f);
        particleNodeExplode.setShadowMode(RenderQueue.ShadowMode.Off);
        particleNodeExplode.addControl(new EffekseerControl(
            new EffekseerReader().read(FileAssets.ROOT, FileAssets.ROOT + "Effekseer/bubble_explode.efkproj"),
            ParticleEffectSettings.builder()
                .loop(false)
                .frameLength(1f / 50)
                .build(),
            MaterialFactory.getAssetManager())
        );
        registeredModel.getNode().attachChild(particleNodeExplode);
        // Glitter
        Node particleNodeGlitter = new Node();
        particleNodeGlitter.setLocalTranslation(0, 2, 0);
        particleNodeGlitter.setLocalScale(0.5f, 0.1f, 0.5f);
        particleNodeGlitter.setShadowMode(RenderQueue.ShadowMode.Off);
        particleNodeGlitter.addControl(new AbstractControl() {

            private float passedTime;

            @Override
            protected void controlUpdate(float lastTimePerFrame) {
                passedTime += lastTimePerFrame;
                if (passedTime > 0.5f) {
                    spatial.addControl(new EffekseerControl(
                        new EffekseerReader().read(FileAssets.ROOT, FileAssets.ROOT + "Effekseer/bubble_glitter.efkproj"),
                        ParticleEffectSettings.builder()
                            .loop(false)
                            .frameLength(4.5f / 120)
                            .build(),
                        MaterialFactory.getAssetManager())
                    );
                    spatial.removeControl(this);
                }
            }

            @Override
            protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

            }
        });
        registeredModel.getNode().attachChild(particleNodeGlitter);
    }
}
