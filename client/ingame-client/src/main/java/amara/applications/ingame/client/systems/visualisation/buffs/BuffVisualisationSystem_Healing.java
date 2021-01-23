package amara.applications.ingame.client.systems.visualisation.buffs;

import amara.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.libraries.applications.display.ingame.models.util.SimpleParticleEmitter;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Spatial;

public class BuffVisualisationSystem_Healing extends BuffVisualisationSystem {

    public BuffVisualisationSystem_Healing(EntitySceneMap entitySceneMap, AssetManager assetManager) {
        super(entitySceneMap, assetManager, "healing");
    }

    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity) {
        SimpleParticleEmitter simpleParticleEmitter = new SimpleParticleEmitter(assetManager);
        ParticleEmitter particleEmitter = simpleParticleEmitter.getParticleEmitter();
        particleEmitter.setParticlesPerSec(4);
        particleEmitter.setLowLife(0.5f);
        particleEmitter.setHighLife(0.5f);
        particleEmitter.setStartSize(1.8f);
        particleEmitter.setEndSize(0.5f);
        particleEmitter.setStartColor(new ColorRGBA(0.5f, 1, 0, 1));
        particleEmitter.setEndColor(new ColorRGBA(0, 1, 0, 1));
        particleEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 0, 0));
        particleEmitter.setQueueBucket(RenderQueue.Bucket.Opaque);
        particleEmitter.getMaterial().getAdditionalRenderState().setDepthTest(false);
        particleEmitter.setUserData("layer", VISUALISATION_LAYER);
        // [jME 3.1 Master] Can somehow throw a multithreading error otherwise
        particleEmitter.updateLogicalState(10);
        return simpleParticleEmitter;
    }
}
