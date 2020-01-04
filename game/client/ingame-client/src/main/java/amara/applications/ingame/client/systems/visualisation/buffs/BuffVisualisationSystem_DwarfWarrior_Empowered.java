/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation.buffs;

import amara.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.applications.ingame.client.systems.visualisation.ModelSystem;
import amara.libraries.applications.display.ingame.models.modifiers.ModelModifier_DwarfWarrior_Weapons;
import amara.libraries.applications.display.models.ModelObject;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.effect.ParticleEmitter;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Carl
 */
public class BuffVisualisationSystem_DwarfWarrior_Empowered extends BuffVisualisationSystem {

    public BuffVisualisationSystem_DwarfWarrior_Empowered(EntitySceneMap entitySceneMap) {
        super(entitySceneMap, "dwarf_warrior_empowered");
    }
    private final static String NODE_NAME_ATTACHMENT = "dwarfWarriorEmpoweredEffect";

    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity){
        SimpleParticleEmitter simpleParticleEmitter = new SimpleParticleEmitter();
        simpleParticleEmitter.setName(NODE_NAME_ATTACHMENT);
        ParticleEmitter particleEmitter = simpleParticleEmitter.getParticleEmitter();
        particleEmitter.setNumParticles(80);
        particleEmitter.setParticlesPerSec(25);
        particleEmitter.setLowLife(1);
        particleEmitter.setHighLife(1);
        particleEmitter.setStartSize(0.45f);
        particleEmitter.setEndSize(0.45f);
        particleEmitter.setStartColor(new ColorRGBA(0, 1, 1, 1));
        particleEmitter.setEndColor(new ColorRGBA(0, 0.3f, 1, 0.5f));
        particleEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 0, -0.5f));
        particleEmitter.getParticleInfluencer().setVelocityVariation(0.05f);
        particleEmitter.setQueueBucket(RenderQueue.Bucket.Opaque);
        particleEmitter.getMaterial().getAdditionalRenderState().setDepthTest(false);
        particleEmitter.setUserData("layer", VISUALISATION_LAYER);
        particleEmitter.updateLogicalState(10);
        requestAttachmentsNode(targetEntity).attachChild(simpleParticleEmitter);
        return null;
    }

    @Override
    protected void removeVisualAttachment(int targetEntity) {
        requestAttachmentsNode(targetEntity).detachChildNamed(NODE_NAME_ATTACHMENT);
    }

    private Node requestAttachmentsNode(int entity) {
        ModelObject modelObject = getModelObject(entity);
        Node boneAttachmentsNode =  modelObject.getOriginalRegisteredModel().requestBoneAttachmentsNode("RigRPalm");
        return (Node) boneAttachmentsNode.getChild(ModelModifier_DwarfWarrior_Weapons.NODE_NAME_HAMMER);
    }

    private ModelObject getModelObject(int entity) {
        Node node = entitySceneMap.requestNode(entity);
        return (ModelObject) node.getChild(ModelSystem.NODE_NAME_MODEL);
    }
}
