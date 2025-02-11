package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.ingame.models.util.FadeOutControl;
import amara.libraries.applications.display.ingame.models.util.GroundTextures;
import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.RegisteredModel;
import com.destroflyer.jme3.effekseer.nativ.EffekseerControl;
import com.jme3.asset.AssetManager;
import com.jme3.material.RenderState;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;

public class ModelModifier_Alice_Thunder extends ModelModifier {

    public ModelModifier_Alice_Thunder(float radius) {
        this.radius = radius;
    }
    private float radius;

    @Override
    public void modify(RegisteredModel registeredModel, AssetManager assetManager) {
        Geometry indicator = GroundTextures.create(assetManager, "Models/alice_thunder/resources/indicator.png", (-1 * radius), radius, (2 * radius), (2 * radius), RenderState.BlendMode.AlphaAdditive);
        indicator.addControl(new FadeOutControl(0.5f));
        registeredModel.getNode().attachChild(indicator);

        Node particleNode = new Node();
        particleNode.setLocalScale(0.2f);
        particleNode.setShadowMode(RenderQueue.ShadowMode.Off);
        particleNode.addControl(new AbstractControl() {

            @Override
            protected void controlUpdate(float lastTimePerFrame) {
                spatial.addControl(new EffekseerControl(assetManager, "Effekseer/lightning_strike.efkefc"));
                spatial.removeControl(this);
            }

            @Override
            protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

            }
        });
        registeredModel.getNode().attachChild(particleNode);
    }
}
