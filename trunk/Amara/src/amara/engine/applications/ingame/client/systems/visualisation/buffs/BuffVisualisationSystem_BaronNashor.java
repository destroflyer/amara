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
import amara.game.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class BuffVisualisationSystem_BaronNashor extends BuffVisualisationSystem{

    public BuffVisualisationSystem_BaronNashor(EntitySceneMap entitySceneMap){
        super(entitySceneMap, "baron_nashor");
    }
    
    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int buffStatusEntity, int targetEntity){
        SimpleParticleEmitter simpleParticleEmitter = new SimpleParticleEmitter();
        ParticleEmitter particleEmitter = simpleParticleEmitter.getParticleEmitter();
        particleEmitter.setParticlesPerSec(10);
        particleEmitter.setLowLife(4);
        particleEmitter.setHighLife(4);
        particleEmitter.setStartSize(0.7f);
        particleEmitter.setEndSize(0.7f);
        particleEmitter.setStartColor(new ColorRGBA(1, 0.3f, 0.85f, 1));
        particleEmitter.setEndColor(new ColorRGBA(1, 0, 0.85f, 1));
        particleEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 1, 0));
        particleEmitter.getParticleInfluencer().setVelocityVariation(0.15f);
        particleEmitter.setQueueBucket(RenderQueue.Bucket.Opaque);
        particleEmitter.getMaterial().getAdditionalRenderState().setDepthTest(false);
        particleEmitter.setUserData("layer", 1);
        particleEmitter.updateLogicalState(10);
        return simpleParticleEmitter;
    }
}
