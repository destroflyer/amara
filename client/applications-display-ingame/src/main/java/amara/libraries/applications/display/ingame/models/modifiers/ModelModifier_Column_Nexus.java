package amara.libraries.applications.display.ingame.models.modifiers;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.Geometry;
import com.jme3.scene.control.AbstractControl;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.models.*;

public class ModelModifier_Column_Nexus extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel, AssetManager assetManager){
        Spatial statue = ModelSkin.get("Models/nidalee/skin.xml").load(assetManager);
        // Divide by 1.5, since the nexus model already has scale 1.5
        statue.setLocalScale(statue.getLocalScale().divide(1.5f));
        Material material = assetManager.loadMaterial("Shaders/glass/materials/glass_3.j3m");
        for (Geometry geometry : JMonkeyUtil.getAllGeometryChilds(statue)) {
            geometry.setMaterial(material);
        }
        statue.setLocalTranslation(0, 4, 0);
        statue.addControl(new AbstractControl() {

            @Override
            protected void controlUpdate(float lastTimePerFrame) {
                getSpatial().rotate(JMonkeyUtil.getQuaternion_Y(-50 * lastTimePerFrame));
            }

            @Override
            protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

            }
        });
        registeredModel.getNode().attachChild(statue);
    }
}
