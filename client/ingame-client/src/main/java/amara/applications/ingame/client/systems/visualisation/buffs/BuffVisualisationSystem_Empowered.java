/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation.buffs;

import amara.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.libraries.applications.display.ingame.models.util.SimpleParticleEmitter;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.effect.ParticleEmitter;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Spatial;

/**
 *
 * @author Carl
 */
public class BuffVisualisationSystem_Empowered extends BuffVisualisationSystem{

    public BuffVisualisationSystem_Empowered(EntitySceneMap entitySceneMap){
        super(entitySceneMap, "empowered");
    }
    
    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity){
        SimpleParticleEmitter simpleParticleEmitter = new SimpleParticleEmitter();
        ParticleEmitter particleEmitter = simpleParticleEmitter.getParticleEmitter();
        particleEmitter.setParticlesPerSec(10);
        particleEmitter.setLowLife(2.75f);
        particleEmitter.setHighLife(2.75f);
        particleEmitter.setStartSize(0.7f);
        particleEmitter.setEndSize(0.7f);
        particleEmitter.setStartColor(new ColorRGBA(1, 1, 0, 1));
        particleEmitter.setEndColor(new ColorRGBA(0.3f, 1, 0, 1));
        particleEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 1, 0));
        particleEmitter.getParticleInfluencer().setVelocityVariation(0.15f);
        particleEmitter.setQueueBucket(RenderQueue.Bucket.Opaque);
        particleEmitter.getMaterial().getAdditionalRenderState().setDepthTest(false);
        particleEmitter.setUserData("layer", VISUALISATION_LAYER);
        particleEmitter.updateLogicalState(10);
        particleEmitter.setLocalScale(2.5f);
        return simpleParticleEmitter;
    }
}
