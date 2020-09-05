/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.models.modifiers;

import com.jme3.math.FastMath;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.applications.display.models.*;

/**
 *
 * @author Carl
 */
public class ModelModifier_Ganfaul_Binding_Particles extends ModelModifier{

    @Override
    public void modify(RegisteredModel registeredModel){
        Node node = registeredModel.getNode();
        int spheresCount = 3;
        for(int i=0;i<spheresCount;i++){
            Spatial particle = MaterialFactory.getAssetManager().loadModel("Models/ganfaul_binding/ganfaul_binding.j3o");
            particle.setLocalScale(0.5f);
            final float speed = (2 * FastMath.PI);
            final float startTime = (i * ((FastMath.TWO_PI / speed) / spheresCount));
            particle.addControl(new AbstractControl(){

                private float time = startTime;
                private float x;
                private float z;
                private float radius = 2;

                @Override
                protected void controlUpdate(float lastTimePerFrame){
                    time += lastTimePerFrame;
                    x = (FastMath.sin(time * speed) * radius);
                    z = (FastMath.cos(time * speed) * radius);
                    spatial.setLocalTranslation(x, 0, z);
                }

                @Override
                protected void controlRender(RenderManager renderManager, ViewPort viewPort){

                }
            });
            node.attachChild(particle);
        }
    }
}
