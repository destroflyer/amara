/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.client.gui.objects.ItemDescription;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplayScoreboardInventoriesSystem extends PlayersDisplaySystem{

    public DisplayScoreboardInventoriesSystem(ScreenController_HUD screenController_HUD){
        super(screenController_HUD);
    }
    private ComponentMapObserver observer;

    @Override
    protected void preUpdate(EntityWorld entityWorld, float deltaSeconds){
        super.preUpdate(entityWorld, deltaSeconds);
        observer = entityWorld.requestObserver(this, InventoryComponent.class);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int playerEntity){
        int playerIndex = entityWorld.getComponent(playerEntity, PlayerIndexComponent.class).getIndex();
        int selectedEntity = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class).getEntity();
        if(hasComponentChanged(observer, selectedEntity, InventoryComponent.class)){
            update_Inventory(entityWorld, playerIndex, selectedEntity);
        }
    }
    
    private void update_Inventory(EntityWorld entityWorld, int playerIndex,int scoreEntity){
        InventoryComponent inventoryComponent = entityWorld.getComponent(scoreEntity, InventoryComponent.class);
        for(int i=0;i<6;i++){
            String imageFilePath = DisplayInventorySystem.getItemImageFilePath(entityWorld, inventoryComponent.getItemEntities(), i);
            screenController_HUD.setScoreboard_InventoryItem_Image(playerIndex, i, imageFilePath);
            if((i < inventoryComponent.getItemEntities().length) && (inventoryComponent.getItemEntities()[i] != -1)){
                String description = ItemDescription.generate_NameAndDescription(entityWorld, inventoryComponent.getItemEntities()[i]);
                screenController_HUD.showScoreboard_InventoryItem_Description(playerIndex, i, description);
            }
            else{
                screenController_HUD.hideScoreboard_InventoryItem_Description(playerIndex, i);
            }
        }
    }
}
