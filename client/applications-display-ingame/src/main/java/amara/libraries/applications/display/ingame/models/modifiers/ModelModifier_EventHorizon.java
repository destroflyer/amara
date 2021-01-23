package amara.libraries.applications.display.ingame.models.modifiers;

import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.effect.shapes.EmitterPointShape;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.models.*;

public class ModelModifier_EventHorizon extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel, AssetManager assetManager) {
        float radius = 4.5f;
        for (int i = 0; i < 5; i++) {
            ParticleEmitter particleEmitter = new ParticleEmitter("", ParticleMesh.Type.Triangle, 40);
            Material material = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
            material.setTexture("Texture", assetManager.loadTexture("Textures/effects/flash_additive.png"));
            particleEmitter.setMaterial(material);
            particleEmitter.setImagesX(2);
            particleEmitter.setImagesY(2);
            particleEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 0, 2f));
            particleEmitter.getParticleInfluencer().setVelocityVariation(0);
            particleEmitter.setGravity(Vector3f.ZERO);
            particleEmitter.setInWorldSpace(false);
            particleEmitter.setParticlesPerSec(2);
            particleEmitter.setShape(new EmitterPointShape(new Vector3f(0, 0, 0)));
            particleEmitter.setLowLife(2.25f);
            particleEmitter.setHighLife(2.25f);
            particleEmitter.setStartSize(0.7f);
            particleEmitter.setEndSize(0.7f);
            particleEmitter.setStartColor(new ColorRGBA(1, 1, 1, 1));
            particleEmitter.setEndColor(new ColorRGBA(1, 1, 1, 0));
            particleEmitter.setFacingVelocity(true);
            particleEmitter.setQueueBucket(RenderQueue.Bucket.Opaque);
            particleEmitter.getMaterial().getAdditionalRenderState().setDepthTest(false);
            particleEmitter.setUserData("layer", 5);
            particleEmitter.updateLogicalState(10);
            float progress = ((i + 0.5f) * (FastMath.TWO_PI / 5));
            float x = (FastMath.sin(progress) * radius);
            float z = (FastMath.cos(progress) * radius);
            Vector2f direction2f = new Vector2f(1, 0);
            direction2f.rotateAroundOrigin(progress, true);
            Vector3f direction3f = new Vector3f(direction2f.getX(), 0, direction2f.getY());
            particleEmitter.setLocalTranslation(new Vector3f(x, 0, z).subtractLocal(direction3f.mult(radius / 2)));
            JMonkeyUtil.setLocalRotation(particleEmitter, direction3f);
            registeredModel.getNode().attachChild(particleEmitter);
        }
    }
}
