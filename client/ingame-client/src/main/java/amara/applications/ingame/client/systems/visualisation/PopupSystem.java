/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.applications.ingame.client.systems.visualisation.meshes.PopupMesh;
import amara.applications.ingame.entitysystem.components.units.PopupComponent;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class PopupSystem extends TopHUDAttachmentSystem {

    public PopupSystem(HUDAttachmentsSystem hudAttachmentsSystem, EntityHeightMap entityHeightMap) {
        super(hudAttachmentsSystem, entityHeightMap, PopupComponent.class);
        hudOffset = new Vector3f(0, 0, 2);
    }
    private final static String TEXT_NAME = "text";
    private final float textSize = 15;
    private final float width = 1000;

    @Override
    protected Spatial createVisualAttachment(EntityWorld entityWorld, int entity) {
        String text = entityWorld.getComponent(entity, PopupComponent.class).getText();
        int rows = text.split("\n").length;
        Node node = new Node();
        Geometry geometry = new Geometry("", new PopupMesh(rows));
        Material material = MaterialFactory.generateUnshadedMaterial("Textures/effects/popups/speech_bubble_" + rows + ".png");
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        geometry.setMaterial(material);
        node.attachChild(geometry);
        BitmapFont font = MaterialFactory.getAssetManager().loadFont("Interface/fonts/Verdana_18.fnt");
        BitmapText bitmapText = new BitmapText(font);
        bitmapText.setName(TEXT_NAME);
        bitmapText.setSize(textSize);
        bitmapText.setColor(ColorRGBA.Black);
        bitmapText.setBox(new Rectangle((-1 * (width / 2)), 0, width, 1));
        bitmapText.setAlignment(BitmapFont.Align.Center);
        bitmapText.setLocalTranslation(0, 26 + (rows * 18), 1);
        bitmapText.setText(text);
        node.attachChild(bitmapText);
        return node;
    }

    @Override
    protected void updateVisualAttachment(EntityWorld entityWorld, int entity, Spatial visualAttachment) {
        String text = entityWorld.getComponent(entity, PopupComponent.class).getText();
        Node node = (Node) visualAttachment;
        BitmapText bitmapText = (BitmapText) node.getChild(TEXT_NAME);
        bitmapText.setText(text);
    }
}
