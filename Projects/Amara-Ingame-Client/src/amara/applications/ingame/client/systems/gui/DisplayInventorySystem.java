/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplayInventorySystem extends GUIDisplaySystem{

    public DisplayInventorySystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int selectedEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, InventoryComponent.class);
        check(entityWorld, observer.getNew().getComponent(selectedEntity, InventoryComponent.class));
        check(entityWorld, observer.getChanged().getComponent(selectedEntity, InventoryComponent.class));
    }
    
    private void check(EntityWorld entityWorld, InventoryComponent inventoryComponent){
        if(inventoryComponent != null){
            for(int i=0;i<6;i++){
                String imageFilePath = getItemImageFilePath(entityWorld, inventoryComponent.getItemEntities(), i);
                screenController_HUD.setInventoryItemImage(i, imageFilePath);
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
