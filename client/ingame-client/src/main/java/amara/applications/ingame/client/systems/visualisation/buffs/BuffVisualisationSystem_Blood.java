package amara.applications.ingame.client.systems.visualisation.buffs;

import amara.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Spatial;

public class BuffVisualisationSystem_Blood extends BuffVisualisationSystem {

    public BuffVisualisationSystem_Blood(EntitySceneMap entitySceneMap, AssetManager assetManager) {
        super(entitySceneMap, assetManager, "blood");
    }

    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity) {
        ParticleEmitter particleEmitter = new ParticleEmitter("", ParticleMesh.Type.Triangle, 100);
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        material.setTexture("Texture", assetManager.loadTexture("Textures/effects/blood.png"));
        particleEmitter.setMaterial(material);
        particleEmitter.setImagesX(4);
        particleEmitter.setImagesY(4);
        particleEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 0, -0.1f));
        particleEmitter.getParticleInfluencer().setVelocityVariation(0);
        particleEmitter.setGravity(Vector3f.ZERO);
        particleEmitter.setParticlesPerSec(25);
        particleEmitter.setLowLife(0.5f);
        particleEmitter.setHighLife(0.5f);
        particleEmitter.setStartSize(1);
        particleEmitter.setEndSize(1);
        particleEmitter.setStartColor(new ColorRGBA(1, 1, 1, 1));
        particleEmitter.setEndColor(new ColorRGBA(1, 1, 1, 1));
        particleEmitter.setFacingVelocity(true);
        particleEmitter.setQueueBucket(RenderQueue.Bucket.Opaque);
        particleEmitter.getMaterial().getAdditionalRenderState().setDepthTest(false);
        particleEmitter.setUserData("layer", VISUALISATION_LAYER);
        // [jME 3.1 Master] Can somehow throw a multithreading error otherwise
        particleEmitter.updateLogicalState(10);
        return particleEmitter;
    }
}
