/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.gui;

import amara.engine.applications.ingame.client.gui.ScreenController_HUD;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.items.*;

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
        InventoryComponent inventoryComponent = entityWorld.getComponent(selectedEntity, InventoryComponent.class);
        for(int i=0;i<6;i++){
            String visualisationName = "none";
            if(inventoryComponent != null){
                int[] items = inventoryComponent.getItemEntities();
                if(i < items.length){
                    ItemVisualisationComponent itemVisualisationComponent = entityWorld.getComponent(items[i], ItemVisualisationComponent.class);
                    if(itemVisualisationComponent != null){
                        visualisationName = itemVisualisationComponent.getName();
                    }
                    else{
                        visualisationName = "unknown";
                    }
                }
            }
            screenController_HUD.setInventoryItemImage(i, "Interface/hud/items/" + visualisationName + ".png");
        }
    }
}
