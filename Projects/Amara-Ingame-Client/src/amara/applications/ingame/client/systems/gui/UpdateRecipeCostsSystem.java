/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.gui.ScreenController_Shop;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class UpdateRecipeCostsSystem implements EntitySystem{

    public UpdateRecipeCostsSystem(int playerEntity, ScreenController_Shop screenController_Shop){
        this.playerEntity = playerEntity;
        this.screenController_Shop = screenController_Shop;
    }
    protected int playerEntity;
    protected ScreenController_Shop screenController_Shop;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        SelectedUnitComponent selectedUnitComponent = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class);
        if(selectedUnitComponent != null){
            ComponentMapObserver observer = entityWorld.requestObserver(this, InventoryComponent.class);
            check(entityWorld, observer.getNew().getComponent(selectedUnitComponent.getEntity(), InventoryComponent.class));
            check(entityWorld, observer.getChanged().getComponent(selectedUnitComponent.getEntity(), InventoryComponent.class));
            check(entityWorld, observer.getRemoved().getComponent(selectedUnitComponent.getEntity(), InventoryComponent.class));
        }
    }
    
    private void check(EntityWorld entityWorld, InventoryComponent inventoryComponent){
        if(inventoryComponent != null){
            screenController_Shop.updateRecipeCosts(entityWorld, inventoryComponent.getItemEntities());
        }
    }
}
