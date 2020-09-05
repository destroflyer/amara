/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.models.modifiers;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.applications.display.models.*;
import amara.libraries.emitter.*;
import amara.libraries.emitter.influencers.*;

/**
 *
 * @author Carl
 */
public class ModelModifier_Backport_Particles extends ModelModifier{

    private final float scale = 2.5f;
    
    @Override
    public void modify(RegisteredModel registeredModel){
        //Ring
        Emitter ring = new Emitter();
        ring.setName("ring");
        ring.setMaxParticles(6);
        ring.addInfluencers(
            new GravityInfluencer(),
            new ColorInfluencer(),
            new AlphaInfluencer(),
            new SizeInfluencer(),
            new RotationInfluencer()
        );
        ring.setShapeSimpleEmitter();
        ring.setEmitterTestMode(false, false);
        ring.setSprite("Textures/effects/halo_additive.png");
        ring.setEmissionsPerSecond(2);
        ring.setParticlesPerEmission(1);
        ring.setForce(0.01f);
        ring.setLifeMinMax(1.25f, 1.75f);
        ring.setBillboardMode(Emitter.BillboardMode.UNIT_Y);
        ring.setUseRandomEmissionPoint(false);
        ring.setUseVelocityStretching(false);
        ring.getInfluencer(GravityInfluencer.class).setGravity(new Vector3f(0, 0, 0));
        ring.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(0, 0, 1, 1));
        ring.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(0.75f, 0.75f, 1, 1));
        ring.getInfluencer(ColorInfluencer.class).setEnabled(true);
        ring.getInfluencer(AlphaInfluencer.class).addAlpha(0, Interpolation.exp5In);
        ring.getInfluencer(AlphaInfluencer.class).addAlpha(1, Interpolation.exp5Out);
        ring.getInfluencer(AlphaInfluencer.class).addAlpha(0, Interpolation.exp5In);
        ring.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(0.05f, 0.05f, 0.05f).multLocal(scale));
        ring.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(2, 2, 0.5f).multLocal(scale));
        ring.getInfluencer(SizeInfluencer.class).setEnabled(true);
        ring.getInfluencer(RotationInfluencer.class).setUseRandomStartRotation(false, false, true);
        ring.getInfluencer(RotationInfluencer.class).addRotationSpeed(new Vector3f(0, 0, 0.25f));
        ring.getInfluencer(RotationInfluencer.class).setUseRandomDirection(true);
        ring.setLocalTranslation(0, 0.01f, 0);
        ring.setLocalScale(0.5f);
        addEmitter(registeredModel, ring);
        //Emitter 1
        Emitter emitter1 = new Emitter();
        emitter1.setName("emitter1");
        emitter1.setMaxParticles(40);
        emitter1.addInfluencers(
            new GravityInfluencer(),
            new ColorInfluencer(),
            new AlphaInfluencer(),
            new SizeInfluencer()
        );
        emitter1.setShapeSimpleEmitter();
        emitter1.setDirectionType(EmitterMesh.DirectionType.RandomTangent);
        emitter1.setEmitterTestMode(false, false);
        emitter1.setSprite("Textures/effects/smoke_additive.png", 2, 2);
        emitter1.setEmissionsPerSecond(35);
        emitter1.setParticlesPerEmission(1);
        emitter1.setForce(0.25f);
        emitter1.setLifeMinMax(0.5f, 1.3f);
        emitter1.setBillboardMode(Emitter.BillboardMode.Velocity_Z_Up_Y_Left);
        emitter1.setUseRandomEmissionPoint(false);
        emitter1.setUseVelocityStretching(false);
        emitter1.getInfluencer(GravityInfluencer.class).setGravity(new Vector3f(0, 0, 0));
        emitter1.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(0.25f, 0.25f, 1, 1));
        emitter1.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(0.75f, 0.75f, 1, 1));
        emitter1.getInfluencer(ColorInfluencer.class).setEnabled(true);
        emitter1.getInfluencer(AlphaInfluencer.class).addAlpha(0, Interpolation.exp5In);
        emitter1.getInfluencer(AlphaInfluencer.class).addAlpha(0.1f, Interpolation.exp5In);
        emitter1.getInfluencer(AlphaInfluencer.class).addAlpha(0, Interpolation.exp5In);
        emitter1.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(0.05f, 0.05f, 0.05f).multLocal(scale));
        emitter1.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(2, 2, 0.5f).multLocal(scale));
        emitter1.getInfluencer(SizeInfluencer.class).setEnabled(true);
        addEmitter(registeredModel, emitter1);
        //Emitter 2
        Emitter emitter2 = new Emitter();
        emitter2.setName("emitter2");
        emitter2.setMaxParticles(70);
        emitter2.addInfluencers(
            new GravityInfluencer(),
            new ColorInfluencer(),
            new AlphaInfluencer(),
            new SizeInfluencer()
        );
        emitter2.setShapeSimpleEmitter();
        emitter2.setDirectionType(EmitterMesh.DirectionType.RandomTangent);
        emitter2.setEmitterTestMode(false, false);
        emitter2.setSprite("Textures/effects/glow_additive.png");
        emitter2.setEmissionsPerSecond(60);
        emitter2.setParticlesPerEmission(1);
        emitter2.setForce(1.25f);
        emitter2.setLifeMinMax(1.15f, 1.15f);
        emitter2.setBillboardMode(Emitter.BillboardMode.Velocity_Z_Up_Y_Left);
        emitter2.setUseRandomEmissionPoint(false);
        emitter2.setUseVelocityStretching(false);
        emitter2.setVelocityStretchFactor(3.65f);
        emitter2.getInfluencer(GravityInfluencer.class).setGravity(new Vector3f(0, 0, 0));
        emitter2.getInfluencer(GravityInfluencer.class).setAlignment(GravityInfluencer.GravityAlignment.Emission_Point);
        emitter2.getInfluencer(GravityInfluencer.class).setMagnitude(1.5f);
        emitter2.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(0.75f, 0.75f, 1, 1));
        emitter2.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(0.75f, 0.75f, 1, 1));
        emitter2.getInfluencer(ColorInfluencer.class).setEnabled(true);
        emitter2.getInfluencer(AlphaInfluencer.class).addAlpha(0, Interpolation.exp5Out);
        emitter2.getInfluencer(AlphaInfluencer.class).addAlpha(0.35f, Interpolation.linear);
        emitter2.getInfluencer(AlphaInfluencer.class).addAlpha(0, Interpolation.exp5In);
        emitter2.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(0.015f, 0.015f, 0.015f).multLocal(scale));
        emitter2.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(0.015f, 0.015f, 0.015f).multLocal(scale));
        emitter2.getInfluencer(SizeInfluencer.class).setEnabled(true);
        addEmitter(registeredModel, emitter2);
        //Water Spout
        Emitter waterSpout = new Emitter();
        waterSpout.setName("waterSpout");
        waterSpout.setMaxParticles(110);
        waterSpout.addInfluencers(
            new GravityInfluencer(),
            new ColorInfluencer(),
            new AlphaInfluencer(),
            new SizeInfluencer(),
            new SpriteInfluencer(),
            new RotationInfluencer()
        );
        Node circle = (Node) MaterialFactory.getAssetManager().loadModel("Models/shapes/circle.j3o");
        Mesh circleMesh = ((Geometry) circle.getChild(0)).getMesh();
        waterSpout.setShape(circleMesh);
        waterSpout.setDirectionType(EmitterMesh.DirectionType.Normal);
        waterSpout.setEmitterTestMode(false, false);
        waterSpout.setSprite("Textures/effects/smoke_additive.png", 2, 2);
        waterSpout.setEmissionsPerSecond(100);
        waterSpout.setParticlesPerEmission(1);
        waterSpout.setForceMinMax(2.25f, 3.75f);
        waterSpout.setLifeMinMax(1.15f, 1.15f);
        waterSpout.setBillboardMode(Emitter.BillboardMode.Velocity_Z_Up);
        waterSpout.setUseRandomEmissionPoint(false);
        waterSpout.setUseVelocityStretching(true);
        waterSpout.setVelocityStretchFactor(3.65f);
        waterSpout.getInfluencer(GravityInfluencer.class).setGravity(new Vector3f(0, 0, 0));
        waterSpout.getInfluencer(GravityInfluencer.class).setAlignment(GravityInfluencer.GravityAlignment.Emission_Point);
        waterSpout.getInfluencer(GravityInfluencer.class).setMagnitude(1.5f);
        waterSpout.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(0.75f, 0.75f, 1, 1));
        waterSpout.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(0.45f, 0.45f, 1, 1));
        waterSpout.getInfluencer(ColorInfluencer.class).setEnabled(true);
        waterSpout.getInfluencer(AlphaInfluencer.class).addAlpha(0, Interpolation.exp5Out);
        waterSpout.getInfluencer(AlphaInfluencer.class).addAlpha(0.35f, Interpolation.linear);
        waterSpout.getInfluencer(AlphaInfluencer.class).addAlpha(0, Interpolation.exp5In);
        waterSpout.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(0.3f, 0.25f, 0.3f).multLocal(scale));
        waterSpout.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(0.3f, 0.25f, 0.3f).multLocal(scale));
        waterSpout.getInfluencer(SizeInfluencer.class).setEnabled(true);
        waterSpout.getInfluencer(SpriteInfluencer.class).setUseRandomStartImage(true);
        waterSpout.getInfluencer(SpriteInfluencer.class).setAnimate(false);
        waterSpout.getInfluencer(RotationInfluencer.class).setUseRandomStartRotation(false, false, true);
        waterSpout.setLocalTranslation(0, 6, 0);
        waterSpout.setLocalScale(0.015f);
        addEmitter(registeredModel, waterSpout);
        //Emitter 3
        Emitter emitter3 = new Emitter();
        emitter3.setName("emitter3");
        emitter3.setMaxParticles(110);
        emitter3.addInfluencers(
            new GravityInfluencer(),
            new ColorInfluencer(),
            new AlphaInfluencer(),
            new SizeInfluencer(),
            new SpriteInfluencer()
        );
        Node dome = (Node) MaterialFactory.getAssetManager().loadModel("Models/shapes/dome.j3o");
        Mesh domeMesh = ((Geometry) dome.getChild(0)).getMesh();
        emitter3.setShape(domeMesh);
        emitter3.setDirectionType(EmitterMesh.DirectionType.Normal);
        emitter3.setEmitterTestMode(false, false);
        emitter3.setSprite("Textures/effects/smoke_additive.png", 2, 2);
        emitter3.setEmissionsPerSecond(100);
        emitter3.setParticlesPerEmission(1);
        emitter3.setForceMinMax(0.55f, 1.25f);
        emitter3.setLifeMinMax(0.5f, 0.5f);
        emitter3.setBillboardMode(Emitter.BillboardMode.Velocity_Z_Up);
        emitter3.setUseRandomEmissionPoint(true);
        emitter3.setUseVelocityStretching(true);
        emitter3.setVelocityStretchFactor(1.65f);
        emitter3.getInfluencer(GravityInfluencer.class).setGravity(new Vector3f(0, 2, 0));
        emitter3.getInfluencer(GravityInfluencer.class).setAlignment(GravityInfluencer.GravityAlignment.World);
        emitter3.getInfluencer(GravityInfluencer.class).setMagnitude(1.5f);
        emitter3.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(0.75f, 0.75f, 1, 1));
        emitter3.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(0.45f, 0.45f, 1, 1));
        emitter3.getInfluencer(ColorInfluencer.class).setEnabled(true);
        emitter3.getInfluencer(AlphaInfluencer.class).addAlpha(0.05f, Interpolation.exp5Out);
        emitter3.getInfluencer(AlphaInfluencer.class).addAlpha(0.35f, Interpolation.linear);
        emitter3.getInfluencer(AlphaInfluencer.class).addAlpha(0.1f, Interpolation.exp5In);
        emitter3.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(0.075f, 0.075f, 0.075f).multLocal(scale));
        emitter3.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(0.015f, 0.015f, 0.015f).multLocal(scale));
        emitter3.getInfluencer(SizeInfluencer.class).setEnabled(true);
        emitter3.getInfluencer(SpriteInfluencer.class).setUseRandomStartImage(true);
        emitter3.getInfluencer(SpriteInfluencer.class).setAnimate(false);
        emitter3.setLocalScale(0.05f);
        addEmitter(registeredModel, emitter3);
        //Emitter 4
        Emitter emitter4 = new Emitter();
        emitter4.setName("emitter4");
        emitter4.setMaxParticles(110);
        emitter4.addInfluencers(
            new GravityInfluencer(),
            new ColorInfluencer(),
            new AlphaInfluencer(),
            new SizeInfluencer(),
            new SpriteInfluencer(),
            new RotationInfluencer()
        );
        emitter4.setShape(domeMesh);
        emitter4.setDirectionType(EmitterMesh.DirectionType.Normal);
        emitter4.setEmitterTestMode(false, false);
        emitter4.setSprite("Textures/effects/smoke_additive.png", 2, 2);
        emitter4.setEmissionsPerSecond(100);
        emitter4.setParticlesPerEmission(1);
        emitter4.setForceMinMax(0.55f, 0.75f);
        emitter4.setLifeMinMax(0.25f, 0.45f);
        emitter4.setBillboardMode(Emitter.BillboardMode.Velocity_Z_Up);
        emitter4.setUseRandomEmissionPoint(true);
        emitter4.setUseVelocityStretching(false);
        emitter4.setVelocityStretchFactor(1.65f);
        emitter4.getInfluencer(GravityInfluencer.class).setGravity(new Vector3f(0, -7, 0));
        emitter4.getInfluencer(GravityInfluencer.class).setAlignment(GravityInfluencer.GravityAlignment.World);
        emitter4.getInfluencer(GravityInfluencer.class).setMagnitude(1.5f);
        emitter4.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(0.75f, 0.75f, 1, 1));
        emitter4.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(0.45f, 0.45f, 1, 1));
        emitter4.getInfluencer(ColorInfluencer.class).setEnabled(true);
        emitter4.getInfluencer(AlphaInfluencer.class).addAlpha(0.05f, Interpolation.exp5Out);
        emitter4.getInfluencer(AlphaInfluencer.class).addAlpha(0.35f, Interpolation.linear);
        emitter4.getInfluencer(AlphaInfluencer.class).addAlpha(0.1f, Interpolation.exp5In);
        emitter4.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(0.075f, 0.075f, 0.075f).multLocal(scale));
        emitter4.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(0.115f, 0.115f, 0.015f).multLocal(scale));
        emitter4.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(0.075f, 0.075f, 0.075f).multLocal(scale));
        emitter4.getInfluencer(SizeInfluencer.class).setEnabled(true);
        emitter4.getInfluencer(SpriteInfluencer.class).setUseRandomStartImage(true);
        emitter4.getInfluencer(SpriteInfluencer.class).setAnimate(false);
        emitter4.getInfluencer(RotationInfluencer.class).setUseRandomStartRotation(false, false, false);
        emitter4.getInfluencer(RotationInfluencer.class).setEnabled(false);
        emitter4.setLocalTranslation(0, 0, 0);
        emitter4.setLocalScale(0.05f);
        addEmitter(registeredModel, emitter4);
    }
    
    private void addEmitter(RegisteredModel registeredModel, Emitter emitter){
        emitter.initialize(MaterialFactory.getAssetManager());
        registeredModel.getNode().addControl(emitter);
        emitter.getParticleNode().setShadowMode(RenderQueue.ShadowMode.Off);
        emitter.setEnabled(true);
        for(Geometry geometry : JMonkeyUtil.getAllGeometryChilds(emitter.getParticleNode())){
            geometry.getMaterial().getAdditionalRenderState().setDepthTest(false);
            geometry.setUserData("layer", 3);
        }
    }
}
