/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.systems.gui;

import amara.engine.client.gui.ScreenController_HUD;
import amara.engine.client.systems.*;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.items.*;

/**
 *
 * @author Carl
 */
public class DisplayInventorySystem implements EntitySystem{

    public DisplayInventorySystem(SelectedUnitSystem selectedUnitSystem, ScreenController_HUD screenController_HUD){
        this.selectedUnitSystem = selectedUnitSystem;
        this.screenController_HUD = screenController_HUD;
    }
    private final static String NON_EXISTING_ITEM_VISUALISATION_NAME = "none";
    private SelectedUnitSystem selectedUnitSystem;
    private ScreenController_HUD screenController_HUD;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        int selectedEntity = selectedUnitSystem.getSelectedEntity();
        if(selectedEntity != -1){
            InventoryComponent inventoryComponent = entityWorld.getComponent(selectedEntity, InventoryComponent.class);
            for(int i=0;i<6;i++){
                String itemVisualsiationName = NON_EXISTING_ITEM_VISUALISATION_NAME;
                if(inventoryComponent != null){
                    int[] items = inventoryComponent.getItemEntities();
                    if(i < items.length){
                        itemVisualsiationName = entityWorld.getComponent(items[i], ItemVisualisationComponent.class).getName();
                    }
                }
                screenController_HUD.setInventoryItemImage(i, "Interface/hud/items/" + itemVisualsiationName + ".png");
            }
        }
    }
}
