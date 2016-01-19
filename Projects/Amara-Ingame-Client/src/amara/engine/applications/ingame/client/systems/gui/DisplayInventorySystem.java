/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.gui;

import amara.applications.ingame.entitysystem.components.items.*;
import amara.engine.applications.ingame.client.gui.ScreenController_HUD;
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
                String itemImageName = "none";
                int[] items = inventoryComponent.getItemEntities();
                if((i < items.length) && (items[i] != -1)){
                    ItemIDComponent itemIDComponent = entityWorld.getComponent(items[i], ItemIDComponent.class);
                    if(itemIDComponent != null){
                        itemImageName = itemIDComponent.getID();
                    }
                    else{
                        itemImageName = "unknown";
                    }
                }
                screenController_HUD.setInventoryItemImage(i, "Interface/hud/items/" + itemImageName + ".png");
            }
        }
    }
}
