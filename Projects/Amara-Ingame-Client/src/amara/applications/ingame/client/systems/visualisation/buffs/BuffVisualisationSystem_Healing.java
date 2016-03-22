/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.effect.ParticleEmitter;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Spatial;
import amara.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class BuffVisualisationSystem_Healing extends BuffVisualisationSystem{

    public BuffVisualisationSystem_Healing(EntitySceneMap entitySceneMap){
        super(entitySceneMap, "healing");
    }
    
    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity){
        SimpleParticleEmitter simpleParticleEmitter = new SimpleParticleEmitter();
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
        particleEmitter.setUserData("layer", 1);
        //[jME 3.1 Master] Can somehow throw a multithreading error otherwise
        particleEmitter.updateLogicalState(10);
        return simpleParticleEmitter;
    }
}
