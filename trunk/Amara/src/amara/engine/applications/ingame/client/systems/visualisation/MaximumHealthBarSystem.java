/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import java.awt.Color;
import com.jme3.math.Vector3f;
import com.jme3.material.Material;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import amara.engine.JMonkeyUtil;
import amara.engine.materials.MaterialFactory;
import amara.engine.applications.ingame.client.maps.MapHeightmap;
import amara.engine.applications.ingame.client.models.ModelObject;
import amara.engine.applications.ingame.client.systems.visualisation.meshes.RectangleMesh;
import amara.engine.materials.PaintableImage;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;

/**
 *
 * @author Carl
 */
public class MaximumHealthBarSystem extends HUDAttachmentSystem{

    public MaximumHealthBarSystem(EntitySceneMap entitySceneMap, Node guiNode, Camera camera, MapHeightmap mapHeightmap){
        super(MaximumHealthComponent.class, true, guiNode, camera, mapHeightmap);
        this.entitySceneMap = entitySceneMap;
    }
    public static final float BAR_WIDTH = 70;
    public static final float BAR_HEIGHT = 8;
    private EntitySceneMap entitySceneMap;
    public static final Vector3f BAR_LOCATION = new Vector3f(0, 4.25f, 0);
        
    @Override
    protected Spatial createVisualAttachment(EntityWorld entityWorld, int entity){
        Geometry geometry = new Geometry("", new RectangleMesh((BAR_WIDTH / -2), 0, 0, BAR_WIDTH, BAR_HEIGHT)); 
        int imageWidth = 80;
        int imageHeight = 10;
        PaintableImage paintableImage = new PaintableImage(imageWidth, imageHeight);
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
        Material material = new Material(MaterialFactory.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Texture2D texture2D = new Texture2D(paintableImage.getImage());
        texture2D.setMagFilter(Texture.MagFilter.Nearest);
        material.setTexture("ColorMap", texture2D);
        geometry.setMaterial(material);
        return geometry;
    }

    @Override
    protected Vector3f getWorldOffset(EntityWorld entityWorld, int entity){
        return getWorldOffset(entityWorld, entity, entitySceneMap);
    }
    
    public static Vector3f getWorldOffset(EntityWorld entityWorld, int entity, EntitySceneMap entitySceneMap){
        ModelObject modelObject = (ModelObject) entitySceneMap.requestNode(entity).getChild(ModelSystem.NODE_NAME_MODEL);
        if(modelObject != null){
            Vector3f spatialDimension = JMonkeyUtil.getSpatialDimension(modelObject);
            return new Vector3f(0, spatialDimension.getY() + 1, 0);
        }
        return Vector3f.ZERO;
    }
}
