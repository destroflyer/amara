package amara.libraries.applications.display.ingame.models.util;

import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.materials.MaterialFactory;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;

public class GroundTextures {

    public static Geometry create(String textureFilePath, float width, float height) {
        return create(textureFilePath, (width / -2), (height / 2), width, height);
    }

    public static Geometry create(String textureFilePath, float x, float y, float width, float height) {
        return create(textureFilePath, x, y, width, height, RenderState.BlendMode.Alpha);
    }

    public static Geometry create(String textureFilePath, float x, float y, float width, float height, RenderState.BlendMode blendMode) {
        Geometry geometry = new Geometry(null, new Quad(width, height));
        geometry.setLocalTranslation(x, 0, y);
        geometry.rotate(JMonkeyUtil.getQuaternion_X(-90));
        Material material = MaterialFactory.generateUnshadedMaterial(textureFilePath);
        material.getAdditionalRenderState().setBlendMode(blendMode);
        material.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        material.getAdditionalRenderState().setDepthTest(false);
        geometry.setMaterial(material);
        geometry.setShadowMode(RenderQueue.ShadowMode.Receive);
        geometry.setUserData("layer", 2);
        return geometry;
    }
}
