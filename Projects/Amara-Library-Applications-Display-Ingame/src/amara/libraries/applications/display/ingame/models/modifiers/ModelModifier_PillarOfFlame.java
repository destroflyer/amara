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
public class ModelModifier_PillarOfFlame extends ModelModifier{

    @Override
    public void modify(ModelObject modelObject){
        //Pillar
        Emitter pillar = new Emitter();
        pillar.setName("pillar");
        pillar.setMaxParticles(72);
        pillar.addInfluencers(
            new GravityInfluencer(),
            new ColorInfluencer(),
            new SizeInfluencer(),
            new RadialVelocityInfluencer()
        );
        pillar.setShapeSimpleEmitter();
        pillar.setDirectionType(EmitterMesh.DirectionType.Random);
        pillar.setEmitterTestMode(false, false);
        pillar.setSprite("Models/pillar_of_flame/resources/fire.png", 2,2);
        pillar.setEmissionsPerSecond(70);
        pillar.setParticlesPerEmission(1);
        pillar.setForceMinMax(1.35f,2.35f);
        pillar.setLifeMinMax(1f,1f);
        pillar.setBillboardMode(Emitter.BillboardMode.Velocity_Z_Up);
        pillar.setUseRandomEmissionPoint(true);
        pillar.setUseSequentialEmissionFace(true);
        pillar.setUseSequentialSkipPattern(true);
        pillar.setUseVelocityStretching(true);
        pillar.setVelocityStretchFactor(0.35f);
        pillar.getInfluencer(GravityInfluencer.class).setGravity(0,-20,0);
        pillar.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(1f,1f,1,1f));
        pillar.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(0f,0f,1,0.25f));
        pillar.getInfluencer(ColorInfluencer.class).setEnabled(true);
        pillar.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(.3f,.3f,.3f));
        pillar.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(.05f,.05f,.05f));
        pillar.getInfluencer(SizeInfluencer.class).setEnabled(false);
        pillar.getInfluencer(RadialVelocityInfluencer.class).setRadialPullAlignment(RadialVelocityInfluencer.RadialPullAlignment.Emission_Point);
        pillar.getInfluencer(RadialVelocityInfluencer.class).setRadialPullCenter(RadialVelocityInfluencer.RadialPullCenter.Variable_Y);
        pillar.getInfluencer(RadialVelocityInfluencer.class).setTangentForce(14);
        pillar.getInfluencer(RadialVelocityInfluencer.class).setRadialPull(3.15f);
        pillar.getInfluencer(RadialVelocityInfluencer.class).setUseRandomDirection(true);
        pillar.setLocalTranslation(0,.2f,0);
        pillar.setLocalScale(0.5f);
        addEmitter(modelObject, pillar);
        //Base
        Emitter base = new Emitter();
        base.setName("base");
        base.setMaxParticles(50);
        base.addInfluencers(
            new ColorInfluencer(),
            new SizeInfluencer(),
            new RadialVelocityInfluencer()
        );
        base.setShapeSimpleEmitter();
        base.setDirectionType(EmitterMesh.DirectionType.RandomTangent);
        base.setEmitterTestMode(false, false);
        base.setSprite("Models/pillar_of_flame/resources/fire.png", 2, 2);
        base.setEmissionsPerSecond(50);
        base.setParticlesPerEmission(1);
        base.setForceMinMax(0.75f,1.05f);
        base.setLifeMinMax(1f,1f);
        base.setBillboardMode(Emitter.BillboardMode.Velocity_Z_Up_Y_Left);
        base.setUseRandomEmissionPoint(true);
        base.setUseVelocityStretching(true);
        base.setVelocityStretchFactor(.35f);
        base.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(1f,1f,1f,1f));
        base.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(0f,0f,0,0.025f));
        base.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(.3f,.3f,.3f));
        base.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(2.35f,2.35f,.05f));
        base.getInfluencer(SizeInfluencer.class).setEnabled(true);
        base.getInfluencer(RadialVelocityInfluencer.class).setRadialPullAlignment(RadialVelocityInfluencer.RadialPullAlignment.Emission_Point);
        base.getInfluencer(RadialVelocityInfluencer.class).setTangentForce(-24);
        base.getInfluencer(RadialVelocityInfluencer.class).setRadialPull(-3);
        base.setLocalTranslation(0,0,0);
        base.getParticleNode().setLocalScale(0.5f);
        addEmitter(modelObject, base);
        //Rocks
        Emitter rocks = new Emitter();
        rocks.setName("rocks");
        rocks.setMaxParticles(200);
        rocks.addInfluencers(
            new GravityInfluencer(),
            new ColorInfluencer(),
            new AlphaInfluencer(),
            new SizeInfluencer(),
            new RotationInfluencer(),
            new SpriteInfluencer(),
            new RadialVelocityInfluencer()
        );
        Node ring = (Node) MaterialFactory.getAssetManager().loadModel("Models/shapes/circle.j3o");
        Mesh ringMesh = ((Geometry) ring.getChild(0)).getMesh();
        rocks.setShape(ringMesh);
        rocks.setSprite("Models/pillar_of_flame/resources/debris.png", 3, 3);
        rocks.setDirectionType(EmitterMesh.DirectionType.RandomNormalAligned);
        rocks.setBillboardMode(Emitter.BillboardMode.Camera);
        rocks.setForceMinMax(2.31f, 4.31f);
        rocks.setLifeMinMax(1f, 1.25f);
        rocks.setEmissionsPerSecond(100);
        rocks.setParticlesPerEmission(1);
        rocks.setEmitterTestMode(false, false);
        rocks.getInfluencer(GravityInfluencer.class).setGravity(0,-0.25f,0);
        rocks.getInfluencer(GravityInfluencer.class).setAlignment(GravityInfluencer.GravityAlignment.Emission_Point);
        rocks.getInfluencer(GravityInfluencer.class).setMagnitude(2);
        rocks.getInfluencer(ColorInfluencer.class).addColor(ColorRGBA.LightGray, Interpolation.linear);
        rocks.getInfluencer(AlphaInfluencer.class).addAlpha(1, Interpolation.exp5Out);
        rocks.getInfluencer(AlphaInfluencer.class).addAlpha(1, Interpolation.exp5In);
        rocks.getInfluencer(AlphaInfluencer.class).addAlpha(0);
        rocks.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(.075f,.075f,.075f));
        rocks.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(.04f,.04f,.04f));
        rocks.getInfluencer(RotationInfluencer.class).addRotationSpeed(new Vector3f(0, 0, 13));
        rocks.getInfluencer(RotationInfluencer.class).setUseRandomStartRotation(false, false, true);
        rocks.getInfluencer(RotationInfluencer.class).setUseRandomDirection(true);
        rocks.getInfluencer(SpriteInfluencer.class).setUseRandomStartImage(true);
        rocks.getInfluencer(SpriteInfluencer.class).setAnimate(false);
        rocks.getInfluencer(RadialVelocityInfluencer.class).setRadialPullAlignment(RadialVelocityInfluencer.RadialPullAlignment.Emission_Point);
        rocks.getInfluencer(RadialVelocityInfluencer.class).setRadialPullCenter(RadialVelocityInfluencer.RadialPullCenter.Absolute);
        rocks.getInfluencer(RadialVelocityInfluencer.class).setTangentForce(3);
        rocks.getInfluencer(RadialVelocityInfluencer.class).setRadialPull(1);
        rocks.setLocalScale(0.5f,0.5f,0.5f);
        addEmitter(modelObject, rocks);
        //Flares
        Emitter flares = new Emitter();
        flares.setName("flares");
        flares.setMaxParticles(10);
        flares.addInfluencers(
            new GravityInfluencer(),
            new ColorInfluencer(),
            new AlphaInfluencer(),
            new SizeInfluencer()
        );
        flares.setShapeSimpleEmitter();
        flares.setDirectionType(EmitterMesh.DirectionType.RandomNormalAligned);
        flares.setEmitterTestMode(false, false);
        flares.setSprite("Models/pillar_of_flame/resources/glow.png");
        flares.setEmissionsPerSecond(2);
        flares.setParticlesPerEmission(2);
        flares.setForceMinMax(6f,6f);
        flares.setLifeMinMax(1f,1f);
        flares.setBillboardMode(Emitter.BillboardMode.Velocity_Z_Up);
        flares.setUseVelocityStretching(true);
        flares.getInfluencer(GravityInfluencer.class).setGravity(0,6,0);
        flares.getInfluencer(GravityInfluencer.class).setAlignment(GravityInfluencer.GravityAlignment.World);
        flares.getInfluencer(GravityInfluencer.class).setMagnitude(0.25f);
        flares.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(1f,1f,0,1f));
        flares.getInfluencer(ColorInfluencer.class).addColor(new ColorRGBA(1f,0f,0,0.25f));
        flares.getInfluencer(AlphaInfluencer.class).addAlpha(1f);
        flares.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(.1f,.1f,.1f));
        flares.getInfluencer(SizeInfluencer.class).addSize(new Vector3f(.065f,.065f,.065f));
        flares.setLocalScale(0.5f);
        addEmitter(modelObject, flares);
    }
    
    private void addEmitter(ModelObject modelObject, Emitter emitter){
        emitter.initialize(MaterialFactory.getAssetManager());
        modelObject.addControl(emitter);
        emitter.getParticleNode().setShadowMode(RenderQueue.ShadowMode.Off);
        emitter.setEnabled(true);
        for(Geometry geometry : JMonkeyUtil.getAllGeometryChilds(emitter.getParticleNode())){
            geometry.getMaterial().getAdditionalRenderState().setDepthTest(false);
            geometry.setUserData("layer", 2);
        }
    }
}
