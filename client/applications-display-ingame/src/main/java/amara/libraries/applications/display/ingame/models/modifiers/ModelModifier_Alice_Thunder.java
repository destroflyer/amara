package amara.libraries.applications.display.ingame.models.modifiers;

import amara.core.files.FileAssets;
import amara.libraries.applications.display.ingame.models.util.FadeOutControl;
import amara.libraries.applications.display.ingame.models.util.GroundTextures;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.RegisteredModel;
import com.destroflyer.jme3.effekseer.model.ParticleEffectSettings;
import com.destroflyer.jme3.effekseer.reader.EffekseerReader;
import com.destroflyer.jme3.effekseer.renderer.EffekseerControl;
import com.jme3.material.RenderState;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;

public class ModelModifier_Alice_Thunder extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel) {
        Geometry indicator = GroundTextures.create("Models/alice_thunder/resources/indicator.png", -2, 2, 4, 4, RenderState.BlendMode.AlphaAdditive);
        indicator.addControl(new FadeOutControl(0.5f));
        registeredModel.getNode().attachChild(indicator);

        Node particleNode = new Node();
        particleNode.setLocalScale(0.2f);
        particleNode.setShadowMode(RenderQueue.ShadowMode.Off);
        particleNode.addControl(new AbstractControl() {

            private float passedTime;

            @Override
            protected void controlUpdate(float lastTimePerFrame) {
                passedTime += lastTimePerFrame;
                // Lightning has to hit ground (frame 22) at 0.5 --> Start at 0.5 - (22 / 120) = 0.3625
                if (passedTime > 0.3625f) {
                    spatial.addControl(new EffekseerControl(
                            new EffekseerReader().read(FileAssets.ROOT, FileAssets.ROOT + "Effekseer/lightning_strike.efkproj"),
                            ParticleEffectSettings.builder()
                                    .loop(false)
                                    .frameLength(0.75f / 120)
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
        registeredModel.getNode().attachChild(particleNode);
    }
}
