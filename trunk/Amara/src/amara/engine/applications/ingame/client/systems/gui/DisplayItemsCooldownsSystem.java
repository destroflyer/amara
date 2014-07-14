/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.gui;

import amara.engine.applications.ingame.client.gui.ScreenController_HUD;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.items.*;
import amara.game.entitysystem.components.spells.*;

/**
 *
 * @author Carl
 */
public class DisplayItemsCooldownsSystem extends GUIDisplaySystem{

    public DisplayItemsCooldownsSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int selectedEntity){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, InventoryComponent.class, RemainingCooldownComponent.class);
        checkChangedItems(entityWorld, observer.getNew().getComponent(selectedEntity, InventoryComponent.class));
        checkChangedItems(entityWorld, observer.getChanged().getComponent(selectedEntity, InventoryComponent.class));
        InventoryComponent inventoryComponent = entityWorld.getComponent(selectedEntity, InventoryComponent.class);
        if(inventoryComponent != null){
            checkCurrentItemsCooldowns(entityWorld, observer, inventoryComponent.getItemEntities());
        }
        observer.reset();
    }
    
    private void checkChangedItems(EntityWorld entityWorld, InventoryComponent inventoryComponent){
        if(inventoryComponent != null){
            int[] items = inventoryComponent.getItemEntities();
            for(int i=0;i<4;i++){
                boolean hideCooldown = true;
                if(i < items.length){
                    ItemActiveComponent itemActiveComponent = entityWorld.getComponent(items[i], ItemActiveComponent.class);
                    if(itemActiveComponent != null){
                        RemainingCooldownComponent remainingCooldownComponent = entityWorld.getComponent(itemActiveComponent.getSpellEntity(), RemainingCooldownComponent.class);
                        if(remainingCooldownComponent != null){
                            screenController_HUD.showItemCooldown(i, remainingCooldownComponent.getDuration());
                        }
                        else{
                            screenController_HUD.hideItemCooldown(i);
                        }
                    }
                }
                else{
                    screenController_HUD.hideSpellCooldown(i);
                }
            }
        }
    }
    
    private void checkCurrentItemsCooldowns(EntityWorld entityWorld, ComponentMapObserver observer, int[] items){
        for(int i=0;i<4;i++){
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
            screenController_HUD.showItemCooldown(itemIndex, remainingCooldownComponent.getDuration());
        }
    }
    
    private void checkCooldownRemoved(RemainingCooldownComponent remainingCooldownComponent, int itemIndex){
        if(remainingCooldownComponent != null){
            screenController_HUD.hideItemCooldown(itemIndex);
        }
    }
}
