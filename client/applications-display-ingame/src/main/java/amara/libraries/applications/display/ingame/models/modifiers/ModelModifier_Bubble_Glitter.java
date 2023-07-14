package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.RegisteredModel;
import com.destroflyer.jme3.effekseer.nativ.EffekseerControl;
import com.jme3.asset.AssetManager;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;

public class ModelModifier_Bubble_Glitter extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel, AssetManager assetManager) {
        // Explode
        Node particleNodeExplode = new Node();
        particleNodeExplode.setLocalTranslation(0, 2.5f, 0);
        particleNodeExplode.setLocalScale(0.5f);
        particleNodeExplode.setShadowMode(RenderQueue.ShadowMode.Off);
        EffekseerControl effect = new EffekseerControl(assetManager, "Effekseer/bubble_explode.efkefc");
        effect.setSpeed(1.25f);
        particleNodeExplode.addControl(effect);
        registeredModel.getNode().attachChild(particleNodeExplode);
        // Glitter
        Node particleNodeGlitter = new Node();
        particleNodeGlitter.setLocalTranslation(0, 2, 0);
        particleNodeGlitter.setLocalScale(0.5f, 0.05f, 0.5f);
        particleNodeGlitter.setShadowMode(RenderQueue.ShadowMode.Off);
        particleNodeGlitter.addControl(new AbstractControl() {

            private float passedTime;

            @Override
            protected void controlUpdate(float lastTimePerFrame) {
                passedTime += lastTimePerFrame;
                if (passedTime > 0.5f) {
                    EffekseerControl effect = new EffekseerControl(assetManager, "Effekseer/bubble_glitter.efkefc");
                    effect.setSpeed(0.62f);
                    spatial.addControl(effect);
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
