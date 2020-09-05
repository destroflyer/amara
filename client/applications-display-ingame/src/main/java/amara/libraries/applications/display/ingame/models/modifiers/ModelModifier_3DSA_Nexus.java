/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.models.modifiers;

import com.jme3.material.Material;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.models.*;

/**
 *
 * @author Carl
 */
public class ModelModifier_3DSA_Nexus extends ModelModifier{

    @Override
    public void modify(RegisteredModel registeredModel){
        Spatial crystal = ModelSkin.get("Models/3dsa_fantasy_forest_waypoint_crystal/skin.xml").load();
        // Divide by 4, since the nexus model already has scale 4
        crystal.setLocalScale(crystal.getLocalScale().mult(0.25f));
        Material material = ((Geometry) registeredModel.getNode().getChild(0)).getMaterial();
        for(Geometry geometry : JMonkeyUtil.getAllGeometryChilds(crystal)){
            geometry.setMaterial(material);
        }
        crystal.addControl(new AbstractControl(){

            @Override
            protected void controlUpdate(float lastTimePerFrame){
                getSpatial().rotate(JMonkeyUtil.getQuaternion_Y(-50 * lastTimePerFrame));
            }

            @Override
            protected void controlRender(RenderManager renderManager, ViewPort viewPort){
                
            }
        });
        registeredModel.getNode().attachChild(crystal);
    }
}
