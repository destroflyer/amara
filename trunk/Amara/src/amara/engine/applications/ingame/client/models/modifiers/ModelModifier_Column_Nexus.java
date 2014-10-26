/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.models.modifiers;

import com.jme3.material.Material;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.Geometry;
import com.jme3.scene.control.AbstractControl;
import amara.engine.JMonkeyUtil;
import amara.engine.applications.ingame.client.models.*;
import amara.engine.materials.MaterialFactory;

/**
 *
 * @author Carl
 */
public class ModelModifier_Column_Nexus extends ModelModifier{

    @Override
    public void modify(ModelObject modelObject){
        Spatial statue = new ModelSkin("/Models/nidalee/skin.xml").loadSpatial();
        Material material = MaterialFactory.getAssetManager().loadMaterial("Shaders/glass/materials/glass_3.j3m");
        for(Geometry geometry : JMonkeyUtil.getAllGeometryChilds(statue)){
            geometry.setMaterial(material);
        }
        statue.setLocalTranslation(0, 6, 0);
        statue.addControl(new AbstractControl(){

            @Override
            protected void controlUpdate(float lastTimePerFrame){
                getSpatial().rotate(JMonkeyUtil.getQuaternion_Y(-50 * lastTimePerFrame));
            }

            @Override
            protected void controlRender(RenderManager renderManager, ViewPort viewPort){
                
            }
        });
        modelObject.attachChild(statue);
    }
}
