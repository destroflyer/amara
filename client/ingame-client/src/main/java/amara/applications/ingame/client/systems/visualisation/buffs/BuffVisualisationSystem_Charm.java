package amara.applications.ingame.client.systems.visualisation.buffs;

import amara.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Spatial;

public class BuffVisualisationSystem_Charm extends BuffVisualisationSystem {

    public BuffVisualisationSystem_Charm(EntitySceneMap entitySceneMap, AssetManager assetManager) {
        super(entitySceneMap, assetManager, "charm");
    }

    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity) {
        ParticleEmitter particleEmitter = new ParticleEmitter("", ParticleMesh.Type.Triangle, 40);
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        material.setTexture("Texture", assetManager.loadTexture("Textures/effects/heart.png"));
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        particleEmitter.setMaterial(material);
        particleEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 0, 3));
        particleEmitter.getParticleInfluencer().setVelocityVariation(1);
        particleEmitter.setGravity(Vector3f.ZERO);
        particleEmitter.setParticlesPerSec(5);
        particleEmitter.setLowLife(1);
        particleEmitter.setHighLife(1);
        particleEmitter.setStartSize(0.4f);
        particleEmitter.setEndSize(0.4f);
        particleEmitter.setStartColor(new ColorRGBA(1, 1, 1, 1));
        particleEmitter.setEndColor(new ColorRGBA(1, 1, 1, 0));
        particleEmitter.setQueueBucket(RenderQueue.Bucket.Opaque);
        particleEmitter.getMaterial().getAdditionalRenderState().setDepthTest(false);
        particleEmitter.setUserData("layer", 7);
        // [jME 3.1 Master] Can somehow throw a multithreading error otherwise
        particleEmitter.updateLogicalState(10);
        particleEmitter.setLocalTranslation(0, 4, 0);
        return particleEmitter;
    }
}
