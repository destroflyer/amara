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
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.engine.applications.ingame.client.systems.visualisation.controls.TextNotificationAttachmentControl;
import amara.engine.materials.MaterialFactory;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class EntityTextNotificationSystem implements EntitySystem{

    protected EntityTextNotificationSystem(HUDAttachmentsSystem hudAttachmentsSystem, EntityHeightMap entityHeightMap){
        this.hudAttachmentsSystem = hudAttachmentsSystem;
        this.entityHeightMap = entityHeightMap;
    }
    private HUDAttachmentsSystem hudAttachmentsSystem;
    private EntityHeightMap entityHeightMap;
    private int nextChangeID;
    protected Vector3f hudOffset = new Vector3f();

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        
    }
    
    protected void displayTextNotification(EntityWorld entityWorld, int entity, String text, ColorRGBA color){
        Vector3f worldOffset = entityHeightMap.getWorldOffset(entity);
        Spatial hudAttachment = createHUDAttachment(text, color);
        hudAttachmentsSystem.attach(new HUDAttachmentInfo(entity, "text_notification_" + hashCode() + "_" + nextChangeID, worldOffset, hudOffset, false), hudAttachment);
        nextChangeID++;
    }
    
    private Spatial createHUDAttachment(String text, ColorRGBA color){
        Node node = new Node();
        BitmapFont font = MaterialFactory.getAssetManager().loadFont("Interface/fonts/Verdana_18_bold.fnt");
        BitmapText bitmapText = new BitmapText(font);
        bitmapText.setSize(12);
        bitmapText.setColor(color);
        int boxWidth = 100;
        bitmapText.setBox(new Rectangle((boxWidth / -2), 20, boxWidth, 1));
        bitmapText.setAlignment(BitmapFont.Align.Center);
        bitmapText.setText(text);
        node.attachChild(bitmapText);
        node.addControl(new TextNotificationAttachmentControl(15, 1.75f, color));
        return node;
    }
}
