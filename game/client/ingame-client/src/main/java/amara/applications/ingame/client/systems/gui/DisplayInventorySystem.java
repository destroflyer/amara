/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.client.gui.objects.ItemDescription;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplayInventorySystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayInventorySystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }
    public static final int ITEM_DESCRIPTION_LINE_LENGTH = 40;

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, InventoryComponent.class);
        check(entityWorld, observer.getNew().getComponent(characterEntity, InventoryComponent.class));
        check(entityWorld, observer.getChanged().getComponent(characterEntity, InventoryComponent.class));
    }
    
    private void check(EntityWorld entityWorld, InventoryComponent inventoryComponent){
        if(inventoryComponent != null){
            for(int i=0;i<6;i++){
                String imageFilePath = getItemImageFilePath(entityWorld, inventoryComponent.getItemEntities(), i);
                screenController.setInventoryItem_Image(i, imageFilePath);
                if((i < inventoryComponent.getItemEntities().length) && (inventoryComponent.getItemEntities()[i] != -1)){
                    String description = ItemDescription.generate_NameAndDescription(entityWorld, inventoryComponent.getItemEntities()[i], ITEM_DESCRIPTION_LINE_LENGTH);
                    screenController.showInventoryItem_Description(i, description);
                }
                else{
                    screenController.hideInventoryItem_Description(i);
                }
            }
        }
    }
    
    public static String getItemImageFilePath(EntityWorld entityWorld, int[] items, int itemIndex){
        String filePath = "Interface/hud/items/";
        if((itemIndex < items.length) && (items[itemIndex] != -1)){
            ItemIDComponent itemIDComponent = entityWorld.getComponent(items[itemIndex], ItemIDComponent.class);
            if(itemIDComponent != null){
                 filePath += itemIDComponent.getID();
            }
            else{
                filePath += "unknown";
            }
        }
        else{
            filePath += "none";
        }
        filePath += ".png";
        return filePath;
    }
}
