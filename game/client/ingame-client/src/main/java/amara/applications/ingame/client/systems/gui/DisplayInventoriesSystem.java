/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.client.gui.objects.ItemDescription;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplayInventoriesSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayInventoriesSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD);
    }
    public static final int ITEM_DESCRIPTION_LINE_LENGTH = 40;

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, InventoryComponent.class);
        checkChange(entityWorld, observer, "player", characterEntity);
        checkChange(entityWorld, observer, "inspection", getInspectedEntity());
    }

    private void checkChange(EntityWorld entityWorld, ComponentMapObserver observer, String uiPrefix, int entity) {
        checkChange(entityWorld, uiPrefix, observer.getNew().getComponent(entity, InventoryComponent.class));
        checkChange(entityWorld, uiPrefix, observer.getChanged().getComponent(entity, InventoryComponent.class));
    }

    private void checkChange(EntityWorld entityWorld, String uiPrefix, InventoryComponent changedInventoryComponent) {
        if (changedInventoryComponent != null) {
            check(entityWorld, uiPrefix, changedInventoryComponent);
        }
    }

    @Override
    protected void onInspectionUpdated(EntityWorld entityWorld, int inspectedEntity) {
        super.onInspectionUpdated(entityWorld, inspectedEntity);
        check(entityWorld, "inspection", entityWorld.getComponent(inspectedEntity, InventoryComponent.class));
    }

    private void check(EntityWorld entityWorld, String uiPrefix, InventoryComponent inventoryComponent) {
        for(int i = 0; i < 6; i++) {
            String imageFilePath = getItemImageFilePath(entityWorld, inventoryComponent, i);
            screenController.setInventoryItem_Image(uiPrefix, i, imageFilePath);
            if ((inventoryComponent != null) && (i < inventoryComponent.getItemEntities().length) && (inventoryComponent.getItemEntities()[i] != -1)) {
                String description = ItemDescription.generate_NameAndDescription(entityWorld, inventoryComponent.getItemEntities()[i], ITEM_DESCRIPTION_LINE_LENGTH);
                screenController.showInventoryItem_Description(uiPrefix, i, description);
            } else {
                screenController.hideInventoryItem_Description(uiPrefix, i);
            }
        }
    }

    public static String getItemImageFilePath(EntityWorld entityWorld, InventoryComponent inventoryComponent, int itemIndex) {
        String fileName = "none";
        if (inventoryComponent != null) {
            int[] items = inventoryComponent.getItemEntities();
            if ((itemIndex < items.length) && (items[itemIndex] != -1)) {
                ItemIDComponent itemIDComponent = entityWorld.getComponent(items[itemIndex], ItemIDComponent.class);
                if (itemIDComponent != null) {
                    fileName = itemIDComponent.getID();
                } else {
                    fileName = "unknown";
                }
            }
        }
        return "Interface/hud/items/" + fileName + ".png";
    }
}
