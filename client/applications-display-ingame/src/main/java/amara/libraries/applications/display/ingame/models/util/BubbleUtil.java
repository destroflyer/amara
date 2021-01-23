package amara.libraries.applications.display.ingame.models.util;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

public class BubbleUtil {

    public static Geometry createDefault(AssetManager assetManager) {
        return create(assetManager, "rainbow");
    }

    public static Geometry createWhite(AssetManager assetManager) {
        return create(assetManager, "rainbow_white");
    }

    public static Geometry createPink(AssetManager assetManager) {
        return create(assetManager, "rainbow_pink");
    }

    public static Geometry createBlue(AssetManager assetManager) {
        return create(assetManager, "rainbow_blue");
    }

    private static Geometry create(AssetManager assetManager, String textureName) {
        Geometry geometry = new Geometry("bubble", new Sphere(50, 50, 3));
        Material material = new Material(assetManager, "Shaders/bubble/matdefs/bubble.j3md");
        material.setTexture("ColorMap", assetManager.loadTexture("Shaders/bubble/textures/" + textureName + ".png"));
        material.setFloat("Shininess", 5);
        material.setColor("SpecularColor", ColorRGBA.White);
        material.setBoolean("UseSpecularNormal", true);
        geometry.setMaterial(material);
        geometry.setQueueBucket(RenderQueue.Bucket.Transparent);
        geometry.setShadowMode(RenderQueue.ShadowMode.Receive);
        geometry.setLocalTranslation(0, 2.5f, 0);
        geometry.addControl(new TimeMaterialParamControl("Time"));
        return geometry;
    }
}
