/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.gui;

import amara.engine.applications.ingame.client.gui.ScreenController_HUD;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.items.*;
import amara.game.entitysystem.components.players.SelectedUnitComponent;

/**
 *
 * @author Carl
 */
public class DisplayInventorySystem implements EntitySystem{

    public DisplayInventorySystem(int playerEntity, ScreenController_HUD screenController_HUD){
        this.playerEntity = playerEntity;
        this.screenController_HUD = screenController_HUD;
    }
    private final static String NON_EXISTING_ITEM_VISUALISATION_NAME = "none";
    private int playerEntity;
    private ScreenController_HUD screenController_HUD;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        SelectedUnitComponent selectedUnitComponent = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class);
        if(selectedUnitComponent != null){
            int selectedEntity = selectedUnitComponent.getEntityID();
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
