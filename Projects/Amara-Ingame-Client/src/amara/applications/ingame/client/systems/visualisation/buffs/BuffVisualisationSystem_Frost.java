/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.effect.shapes.EmitterPointShape;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Spatial;
import amara.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class BuffVisualisationSystem_Frost extends BuffVisualisationSystem{

    public BuffVisualisationSystem_Frost(EntitySceneMap entitySceneMap){
        super(entitySceneMap, "frost");
    }
    
    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity){
        ParticleEmitter particleEmitter = new ParticleEmitter("", ParticleMesh.Type.Triangle, 100);
        Material material = new Material(MaterialFactory.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
        material.setTexture("Texture", MaterialFactory.getAssetManager().loadTexture("Textures/effects/frost_additive.png"));
        particleEmitter.setMaterial(material);
        particleEmitter.setImagesX(2);
        particleEmitter.setImagesY(2);
        particleEmitter.setSelectRandomImage(true);
        particleEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 0, -0.1f));
        particleEmitter.getParticleInfluencer().setVelocityVariation(0);
        particleEmitter.setGravity(Vector3f.ZERO);
        particleEmitter.setParticlesPerSec(25);
        particleEmitter.setShape(new EmitterPointShape(new Vector3f(0, 0, 0)));
        particleEmitter.setLowLife(1);
        particleEmitter.setHighLife(1);
        particleEmitter.setStartSize(0.8f);
        particleEmitter.setEndSize(0.8f);
        particleEmitter.setStartColor(new ColorRGBA(0, 0.5f, 1, 1));
        particleEmitter.setEndColor(new ColorRGBA(0, 0, 0.5f, 0));
        particleEmitter.setFacingVelocity(true);
        particleEmitter.setQueueBucket(RenderQueue.Bucket.Opaque);
        particleEmitter.getMaterial().getAdditionalRenderState().setDepthTest(false);
        particleEmitter.setUserData("layer", 1);
        //[jME 3.1 Master] Can somehow throw a multithreading error otherwise
        particleEmitter.updateLogicalState(10);
        return particleEmitter;
    }
}
