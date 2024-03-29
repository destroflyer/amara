package amara.libraries.applications.display.ingame.models.util;

import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class SimpleParticleEmitter extends Node {

    public SimpleParticleEmitter(AssetManager assetManager) {
        particleEmitter = new ParticleEmitter("buff", ParticleMesh.Type.Triangle, 40);
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        material.setTexture("Texture", assetManager.loadTexture("Textures/effects/flame_additive.png"));
        particleEmitter.setMaterial(material);
        particleEmitter.setImagesX(2);
        particleEmitter.setImagesY(2);
        particleEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 4, 0));
        particleEmitter.getParticleInfluencer().setVelocityVariation(0.3f);
        particleEmitter.setGravity(Vector3f.ZERO);
        particleEmitter.setInWorldSpace(false);
        attachChild(particleEmitter);
    }
    protected ParticleEmitter particleEmitter;

    public ParticleEmitter getParticleEmitter() {
        return particleEmitter;
    }
}
