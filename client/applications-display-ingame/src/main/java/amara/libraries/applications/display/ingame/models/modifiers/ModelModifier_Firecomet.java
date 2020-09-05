/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.models.modifiers;

import com.jme3.effect.ParticleEmitter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Sphere;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.applications.display.models.*;

/**
 *
 * @author Carl
 */
public class ModelModifier_Firecomet extends ModelModifier{

    @Override
    public void modify(RegisteredModel registeredModel){
        Node node = registeredModel.getNode();
        Geometry rock = new Geometry(null, new Sphere(20, 20, 0.58f));
        rock.setMaterial(MaterialFactory.generateUnshadedMaterial("Textures/terrain/rock.jpg"));
        node.attachChild(rock);
        ParticleEmitter particleEmitter = (ParticleEmitter) node.getChild(0);
        particleEmitter.updateLogicalState(10);
        node.addControl(new AbstractControl(){
            
            private float time;
            private float y = 5;
            private float speed;
            private boolean isFinished;

            @Override
            protected void controlUpdate(float lastTimePerFrame){
                time += lastTimePerFrame;
                if(time > 0.4f){
                    speed += (lastTimePerFrame * 3.2f);
                    y -= (lastTimePerFrame * speed * speed);
                    isFinished = (y <= 0);
                    if(isFinished){
                        y = 0;
                    }
                }
                spatial.setLocalTranslation(0, y, 0);
                spatial.rotate(0, lastTimePerFrame * 0.5f, 0);
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
