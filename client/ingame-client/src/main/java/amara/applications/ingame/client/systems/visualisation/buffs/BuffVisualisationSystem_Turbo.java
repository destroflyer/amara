package amara.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Spatial;
import amara.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.libraries.entitysystem.EntityWorld;

public class BuffVisualisationSystem_Turbo extends BuffVisualisationSystem {

    public BuffVisualisationSystem_Turbo(EntitySceneMap entitySceneMap, AssetManager assetManager){
        super(entitySceneMap, assetManager, "turbo");
    }

    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity) {
        ParticleEmitter particleEmitter = new ParticleEmitter("", ParticleMesh.Type.Triangle, 40);
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        material.setTexture("Texture", assetManager.loadTexture("Textures/effects/slow_additive.png"));
        particleEmitter.setMaterial(material);
        particleEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 0, -1f));
        particleEmitter.getParticleInfluencer().setVelocityVariation(0);
        particleEmitter.setGravity(Vector3f.ZERO);
        particleEmitter.setParticlesPerSec(11);
        particleEmitter.setLowLife(1);
        particleEmitter.setHighLife(1);
        particleEmitter.setStartSize(0.6f);
        particleEmitter.setEndSize(0.6f);
        particleEmitter.setStartColor(new ColorRGBA(1, 0.5f, 0, 1));
        particleEmitter.setEndColor(new ColorRGBA(0.5f, 0, 0, 0));
        particleEmitter.setFacingVelocity(true);
        particleEmitter.setQueueBucket(RenderQueue.Bucket.Opaque);
        particleEmitter.getMaterial().getAdditionalRenderState().setDepthTest(false);
        particleEmitter.setUserData("layer", VISUALISATION_LAYER);
        // [jME 3.1 Master] Can somehow throw a multithreading error otherwise
        particleEmitter.updateLogicalState(10);
        return particleEmitter;
    }
}
