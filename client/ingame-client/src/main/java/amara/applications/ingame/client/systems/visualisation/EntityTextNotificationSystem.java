package amara.applications.ingame.client.systems.visualisation;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.applications.ingame.client.systems.visualisation.controls.TextNotificationAttachmentControl;
import amara.libraries.entitysystem.*;

public class EntityTextNotificationSystem implements EntitySystem {

    protected EntityTextNotificationSystem(HUDAttachmentsSystem hudAttachmentsSystem, EntityHeightMap entityHeightMap, AssetManager assetManager) {
        this.hudAttachmentsSystem = hudAttachmentsSystem;
        this.entityHeightMap = entityHeightMap;
        this.assetManager = assetManager;
    }
    private HUDAttachmentsSystem hudAttachmentsSystem;
    private EntityHeightMap entityHeightMap;
    private AssetManager assetManager;
    private int nextChangeID;
    protected Vector3f hudOffset = new Vector3f();

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {

    }

    protected void displayTextNotification(int entity, String text, ColorRGBA color) {
        Vector3f worldOffset = entityHeightMap.getWorldOffset(entity);
        Spatial hudAttachment = createHUDAttachment(text, color);
        hudAttachmentsSystem.attach(new HUDAttachmentInfo(entity, "text_notification_" + hashCode() + "_" + nextChangeID, worldOffset, hudOffset, false), hudAttachment);
        nextChangeID++;
    }

    private Spatial createHUDAttachment(String text, ColorRGBA color) {
        Node node = new Node();
        BitmapFont font = assetManager.loadFont("Interface/fonts/Verdana_18_bold.fnt");
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
