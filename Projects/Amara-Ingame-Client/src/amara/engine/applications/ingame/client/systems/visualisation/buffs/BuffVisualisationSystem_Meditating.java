/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation.buffs;

import java.util.HashMap;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.engine.JMonkeyUtil;
import amara.engine.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.engine.materials.MaterialFactory;
import amara.game.entitysystem.EntityWorld;
import amara.libraries.emitter.*;
import amara.libraries.emitter.influencers.*;

/**
 *
 * @author Carl
 */
public class BuffVisualisationSystem_Meditating extends BuffVisualisationSystem{

    public BuffVisualisationSystem_Meditating(EntitySceneMap entitySceneMap){
        super(entitySceneMap, "meditating");
    }
    private HashMap<Integer, MeditatingParticleSystem> particleSystems = new HashMap<Integer, MeditatingParticleSystem>();

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        super.update(entityWorld, deltaSeconds);
        for(MeditatingParticleSystem particleSystem : particleSystems.values()){
            particleSystem.update(deltaSeconds);
        }
    }
    
    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int buffStatusEntity, int targetEntity){
        MeditatingParticleSystem particleSystem = new MeditatingParticleSystem();
        particleSystems.put(targetEntity, particleSystem);
        return particleSystem.getRootNode();
    }

    @Override
    protected void removeVisualAttachment(int entity, Node entityNode, Spatial visualAttachment){
        super.removeVisualAttachment(entity, entityNode, visualAttachment);
        particleSystems.remove(entity);
    }
    
    private class MeditatingParticleSystem{

        public MeditatingParticleSystem(){
            rootNode = new Node();
            rootNode.rotate(JMonkeyUtil.getQuaternion_X(90));
            rootNode.setLocalScale(1.5f);

            Emitter emitter1 = new Emitter();
            emitter1.setName("e1");
            emitter1.setMaxParticles(6);
            emitter1.addInfluencers(
                new GravityInfluencer(),
                new ColorInfluencer(),
                new AlphaInfluencer(),
                new SizeInfluencer(),
                new RotationInfluencer()
            );
            emitter1.setShapeSimpleEmitter();
            //e1.setDirectionType(DirectionType.Normal);
            //e1.initParticles(ParticleDataTriMesh.class, null);
            emitter1.setEmitterTestMode(false, false);
            emitter1.setSprite("Textures/effects/halo_additive.png");
            emitter1.setEmissionsPerSecond(2);
            emitter1.setParticlesPerEmission(1);
            emitter1.setForce(0.01f);
            emitter1.setLifeMinMax(1.25f,1.75f);
            emitter1.setBillboardMode(Emitter.BillboardMode.Velocity);
            emitter1.setDirectionType(EmitterMesh.DirectionType.Normal);
            emitter1.setUseRandomEmissionPoint(true);
            emitter1.setUseVelocityStretching(false);

            emitter1.getInfluencer(GravityInfluencer.class).setGravity(new Vector3f(0f,0f,0f));

            emitter1.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(1f,1f,1f,1f));
            emitter1.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(0.75f,1f,0.75f,1f));
            emitter1.getInfluencer(ColorInfluencer.class).setEnabled(true);

            emitter1.getInfluencer(AlphaInfluencer.class).addAlpha(0, Interpolation.exp5Out);
            emitter1.getInfluencer(AlphaInfluencer.class).addAlpha(0.5f, Interpolation.exp5In);
            emitter1.getInfluencer(AlphaInfluencer.class).addAlpha(0, Interpolation.exp5In);

            emitter1.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(.05f,.05f,.05f));
            emitter1.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(2f,2f,.5f));
            emitter1.getInfluencer(SizeInfluencer.class).setEnabled(true);

            emitter1.getInfluencer(RotationInfluencer.class).setUseRandomStartRotation(false, false, true);
            emitter1.getInfluencer(RotationInfluencer.class).addRotationSpeed(new Vector3f(0, 0, 0.25f));
            emitter1.getInfluencer(RotationInfluencer.class).setUseRandomDirection(true);

            emitter1.setLocalRotation(emitter1.getLocalRotation().fromAngleAxis(90*FastMath.DEG_TO_RAD, Vector3f.UNIT_X));
            emitter1.setLocalTranslation(0, 0.01f, 0);
            emitter1.setLocalScale(0.05f);
            addEmitter(emitter1);

            Emitter emitter2 = new Emitter();
            emitter2.setName("e2");
            emitter2.setMaxParticles(40);
            emitter2.addInfluencers(
                new GravityInfluencer(),
                new ColorInfluencer(),
                new AlphaInfluencer(),
                new SizeInfluencer()
            );
            emitter2.setShapeSimpleEmitter();
            emitter2.setDirectionType(EmitterMesh.DirectionType.RandomTangent);
            //e2.initParticles(ParticleDataTriMesh.class, null);
            emitter2.setEmitterTestMode(false, false);
            emitter2.setSprite("Textures/effects/smoke_additive.png", 2, 2);
            emitter2.setEmissionsPerSecond(35);
            emitter2.setParticlesPerEmission(1);
            emitter2.setForce(.25f);
            emitter2.setLifeMinMax(.5f,1.3f);
            emitter2.setBillboardMode(Emitter.BillboardMode.Velocity_Z_Up_Y_Left);
            emitter2.setUseRandomEmissionPoint(false);
            emitter2.setUseVelocityStretching(false);

            emitter2.getInfluencer(GravityInfluencer.class).setGravity(new Vector3f(0f,0f,0f));

            emitter2.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(0.25f,1,0.25f,1f));
            emitter2.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(0.75f,1,0.75f,1f));
            emitter2.getInfluencer(ColorInfluencer.class).setEnabled(true);

            emitter2.getInfluencer(AlphaInfluencer.class).addAlpha(0, Interpolation.exp5In);
            emitter2.getInfluencer(AlphaInfluencer.class).addAlpha(0.1f, Interpolation.exp5In);
            emitter2.getInfluencer(AlphaInfluencer.class).addAlpha(0, Interpolation.exp5In);

            emitter2.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(.05f,.05f,.05f));
            emitter2.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(2f,2f,.5f));
            emitter2.getInfluencer(SizeInfluencer.class).setEnabled(true);

            emitter2.getParticleNode().setLocalRotation(emitter2.getParticleNode().getLocalRotation().fromAngleAxis(90*FastMath.DEG_TO_RAD, Vector3f.UNIT_X));
            addEmitter(emitter2);

            emitter3 = new Emitter();
            emitter3.setName("e3");
            emitter3.setMaxParticles(10);
            emitter3.addInfluencers(
                new GravityInfluencer(),
                new ColorInfluencer(),
                new AlphaInfluencer(),
                new SizeInfluencer(),
                new RotationInfluencer(),
                new SpriteInfluencer()
            );
            emitter3.setShapeSimpleEmitter();
            emitter3.setDirectionType(EmitterMesh.DirectionType.Normal);
            //e3.initParticles(ParticleDataTriMesh.class, null);
            emitter3.setEmitterTestMode(false, false);
            emitter3.setSprite("Textures/effects/flash_additive.png", 2, 2);
            emitter3.setEmissionsPerSecond(8);
            emitter3.setParticlesPerEmission(1);
            emitter3.setForce(2.5f);
            emitter3.setLifeMinMax(1f,1f);
            emitter3.setBillboardMode(Emitter.BillboardMode.Camera);
            emitter3.setUseRandomEmissionPoint(false);
            emitter3.setUseVelocityStretching(false);

            emitter3.getInfluencer(GravityInfluencer.class).setGravity(new Vector3f(0f,1f,0f));
            emitter3.getInfluencer(GravityInfluencer.class).setAlignment(GravityInfluencer.GravityAlignment.Emission_Point);
            emitter3.getInfluencer(GravityInfluencer.class).setMagnitude(2);

            emitter3.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(0.25f,0.25f,0.25f,1f));
            emitter3.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(1f,1f,1f,1f));
            emitter3.getInfluencer(ColorInfluencer.class).setEnabled(true);

            emitter3.getInfluencer(AlphaInfluencer.class).addAlpha(0f, Interpolation.exp5Out);
            emitter3.getInfluencer(AlphaInfluencer.class).addAlpha(0.65f, Interpolation.exp5In);
            emitter3.getInfluencer(AlphaInfluencer.class).addAlpha(0.25f, Interpolation.linear);
            emitter3.getInfluencer(AlphaInfluencer.class).setEnabled(true);

            emitter3.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(.15f,.15f,.15f));
            emitter3.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(.015f,.015f,.015f));
            emitter3.getInfluencer(SizeInfluencer.class).setEnabled(true);

            emitter3.getInfluencer(RotationInfluencer.class).setUseRandomStartRotation(false, false, true);
            emitter3.getInfluencer(RotationInfluencer.class).setUseRandomDirection(true);
            emitter3.getInfluencer(RotationInfluencer.class).addRotationSpeed(new Vector3f(0, 0, 10));

            emitter3.getInfluencer(SpriteInfluencer.class).setAnimate(false);
            emitter3.getInfluencer(SpriteInfluencer.class).setUseRandomStartImage(true);
            addEmitter(emitter3);


            emitter4 = new Emitter();
            emitter4.setName("e4");
            emitter4.setMaxParticles(10);
            emitter4.addInfluencers(
                new GravityInfluencer(),
                new ColorInfluencer(),
                new AlphaInfluencer(),
                new SizeInfluencer(),
                new RotationInfluencer(),
                new SpriteInfluencer()
            );
            emitter4.setShapeSimpleEmitter();
            emitter4.setDirectionType(EmitterMesh.DirectionType.NormalNegate);
            //e4.initParticles(ParticleDataTriMesh.class, null);
            emitter4.setEmitterTestMode(false, false);
            emitter4.setSprite("Textures/effects/flash_additive.png", 2, 2);
            emitter4.setEmissionsPerSecond(8);
            emitter4.setParticlesPerEmission(1);
            emitter4.setForce(2.5f);
            emitter4.setLifeMinMax(1f,1f);
            emitter4.setBillboardMode(Emitter.BillboardMode.Camera);
            emitter4.setUseRandomEmissionPoint(false);
            emitter4.setUseVelocityStretching(false);

            emitter4.getInfluencer(GravityInfluencer.class).setGravity(new Vector3f(0f,1f,0f));
            emitter4.getInfluencer(GravityInfluencer.class).setAlignment(GravityInfluencer.GravityAlignment.Emission_Point);
            emitter4.getInfluencer(GravityInfluencer.class).setMagnitude(2);

            emitter4.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(0.25f,0.25f,0.25f,1f));
            emitter4.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(1f,1f,1f,1f));
            emitter4.getInfluencer(ColorInfluencer.class).setEnabled(true);

            emitter4.getInfluencer(AlphaInfluencer.class).addAlpha(0f, Interpolation.exp5Out);
            emitter4.getInfluencer(AlphaInfluencer.class).addAlpha(0.65f, Interpolation.exp5In);
            emitter4.getInfluencer(AlphaInfluencer.class).addAlpha(0.25f, Interpolation.linear);
            emitter4.getInfluencer(AlphaInfluencer.class).setEnabled(true);

            emitter4.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(.15f,.15f,.15f));
            emitter4.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(.015f,.015f,.015f));
            emitter4.getInfluencer(SizeInfluencer.class).setEnabled(true);

            emitter4.getInfluencer(RotationInfluencer.class).setUseRandomStartRotation(false, false, true);
            emitter4.getInfluencer(RotationInfluencer.class).setUseRandomDirection(true);
            emitter4.getInfluencer(RotationInfluencer.class).addRotationSpeed(new Vector3f(0, 0, 10));

            emitter4.getInfluencer(SpriteInfluencer.class).setAnimate(false);
            emitter4.getInfluencer(SpriteInfluencer.class).setUseRandomStartImage(true);
            addEmitter(emitter4);

            Emitter emitter5 = new Emitter();
            emitter5.setName("e5");
            emitter5.setMaxParticles(70);
            emitter5.addInfluencers(
                new GravityInfluencer(),
                new ColorInfluencer(),
                new AlphaInfluencer(),
                new SizeInfluencer()
            );
            emitter5.setShapeSimpleEmitter();
            emitter5.setDirectionType(EmitterMesh.DirectionType.RandomTangent);
            //e5.initParticles(ParticleDataTriMesh.class, null);
            emitter5.setEmitterTestMode(false, false);
            emitter5.setSprite("Textures/effects/glow_additive.png");
            emitter5.setEmissionsPerSecond(60);
            emitter5.setParticlesPerEmission(1);
            emitter5.setForce(1.25f);
            emitter5.setLifeMinMax(1.15f,1.15f);
            emitter5.setBillboardMode(Emitter.BillboardMode.Velocity_Z_Up_Y_Left);
            emitter5.setUseRandomEmissionPoint(false);
            emitter5.setUseVelocityStretching(false);
            emitter5.setVelocityStretchFactor(3.65f);

            emitter5.getInfluencer(GravityInfluencer.class).setGravity(new Vector3f(0f,0f,0f));
            emitter5.getInfluencer(GravityInfluencer.class).setAlignment(GravityInfluencer.GravityAlignment.Emission_Point);
            emitter5.getInfluencer(GravityInfluencer.class).setMagnitude(1.5f);

            emitter5.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(0.75f,0.75f,1f,1f));
            emitter5.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(0.75f,0.75f,1f,1f));
            emitter5.getInfluencer(ColorInfluencer.class).setEnabled(true);

            emitter5.getInfluencer(AlphaInfluencer.class).addAlpha(0f, Interpolation.exp5Out);
            emitter5.getInfluencer(AlphaInfluencer.class).addAlpha(0.35f, Interpolation.linear);
            emitter5.getInfluencer(AlphaInfluencer.class).addAlpha(0f, Interpolation.exp5In);
            //e5.getInfluencer(AlphaInfluencer.class).addAlpha(.2f, Interpolation.exp5In);

            emitter5.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(.015f,.015f,.015f));
            emitter5.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(.015f,.015f,.015f));
            emitter5.getInfluencer(SizeInfluencer.class).setEnabled(true);

            emitter5.getParticleNode().setLocalRotation(emitter5.getParticleNode().getLocalRotation().fromAngleAxis(90*FastMath.DEG_TO_RAD, Vector3f.UNIT_X));
            addEmitter(emitter5);
        }
        private Node rootNode;
        private Emitter emitter3;
        private Emitter emitter4;
        private float rotation;
    
        private void addEmitter(Emitter emitter){
            emitter.initialize(MaterialFactory.getAssetManager());
            rootNode.addControl(emitter);
            emitter.getParticleNode().setShadowMode(RenderQueue.ShadowMode.Off);
            emitter.setEnabled(true);
            for(Geometry geometry : JMonkeyUtil.getAllGeometryChilds(emitter.getParticleNode())){
                geometry.getMaterial().getAdditionalRenderState().setDepthTest(false);
                geometry.setUserData("layer", 1);
            }
        }
    
        public void update(float lastTimePerFrame){
            rotation += (lastTimePerFrame * 2);
            emitter3.setLocalRotation(emitter3.getLocalRotation().fromAngleAxis(rotation, Vector3f.UNIT_Z));
            emitter4.setLocalRotation(emitter4.getLocalRotation().fromAngleAxis(rotation, Vector3f.UNIT_Z));
        }

        public Node getRootNode(){
            return rootNode;
        }
    }
}
