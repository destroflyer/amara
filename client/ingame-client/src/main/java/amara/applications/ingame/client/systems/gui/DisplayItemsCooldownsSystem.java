package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.libraries.entitysystem.*;

public class DisplayItemsCooldownsSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayItemsCooldownsSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, InventoryComponent.class, RemainingCooldownComponent.class);
        checkChangedItems(entityWorld, observer.getNew().getComponent(characterEntity, InventoryComponent.class));
        checkChangedItems(entityWorld, observer.getChanged().getComponent(characterEntity, InventoryComponent.class));
        InventoryComponent inventoryComponent = entityWorld.getComponent(characterEntity, InventoryComponent.class);
        if (inventoryComponent != null) {
            checkCurrentItemsCooldowns(entityWorld, observer, inventoryComponent.getItemEntities());
        }
    }

    private void checkChangedItems(EntityWorld entityWorld, InventoryComponent inventoryComponent) {
        if (inventoryComponent != null) {
            int[] itemEntities = inventoryComponent.getItemEntities();
            for (int i = 0; i < itemEntities.length; i++) {
                if (itemEntities[i] != -1) {
                    ItemActiveComponent itemActiveComponent = entityWorld.getComponent(itemEntities[i], ItemActiveComponent.class);
                    if (itemActiveComponent != null) {
                        RemainingCooldownComponent remainingCooldownComponent = entityWorld.getComponent(itemActiveComponent.getSpellEntity(), RemainingCooldownComponent.class);
                        if (remainingCooldownComponent != null) {
                            screenController.showPlayer_ItemCooldown(i, remainingCooldownComponent.getDuration());
                        } else {
                            screenController.hidePlayer_ItemCooldown(i);
                        }
                    } else {
                        screenController.hidePlayer_ItemCooldown(i);
                    }
                }
            }
        }
    }

    private void checkCurrentItemsCooldowns(EntityWorld entityWorld, ComponentMapObserver observer, int[] itemEntities) {
        for (int i = 0; i < itemEntities.length; i++) {
            if (itemEntities[i] != -1) {
                ItemActiveComponent itemActiveComponent = entityWorld.getComponent(itemEntities[i], ItemActiveComponent.class);
                if (itemActiveComponent != null) {
                    checkCooldownChanged(observer.getNew().getComponent(itemActiveComponent.getSpellEntity(), RemainingCooldownComponent.class), i);
                    checkCooldownChanged(observer.getChanged().getComponent(itemActiveComponent.getSpellEntity(), RemainingCooldownComponent.class), i);
                    checkCooldownRemoved(observer.getRemoved().getComponent(itemActiveComponent.getSpellEntity(), RemainingCooldownComponent.class), i);
                }
            }
        }
    }

    private void checkCooldownChanged(RemainingCooldownComponent remainingCooldownComponent, int itemIndex) {
        if (remainingCooldownComponent != null) {
            screenController.showPlayer_ItemCooldown(itemIndex, remainingCooldownComponent.getDuration());
        }
    }

    private void checkCooldownRemoved(RemainingCooldownComponent remainingCooldownComponent, int itemIndex) {
        if (remainingCooldownComponent != null) {
            screenController.hidePlayer_ItemCooldown(itemIndex);
        }
    }
}
