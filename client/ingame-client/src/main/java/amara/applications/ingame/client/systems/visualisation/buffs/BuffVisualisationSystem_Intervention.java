package amara.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import amara.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.entitysystem.EntityWorld;

public class BuffVisualisationSystem_Intervention extends BuffVisualisationSystem {

    public BuffVisualisationSystem_Intervention(EntitySceneMap entitySceneMap, AssetManager assetManager) {
        super(entitySceneMap, assetManager, "intervention");
    }

    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity) {
        Geometry geometry = new Geometry("", new Sphere(20, 20, 3));
        Material material = MaterialFactory.generateUnshadedMaterial(assetManager, new ColorRGBA(1, 1, 0, 0.25f));
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        material.getAdditionalRenderState().setDepthTest(false);
        geometry.setMaterial(material);
        geometry.setLocalTranslation(0, 2, 0);
        geometry.setUserData("layer", 8);
        return geometry;
    }
}
