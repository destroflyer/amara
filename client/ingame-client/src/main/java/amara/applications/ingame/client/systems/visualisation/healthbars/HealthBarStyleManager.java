package amara.applications.ingame.client.systems.visualisation.healthbars;

import java.util.HashMap;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import amara.applications.ingame.client.systems.visualisation.healthbars.styles.*;
import amara.applications.ingame.client.systems.visualisation.meshes.RectangleMesh;
import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.applications.display.materials.*;
import amara.libraries.entitysystem.EntityWorld;

public class HealthBarStyleManager {

    private HashMap<Integer, PaintableImage[]> paintableImages = new HashMap<>();
    // The order of these objects maps them to the according HealthBarStyleComponent.HealthBarStyle
    private HealthBarStyle[] styles = new HealthBarStyle[]{
        new HealthBarStyle_Small(),
        new HealthBarStyle_Medium(),
        new HealthBarStyle_Large(),
        new HealthBarStyle_Character(),
        new HealthBarStyle_Boss()
    };

    public Spatial createGeometry(EntityWorld entityWorld, int entity) {
        HealthBarStyle style = getStyle(entityWorld, entity);
        Geometry geometry = new Geometry(null, new RectangleMesh((style.getBarWidth() / -2), 0, 0, style.getBarWidth(), style.getBarHeight()));
        Material material = new Material(MaterialFactory.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Texture2D texture2D = new Texture2D();
        texture2D.setMagFilter(Texture.MagFilter.Nearest);
        material.setTexture("ColorMap", texture2D);
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        geometry.setMaterial(material);
        return geometry;
    }

    public PaintableImage getImage_MaximumHealth(int entity, HealthBarStyle style) {
        return getImages(entity, style)[0];
    }

    public PaintableImage getImage_CurrentHealth(int entity, HealthBarStyle style) {
        return getImages(entity, style)[1];
    }

    private PaintableImage[] getImages(int entity, HealthBarStyle style) {
        PaintableImage[] images = paintableImages.get(entity);
        if (images == null) {
            PaintableImage imageMaximum = new PaintableImage(style.getImageWidth(), style.getImageHeight());
            PaintableImage imageCurrent = new PaintableImage(style.getImageWidth(), style.getImageHeight());
            images = new PaintableImage[]{imageMaximum, imageCurrent};
            paintableImages.put(entity, images);
        }
        return images;
    }

    public float getHealthBarY(EntityWorld entityWorld, int entity) {
        if (entityWorld.hasComponent(entity, HealthComponent.class)) {
            HealthBarStyle style = getStyle(entityWorld, entity);
            return style.getBarHeight();
        }
        return 0;
    }

    public HealthBarStyle getStyle(EntityWorld entityWorld, int entity) {
        HealthBarStyleComponent.HealthBarStyle style = HealthBarStyleComponent.HealthBarStyle.SMALL;
        HealthBarStyleComponent healthBarStyleComponent = entityWorld.getComponent(entity, HealthBarStyleComponent.class);
        if (healthBarStyleComponent != null) {
            style = healthBarStyleComponent.getStyle();
        }
        return styles[style.ordinal()];
    }
}
