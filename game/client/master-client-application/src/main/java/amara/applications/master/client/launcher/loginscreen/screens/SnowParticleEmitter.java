/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.loginscreen.screens;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import amara.libraries.applications.display.materials.MaterialFactory;

/**
 *
 * @author Carl
 */
public class SnowParticleEmitter extends Node{
    
    public SnowParticleEmitter(){
        particleEmitter = new ParticleEmitter("", ParticleMesh.Type.Triangle, 80);
        Material material = new Material(MaterialFactory.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
        material.setTexture("Texture", MaterialFactory.getAssetManager().loadTexture("Textures/effects/flame_additive.png"));
        particleEmitter.setMaterial(material);
        particleEmitter.setImagesX(2);
        particleEmitter.setImagesY(2);
        particleEmitter.setNumParticles(180);
        particleEmitter.setParticlesPerSec(30);
        particleEmitter.setLowLife(10);
        particleEmitter.setHighLife(10);
        particleEmitter.setStartSize(0.2f);
        particleEmitter.setEndSize(0.1f);
        particleEmitter.setStartColor(new ColorRGBA(1, 1, 1, 1));
        particleEmitter.setEndColor(new ColorRGBA(1, 1, 1, 0));
        particleEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(1, 1, 1));
        particleEmitter.getParticleInfluencer().setVelocityVariation(1);
        particleEmitter.setGravity(0, 0.3f, 0);
        attachChild(particleEmitter);
    }
    private ParticleEmitter particleEmitter;
    
    public void emitAllParticles(){
        particleEmitter.emitAllParticles();
        particleEmitter.setParticlesPerSec(0);
    }
}
