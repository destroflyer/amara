/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Spatial;
import amara.engine.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.engine.materials.MaterialFactory;
import amara.game.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class BuffVisualisationSystem_Wither extends BuffVisualisationSystem{

    public BuffVisualisationSystem_Wither(EntitySceneMap entitySceneMap){
        super(entitySceneMap, "withered");
    }
    
    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int buffStatusEntity, int targetEntity){
        ParticleEmitter particleEmitter = new ParticleEmitter("buff", ParticleMesh.Type.Triangle, 30);
        Material material = new Material(MaterialFactory.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
        material.setTexture("Texture", MaterialFactory.getAssetManager().loadTexture("Textures/effects/flame_alpha.png"));
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        particleEmitter.setMaterial(material);
        particleEmitter.setImagesX(2);
        particleEmitter.setImagesY(2);
        particleEmitter.setSelectRandomImage(false);
        particleEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 3.5f, 0));
        particleEmitter.getParticleInfluencer().setVelocityVariation(0.3f);
        particleEmitter.setGravity(Vector3f.ZERO);
        particleEmitter.setParticlesPerSec(4);
        particleEmitter.setLowLife(3);
        particleEmitter.setHighLife(4);
        particleEmitter.setStartSize(2);
        particleEmitter.setEndSize(2);
        particleEmitter.setStartColor(new ColorRGBA(0, 0, 0, 1));
        particleEmitter.setEndColor(new ColorRGBA(0.2f, 0.2f, 0.2f, 0));
        particleEmitter.setRotateSpeed(2);
        particleEmitter.setInWorldSpace(false);
        particleEmitter.setQueueBucket(RenderQueue.Bucket.Opaque);
        particleEmitter.getMaterial().getAdditionalRenderState().setDepthTest(false);
        particleEmitter.setUserData("layer", 1);
        return particleEmitter;
    }
}
