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
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, InventoryComponent.class);
        check(entityWorld, observer.getNew().getComponent(selectedEntity, InventoryComponent.class));
        check(entityWorld, observer.getChanged().getComponent(selectedEntity, InventoryComponent.class));
        observer.reset();
    }
    
    private void check(EntityWorld entityWorld, InventoryComponent inventoryComponent){
        if(inventoryComponent != null){
            for(int i=0;i<6;i++){
                String visualisationName = "none";
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
                screenController_HUD.setInventoryItemImage(i, "Interface/hud/items/" + visualisationName + ".png");
            }
        }
    }
}
