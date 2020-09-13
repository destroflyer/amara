package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.ingame.models.util.SimpleParticleEmitter;
import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.RegisteredModel;
import com.jme3.effect.ParticleEmitter;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;

public class ModelModifier_ElvenArcher_BigArrow extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel) {
        SimpleParticleEmitter simpleParticleEmitter = new SimpleParticleEmitter();
        ParticleEmitter particleEmitter = simpleParticleEmitter.getParticleEmitter();
        particleEmitter.setNumParticles(300);
        particleEmitter.setParticlesPerSec(100);
        particleEmitter.setLowLife(1);
        particleEmitter.setHighLife(1);
        particleEmitter.setStartSize(0.05f);
        particleEmitter.setEndSize(0.05f);
        particleEmitter.setStartColor(new ColorRGBA(1, 0.4f, 0, 1));
        particleEmitter.setEndColor(new ColorRGBA(0.8f, 0.2f, 0, 0));
        particleEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 0, -1));
        particleEmitter.getParticleInfluencer().setVelocityVariation(0.03f);
        particleEmitter.setQueueBucket(RenderQueue.Bucket.Opaque);
        particleEmitter.getMaterial().getAdditionalRenderState().setDepthTest(false);
        particleEmitter.setUserData("layer", 3);
        particleEmitter.updateLogicalState(10);
        simpleParticleEmitter.setLocalTranslation(0, 0, 0.35f);
        registeredModel.getNode().attachChild(simpleParticleEmitter);
    }
}
