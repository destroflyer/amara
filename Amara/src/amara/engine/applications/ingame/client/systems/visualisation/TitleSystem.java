/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.engine.applications.ingame.client.maps.MapHeightmap;
import amara.engine.materials.MaterialFactory;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.visuals.TitleComponent;

/**
 *
 * @author Carl
 */
public class TitleSystem extends HUDAttachmentSystem{

    public TitleSystem(Node guiNode, Camera camera, MapHeightmap mapHeightmap){
        super(TitleComponent.class, true, guiNode, camera, mapHeightmap);
        worldOffset = MaximumHealthBarSystem.BAR_LOCATION;
        hudOffset = new Vector3f(0, 24, 0);
    }
    private final float textSize = 12;
    private final float width = 100;
        
    @Override
    protected Spatial createVisualAttachment(EntityWorld entityWorld, int entity){
        TitleComponent titleComponent = entityWorld.getComponent(entity, TitleComponent.class);
        BitmapFont font = MaterialFactory.getAssetManager().loadFont("Interface/fonts/Verdana_18.fnt");
        BitmapText bitmapText = new BitmapText(font);
        bitmapText.setSize(textSize);
        bitmapText.setColor(ColorRGBA.White);
        bitmapText.setText(titleComponent.getTitle());
        bitmapText.setBox(new Rectangle((-1 * (width / 2)), 0, width, 1));
        bitmapText.setAlignment(BitmapFont.Align.Center);
        bitmapText.setLocalTranslation(100, 100, 0);
        return bitmapText;
    }
}
