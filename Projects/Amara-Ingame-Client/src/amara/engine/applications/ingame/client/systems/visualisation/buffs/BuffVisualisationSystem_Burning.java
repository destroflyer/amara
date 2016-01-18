/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.effect.ParticleEmitter;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Spatial;
import amara.engine.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class BuffVisualisationSystem_Burning extends BuffVisualisationSystem{

    public BuffVisualisationSystem_Burning(EntitySceneMap entitySceneMap){
        super(entitySceneMap, "burning");
    }
    
    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int buffStatusEntity, int targetEntity){
        SimpleParticleEmitter simpleParticleEmitter = new SimpleParticleEmitter();
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
        particleEmitter.setUserData("layer", 1);
        return simpleParticleEmitter;
    }
}
