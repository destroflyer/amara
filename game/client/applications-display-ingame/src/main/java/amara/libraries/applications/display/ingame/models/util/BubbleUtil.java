
package amara.libraries.applications.display.ingame.models.util;

import amara.libraries.applications.display.materials.MaterialFactory;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

public class BubbleUtil {

    public static Geometry createDefault() {
        return create("rainbow");
    }

    public static Geometry createWhite() {
        return create("rainbow_white");
    }

    public static Geometry createPink() {
        return create("rainbow_pink");
    }

    public static Geometry createBlue() {
        return create("rainbow_blue");
    }

    private static Geometry create(String textureName) {
        Geometry geometry = new Geometry("bubble", new Sphere(50, 50, 3));
        Material material = new Material(MaterialFactory.getAssetManager(), "Shaders/bubble/matdefs/bubble.j3md");
        material.setTexture("ColorMap", MaterialFactory.getAssetManager().loadTexture("Shaders/bubble/textures/" + textureName + ".png"));
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
