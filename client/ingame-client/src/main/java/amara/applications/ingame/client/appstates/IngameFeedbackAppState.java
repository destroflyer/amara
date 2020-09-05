/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.effect.shapes.EmitterPointShape;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.applications.ingame.client.IngameClientApplication;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.appstates.BaseDisplayAppState;
import amara.libraries.applications.display.ingame.appstates.MapAppState;
import amara.libraries.applications.display.materials.MaterialFactory;

/**
 *
 * @author Carl
 */
public class IngameFeedbackAppState extends BaseDisplayAppState<IngameClientApplication>{

    public IngameFeedbackAppState(){
        
    }
    private Node movementTargetDisplay;
    private ParticleEmitter[] movementTargetDisplay_ParticleEmitters = new ParticleEmitter[4];

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        movementTargetDisplay = new Node();
        Vector2f currentArrowAngle = new Vector2f(1, 1);
        for(int i=0;i<movementTargetDisplay_ParticleEmitters.length;i++){
            ParticleEmitter particleEmitter = new ParticleEmitter("", ParticleMesh.Type.Triangle, 1);
            Material material = new Material(MaterialFactory.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
            material.setTexture("Texture", MaterialFactory.getAssetManager().loadTexture("Textures/effects/movement_target_arrow_additive.png"));
            particleEmitter.setMaterial(material);
            particleEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 0, -6));
            particleEmitter.getParticleInfluencer().setVelocityVariation(0);
            particleEmitter.setGravity(Vector3f.ZERO);
            particleEmitter.setInWorldSpace(false);
            particleEmitter.setParticlesPerSec(0);
            particleEmitter.setShape(new EmitterPointShape(new Vector3f(0, 0, 0)));
            particleEmitter.setLowLife(0.25f);
            particleEmitter.setHighLife(0.25f);
            particleEmitter.setStartSize(0.4f);
            particleEmitter.setEndSize(0.4f);
            particleEmitter.setStartColor(new ColorRGBA(1, 1, 1, 1));
            particleEmitter.setEndColor(new ColorRGBA(1, 1, 1, 0));
            particleEmitter.setFacingVelocity(true);
            particleEmitter.setQueueBucket(RenderQueue.Bucket.Opaque);
            particleEmitter.getMaterial().getAdditionalRenderState().setDepthTest(false);
            particleEmitter.setUserData("layer", 4);
            Vector3f angleVector = new Vector3f(currentArrowAngle.getX(), 0, currentArrowAngle.getY());
            particleEmitter.setLocalTranslation(angleVector.mult(1.1f));
            JMonkeyUtil.setLocalRotation(particleEmitter, angleVector);
            movementTargetDisplay.attachChild(particleEmitter);
            movementTargetDisplay_ParticleEmitters[i] = particleEmitter;
            currentArrowAngle.rotateAroundOrigin(FastMath.HALF_PI, true);
        }
        movementTargetDisplay.setCullHint(Spatial.CullHint.Always);
        mainApplication.getRootNode().attachChild(movementTargetDisplay);
    }
    
    public void displayMovementTarget(Vector2f location){
        float y = getAppState(MapAppState.class).getMapHeightmap().getHeight(location);
        movementTargetDisplay.setLocalTranslation(location.getX(), y, location.getY());
        movementTargetDisplay.setCullHint(Spatial.CullHint.Inherit);
        for(ParticleEmitter particleEmitter : movementTargetDisplay_ParticleEmitters){
            particleEmitter.killAllParticles();
            particleEmitter.emitAllParticles();
        }
    }
}
