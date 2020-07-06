package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.libraries.entitysystem.*;

public class DisplayInventoriesSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayInventoriesSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, InventoryComponent.class);
        checkChange(entityWorld, observer, true, characterEntity);
        checkChange(entityWorld, observer, false, getInspectedEntity());
    }

    private void checkChange(EntityWorld entityWorld, ComponentMapObserver observer, boolean playerOrInspection, int entity) {
        checkChange(entityWorld, playerOrInspection, observer.getNew().getComponent(entity, InventoryComponent.class));
        checkChange(entityWorld, playerOrInspection, observer.getChanged().getComponent(entity, InventoryComponent.class));
    }

    private void checkChange(EntityWorld entityWorld, boolean playerOrInspection, InventoryComponent changedInventoryComponent) {
        if (changedInventoryComponent != null) {
            check(entityWorld, playerOrInspection, changedInventoryComponent);
        }
    }

    @Override
    protected void onInspectionUpdated(EntityWorld entityWorld, int inspectedEntity) {
        super.onInspectionUpdated(entityWorld, inspectedEntity);
        check(entityWorld, false, entityWorld.getComponent(inspectedEntity, InventoryComponent.class));
    }

    private void check(EntityWorld entityWorld, boolean playerOrInspection, InventoryComponent inventoryComponent) {
        int[] itemEntities = ((inventoryComponent != null) ? inventoryComponent.getItemEntities() : new int[0]);
        if (playerOrInspection) {
            screenController.generatePlayerInventory(entityWorld, itemEntities);
        } else {
            screenController.generateInspectionInventory(entityWorld, itemEntities);
        }
    }
}
