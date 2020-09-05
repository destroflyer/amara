/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.models.modifiers;

import com.jme3.effect.ParticleEmitter;
import com.jme3.math.FastMath;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.applications.display.models.*;

/**
 *
 * @author Carl
 */
public class ModelModifier_Erika_FireItUp extends ModelModifier{

    @Override
    public void modify(RegisteredModel registeredModel){
        Node arrowHead = (Node) MaterialFactory.getAssetManager().loadModel("Models/erika_arrow/head_fire.j3o");
        arrowHead.setLocalTranslation(0, 0, 0.75f);
        arrowHead.setLocalScale(0.015f, 0.015f, 0.015f);
        ParticleEmitter particleEmitter = (ParticleEmitter) arrowHead.getChild(0);
        particleEmitter.updateLogicalState(10);
        registeredModel.getNode().attachChild(arrowHead);
        registeredModel.getNode().addControl(new AbstractControl(){
            
            private float y = 20;
            private float speed;
            private boolean isFallingFinished;

            @Override
            protected void controlUpdate(float lastTimePerFrame){
                speed += (lastTimePerFrame * 7);
                y -= (lastTimePerFrame * speed * speed);
                isFallingFinished = (y <= 0);
                if(isFallingFinished){
                    y = 0;
                }
                spatial.setLocalTranslation(0, y, 0);
                spatial.rotate(0, 0, lastTimePerFrame * FastMath.PI);
                if(isFallingFinished){
                    spatial.removeControl(this);
                }
            }

            @Override
            protected void controlRender(RenderManager renderManager, ViewPort viewPort){
                
            }
        });
    }
}
