/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import amara.libraries.applications.display.materials.MaterialFactory;

/**
 *
 * @author Carl
 */
public class SimpleParticleEmitter extends Node{

    public SimpleParticleEmitter(){
        particleEmitter = new ParticleEmitter("buff", ParticleMesh.Type.Triangle, 40);
        Material material = new Material(MaterialFactory.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
        material.setTexture("Texture", MaterialFactory.getAssetManager().loadTexture("Textures/effects/flame_additive.png"));
        particleEmitter.setMaterial(material);
        particleEmitter.setImagesX(2);
        particleEmitter.setImagesY(2);
        particleEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 4, 0));
        particleEmitter.getParticleInfluencer().setVelocityVariation(0.3f);
        particleEmitter.setGravity(Vector3f.ZERO);
        particleEmitter.setInWorldSpace(false);
        attachChild(particleEmitter);
    }
    protected ParticleEmitter particleEmitter;
    
    public void emitAllParticles(){
        particleEmitter.emitAllParticles();
        particleEmitter.setParticlesPerSec(0);
    }

    public ParticleEmitter getParticleEmitter(){
        return particleEmitter;
    }
}
