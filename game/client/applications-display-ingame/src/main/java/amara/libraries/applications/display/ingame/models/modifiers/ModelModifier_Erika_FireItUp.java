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
    public void modify(ModelObject modelObject){
        Node arrowHead = (Node) MaterialFactory.getAssetManager().loadModel("Models/erika_arrow/head_fire.j3o");
        arrowHead.setLocalTranslation(0, -1.7f, 0);
        arrowHead.setLocalScale(0.3f, 0.5f, 0.3f);
        ParticleEmitter particleEmitter = (ParticleEmitter) arrowHead.getChild(0);
        particleEmitter.updateLogicalState(10);
        modelObject.attachChild(arrowHead);
        modelObject.addControl(new AbstractControl(){
            
            private float y = 20;
            private float speed;
            private boolean isFinished;

            @Override
            protected void controlUpdate(float lastTimePerFrame){
                speed += (lastTimePerFrame * 7);
                y -= (lastTimePerFrame * speed * speed);
                isFinished = (y <= 0);
                if(isFinished){
                    y = 0;
                }
                spatial.setLocalTranslation(0, y, 0);
                spatial.rotate(0, lastTimePerFrame * FastMath.PI, 0);
                if(isFinished){
                    spatial.removeControl(this);
                }
            }

            @Override
            protected void controlRender(RenderManager renderManager, ViewPort viewPort){
                
            }
        });
    }
}
