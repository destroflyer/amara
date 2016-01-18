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
import com.jme3.scene.Spatial;
import amara.game.entitysystem.components.visuals.TitleComponent;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class TitleSystem extends TopHUDAttachmentSystem{

    public TitleSystem(HUDAttachmentsSystem hudAttachmentsSystem, EntityHeightMap entityHeightMap){
        super(hudAttachmentsSystem, entityHeightMap, TitleComponent.class);
        hudOffset = new Vector3f(0, 18, 0);
    }
    private final float textSize = 12;
    private final float width = 100;
        
    @Override
    protected Spatial createVisualAttachment(EntityWorld entityWorld, int entity){
        BitmapFont font = MaterialFactory.getAssetManager().loadFont("Interface/fonts/Verdana_18.fnt");
        BitmapText bitmapText = new BitmapText(font);
        bitmapText.setSize(textSize);
        bitmapText.setColor(ColorRGBA.White);
        bitmapText.setBox(new Rectangle((-1 * (width / 2)), 0, width, 1));
        bitmapText.setAlignment(BitmapFont.Align.Center);
        return bitmapText;
    }

    @Override
    protected void updateVisualAttachment(EntityWorld entityWorld, int entity, Spatial visualAttachment){
        BitmapText bitmapText = (BitmapText) visualAttachment;
        String title = entityWorld.getComponent(entity, TitleComponent.class).getTitle();
        bitmapText.setText(title);
    }
}
