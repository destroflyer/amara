/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import amara.engine.applications.ingame.client.systems.visualisation.*;
import amara.engine.materials.MaterialFactory;
import amara.game.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class BuffVisualisationSystem_Intervention extends BuffVisualisationSystem{

    public BuffVisualisationSystem_Intervention(EntitySceneMap entitySceneMap){
        super(entitySceneMap, "intervention");
    }
    
    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int buffStatusEntity, int targetEntity){
        Geometry geometry = new Geometry("", new Sphere(20, 20, 3));
        Material material = MaterialFactory.generateUnshadedMaterial(new ColorRGBA(1, 1, 0, 0.25f));
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        material.getAdditionalRenderState().setDepthTest(false);
        geometry.setMaterial(material);
        geometry.setLocalTranslation(0, 2, 0);
        geometry.setUserData("layer", 4);
        return geometry;
    }
}
