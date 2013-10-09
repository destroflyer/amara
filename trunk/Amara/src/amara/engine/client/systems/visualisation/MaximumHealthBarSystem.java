/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.systems.visualisation;

import java.awt.Color;
import com.jme3.math.Vector3f;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.BillboardControl;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import amara.engine.client.MaterialFactory;
import amara.engine.client.systems.visualisation.meshes.RectangleMesh;
import amara.engine.materials.PaintableImage;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.MaximumHealthComponent;

/**
 *
 * @author Carl
 */
public class MaximumHealthBarSystem extends SimpleVisualAttachmentSystem{

    public MaximumHealthBarSystem(EntitySceneMap entitySceneMap){
        super(entitySceneMap, MaximumHealthComponent.class);
    }
    public static final float BAR_WIDTH = 3;
    public static final float BAR_HEIGHT = 0.3f;
    public static final Vector3f BAR_LOCATION = new Vector3f(0, 5.5f, 0);
        
    @Override
    protected Spatial createVisualAttachment(EntityWorld entityWorld, int entity){
        Geometry geometry = new Geometry("", new RectangleMesh((BAR_WIDTH / -2), 0, 0, BAR_WIDTH, BAR_HEIGHT)); 
        Material material = new Material(MaterialFactory.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        int imageWidth = 80;
        int imageHeight = 10;
        PaintableImage paintableImage = new PaintableImage(imageWidth, imageHeight);
        paintableImage.setBackground(new Color(0.3f, 0.9f, 0.2f));
        float maximumHealth = entityWorld.getCurrent().getComponent(entity, MaximumHealthComponent.class).getValue();
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
        Texture2D texture2D = new Texture2D(paintableImage.getImage());
        texture2D.setMagFilter(Texture.MagFilter.Nearest);
        material.setTexture("ColorMap", texture2D);
        material.getAdditionalRenderState().setDepthTest(false);
        geometry.setMaterial(material);
        geometry.addControl(new BillboardControl());
        geometry.setLocalTranslation(BAR_LOCATION);
        geometry.setUserData("layer", 5);
        return geometry;
    }
}
