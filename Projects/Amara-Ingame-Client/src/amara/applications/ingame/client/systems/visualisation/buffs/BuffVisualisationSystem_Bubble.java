/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import amara.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class BuffVisualisationSystem_Bubble extends BuffVisualisationSystem{

    public BuffVisualisationSystem_Bubble(EntitySceneMap entitySceneMap){
        super(entitySceneMap, "bubble");
    }
    
    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int buffStatusEntity, int targetEntity){
        Geometry geometry = new Geometry("Sphere", new Sphere(200, 200, 3.5f));
        Material material = new Material(MaterialFactory.getAssetManager(), "Shaders/bubble/matdefs/bubble.j3md");
        material.setTexture("ColorMap", MaterialFactory.getAssetManager().loadTexture("Shaders/bubble/textures/rainbow.png"));
        material.setFloat("Shininess", 5f);
        material.setColor("SpecularColor", ColorRGBA.Blue);
        material.setBoolean("UseSpecularNormal", true);
        geometry.setMaterial(material);
        geometry.setQueueBucket(RenderQueue.Bucket.Transparent);
        geometry.setLocalTranslation(0, 2.5f, 0);
        return geometry;
    }
}
