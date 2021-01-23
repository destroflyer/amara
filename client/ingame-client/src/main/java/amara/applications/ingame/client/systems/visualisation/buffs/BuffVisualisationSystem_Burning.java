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

public class BuffVisualisationSystem_Burning extends BuffVisualisationSystem {

    public BuffVisualisationSystem_Burning(EntitySceneMap entitySceneMap, AssetManager assetManager) {
        super(entitySceneMap, assetManager, "burning");
    }
    
    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity) {
        SimpleParticleEmitter simpleParticleEmitter = new SimpleParticleEmitter(assetManager);
        ParticleEmitter particleEmitter = simpleParticleEmitter.getParticleEmitter();
        particleEmitter.setParticlesPerSec(6);
        particleEmitter.setLowLife(0.65f);
        particleEmitter.setHighLife(0.65f);
        particleEmitter.setStartSize(2.5f);
        particleEmitter.setEndSize(0.1f);
        particleEmitter.setStartColor(new ColorRGBA(1, 0.5f, 0, 1));
        particleEmitter.setEndColor(new ColorRGBA(1, 0, 0, 1));
        particleEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
        particleEmitter.setQueueBucket(RenderQueue.Bucket.Opaque);
        particleEmitter.getMaterial().getAdditionalRenderState().setDepthTest(false);
        particleEmitter.setUserData("layer", VISUALISATION_LAYER);
        // [jME 3.1 Master] Can somehow throw a multithreading error otherwise
        particleEmitter.updateLogicalState(10);
        return simpleParticleEmitter;
    }
}
