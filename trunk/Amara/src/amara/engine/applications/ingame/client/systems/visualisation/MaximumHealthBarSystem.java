/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import java.awt.Color;
import java.util.HashMap;
import com.jme3.math.Vector3f;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import amara.engine.materials.MaterialFactory;
import amara.engine.applications.ingame.client.systems.visualisation.meshes.RectangleMesh;
import amara.engine.materials.PaintableImage;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;

/**
 *
 * @author Carl
 */
public class MaximumHealthBarSystem extends TopHUDAttachmentSystem{

    public MaximumHealthBarSystem(HUDAttachmentsSystem hudAttachmentsSystem, EntityHeightMap entityHeightMap){
        super(hudAttachmentsSystem, entityHeightMap, MaximumHealthComponent.class);
    }
    public static final float BAR_WIDTH = 70;
    public static final float BAR_HEIGHT = 8;
    public static final Vector3f BAR_LOCATION = new Vector3f(0, 4.25f, 0);
    private HashMap<Integer, PaintableImage> paintableImages = new HashMap<Integer, PaintableImage>();
        
    @Override
    protected Spatial createVisualAttachment(EntityWorld entityWorld, int entity){
        Geometry geometry = new Geometry("", new RectangleMesh((BAR_WIDTH / -2), 0, 0, BAR_WIDTH, BAR_HEIGHT));
        Material material = new Material(MaterialFactory.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Texture2D texture2D = new Texture2D();
        texture2D.setMagFilter(Texture.MagFilter.Nearest);
        material.setTexture("ColorMap", texture2D);
        geometry.setMaterial(material);
        if(!paintableImages.containsKey(entity)){
            paintableImages.put(entity, new PaintableImage(80, 10));
        }
        return geometry;
    }

    @Override
    protected void updateVisualAttachment(EntityWorld entityWorld, int entity, Spatial visualAttachment){
        PaintableImage paintableImage = paintableImages.get(entity);
        int imageWidth = paintableImage.getWidth();
        int imageHeight = paintableImage.getHeight();
        paintableImage.setBackground(new Color(0.3f, 0.9f, 0.2f));
        float maximumHealth = entityWorld.getComponent(entity, MaximumHealthComponent.class).getValue();
        int subBarWidth = 100;
        float partWidth = (imageWidth / (maximumHealth / subBarWidth));
        int finishedSubBars = 0;
        for(int x=1;x<(imageWidth - 1);x++){
            int subBarIndex = (int) (x / partWidth);
            if(subBarIndex > finishedSubBars){
                for(int y=1;y<(imageHeight - 1);y++){
                    paintableImage.setPixel(x, 0, y, 0, 0, 255);
                    paintableImage.setPixel(x + 1, y, 0, 0, 0, 255);
                }
                finishedSubBars++;
            }
        }
        for(int x=0;x<imageWidth;x++){
            paintableImage.setPixel(x, 0, 0, 0, 0, 255);
            paintableImage.setPixel(x, (imageHeight - 1), 0, 0, 0, 255);
        }
        for(int y=0;y<imageHeight;y++){
            paintableImage.setPixel(0, y, 0, 0, 0, 255);
            paintableImage.setPixel((imageWidth - 1), y, 0, 0, 0, 255);
        }
        Geometry geometry = (Geometry) visualAttachment;
        Texture texture = geometry.getMaterial().getTextureParam("ColorMap").getTextureValue();
        texture.setImage(paintableImage.getImage());
    }
}
