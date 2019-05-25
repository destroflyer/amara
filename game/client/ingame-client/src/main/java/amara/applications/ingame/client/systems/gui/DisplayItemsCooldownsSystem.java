/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplayItemsCooldownsSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayItemsCooldownsSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, InventoryComponent.class, RemainingCooldownComponent.class);
        checkChangedItems(entityWorld, observer.getNew().getComponent(characterEntity, InventoryComponent.class));
        checkChangedItems(entityWorld, observer.getChanged().getComponent(characterEntity, InventoryComponent.class));
        InventoryComponent inventoryComponent = entityWorld.getComponent(characterEntity, InventoryComponent.class);
        if(inventoryComponent != null){
            checkCurrentItemsCooldowns(entityWorld, observer, inventoryComponent.getItemEntities());
        }
    }
    
    private void checkChangedItems(EntityWorld entityWorld, InventoryComponent inventoryComponent){
        if(inventoryComponent != null){
            int[] items = inventoryComponent.getItemEntities();
            for(int i=0;i<6;i++){
                if(i < items.length){
                    ItemActiveComponent itemActiveComponent = entityWorld.getComponent(items[i], ItemActiveComponent.class);
                    if(itemActiveComponent != null){
                        RemainingCooldownComponent remainingCooldownComponent = entityWorld.getComponent(itemActiveComponent.getSpellEntity(), RemainingCooldownComponent.class);
                        if(remainingCooldownComponent != null){
                            screenController.showItemCooldown(i, remainingCooldownComponent.getDuration());
                        }
                        else{
                            screenController.hideItemCooldown(i);
                        }
                    }
                }
                else{
                    screenController.hideItemCooldown(i);
                }
            }
        }
    }
    
    private void checkCurrentItemsCooldowns(EntityWorld entityWorld, ComponentMapObserver observer, int[] items){
        for(int i=0;i<6;i++){
            if(i < items.length){
                ItemActiveComponent itemActiveComponent = entityWorld.getComponent(items[i], ItemActiveComponent.class);
                if(itemActiveComponent != null){
                    checkCooldownChanged(observer.getNew().getComponent(itemActiveComponent.getSpellEntity(), RemainingCooldownComponent.class), i);
                    checkCooldownChanged(observer.getChanged().getComponent(itemActiveComponent.getSpellEntity(), RemainingCooldownComponent.class), i);
                    checkCooldownRemoved(observer.getRemoved().getComponent(itemActiveComponent.getSpellEntity(), RemainingCooldownComponent.class), i);
                }
            }
        }
    }
    
    private void checkCooldownChanged(RemainingCooldownComponent remainingCooldownComponent, int itemIndex){
        if(remainingCooldownComponent != null){
            screenController.showItemCooldown(itemIndex, remainingCooldownComponent.getDuration());
        }
    }
    
    private void checkCooldownRemoved(RemainingCooldownComponent remainingCooldownComponent, int itemIndex){
        if(remainingCooldownComponent != null){
            screenController.hideItemCooldown(itemIndex);
        }
    }
}
