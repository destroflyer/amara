/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import java.util.HashMap;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.engine.applications.ingame.client.systems.visualisation.controls.GoldChangeAttachmentControl;
import amara.engine.materials.MaterialFactory;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.GoldComponent;

/**
 *
 * @author Carl
 */
public class GoldChangeSystem implements EntitySystem{

    public GoldChangeSystem(HUDAttachmentsSystem hudAttachmentsSystem, EntitySceneMap entitySceneMap){
        this.hudAttachmentsSystem = hudAttachmentsSystem;
        this.entitySceneMap = entitySceneMap;
    }
    private HUDAttachmentsSystem hudAttachmentsSystem;
    private EntitySceneMap entitySceneMap;
    private HashMap<Integer, Integer> cachedGold = new HashMap<Integer, Integer>();
    private int nextChangeID;

    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, GoldComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll()){
            onGoldChange(entityWorld, entity, entityWorld.getComponent(entity, GoldComponent.class).getGold());
        }
        for(int entity : observer.getChanged().getEntitiesWithAll()){
            onGoldChange(entityWorld, entity, entityWorld.getComponent(entity, GoldComponent.class).getGold());
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll()){
            onGoldChange(entityWorld, entity, 0);
        }
        observer.reset();
    }
    
    private void onGoldChange(EntityWorld entityWorld, int entity, int currentGold){
        Integer oldGold = cachedGold.get(entity);
        if(oldGold == null){
            oldGold = 0;
        }
        int goldChange = (currentGold - oldGold);
        if(goldChange != 0){
            displayGoldChange(entityWorld, entity, goldChange);
        }
        cachedGold.put(entity, currentGold);
    }
    
    private void displayGoldChange(EntityWorld entityWorld, int entity, int goldChange){
        Vector3f worldOffset = MaximumHealthBarSystem.getWorldOffset(entityWorld, entity, entitySceneMap);
        hudAttachmentsSystem.attach(new HUDAttachmentInfo(entity, "gold_change_" + nextChangeID, worldOffset, new Vector3f(0, 20, 0)), createHUDAttachment(goldChange));
        nextChangeID++;
    }
    
    private Spatial createHUDAttachment(int goldChange){
        Node node = new Node();
        BitmapFont font = MaterialFactory.getAssetManager().loadFont("Interface/fonts/Verdana_18.fnt");
        BitmapText bitmapText = new BitmapText(font);
        bitmapText.setSize(12);
        bitmapText.setColor(new ColorRGBA(1, 1, 0, 1));
        int boxWidth = 100;
        bitmapText.setBox(new Rectangle((boxWidth / -2), 20, boxWidth, 1));
        bitmapText.setAlignment(BitmapFont.Align.Center);
        bitmapText.setText(((goldChange > 0)?"+":"") + goldChange + " g");
        node.attachChild(bitmapText);
        node.addControl(new GoldChangeAttachmentControl(20, 2.25f));
        return node;
    }
}
