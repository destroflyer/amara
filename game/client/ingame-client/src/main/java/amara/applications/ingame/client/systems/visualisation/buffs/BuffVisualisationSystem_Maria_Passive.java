/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.effect.ParticleEmitter;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.applications.ingame.client.systems.visualisation.*;
import amara.libraries.applications.display.models.ModelObject;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class BuffVisualisationSystem_Maria_Passive extends BuffVisualisationSystem {

    public BuffVisualisationSystem_Maria_Passive(EntitySceneMap entitySceneMap) {
        super(entitySceneMap, "maria_passive");
    }
    private final static String NODE_NAME_ATTACHMENT = "mariaPassiveEffect";

    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity){
        SimpleParticleEmitter simpleParticleEmitter = new SimpleParticleEmitter();
        simpleParticleEmitter.setName(NODE_NAME_ATTACHMENT);
        ParticleEmitter particleEmitter = simpleParticleEmitter.getParticleEmitter();
        particleEmitter.setNumParticles(80);
        particleEmitter.setParticlesPerSec(25);
        particleEmitter.setLowLife(1);
        particleEmitter.setHighLife(1);
        particleEmitter.setStartSize(0.4f);
        particleEmitter.setEndSize(0.4f);
        particleEmitter.setStartColor(new ColorRGBA(1, 1, 0, 1));
        particleEmitter.setEndColor(new ColorRGBA(1, 1, 0.3f, 0.5f));
        particleEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(-1, -0.6f, 1.8f));
        particleEmitter.getParticleInfluencer().setVelocityVariation(0.05f);
        particleEmitter.setQueueBucket(RenderQueue.Bucket.Opaque);
        particleEmitter.getMaterial().getAdditionalRenderState().setDepthTest(false);
        particleEmitter.setUserData("layer", VISUALISATION_LAYER);
        particleEmitter.updateLogicalState(10);
        particleEmitter.setLocalScale(50);
        requestBoneAttachmentsNode(targetEntity).attachChild(simpleParticleEmitter);
        return null;
    }

    @Override
    protected void removeVisualAttachment(int targetEntity) {
        requestBoneAttachmentsNode(targetEntity).detachChildNamed(NODE_NAME_ATTACHMENT);
    }

    private Node requestBoneAttachmentsNode(int entity) {
        ModelObject modelObject = getModelObject(entity);
        return modelObject.getOriginalRegisteredModel().requestBoneAttachmentsNode("sword_joint");
    }

    private ModelObject getModelObject(int entity) {
        Node node = entitySceneMap.requestNode(entity);
        return (ModelObject) node.getChild(ModelSystem.NODE_NAME_MODEL);
    }
}
