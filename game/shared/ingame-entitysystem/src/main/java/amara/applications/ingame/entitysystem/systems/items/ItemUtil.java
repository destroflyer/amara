package amara.applications.ingame.entitysystem.systems.items;

import amara.applications.ingame.entitysystem.components.attributes.RequestUpdateAttributesComponent;
import amara.applications.ingame.entitysystem.components.items.BagComponent;
import amara.applications.ingame.entitysystem.components.items.InventoryComponent;
import amara.libraries.entitysystem.EntityWorld;

public class ItemUtil {

    public static final int MAX_INVENTORY_SIZE = 6;
    public static final int MAX_BAG_SIZE = 100;

    public static void addItems(EntityWorld entityWorld, int entity, int... itemEntities) {
        for (int itemEntity : itemEntities) {
            addItem(entityWorld, entity, itemEntity);
        }
    }

    public static void addItem(EntityWorld entityWorld, int entity, int itemEntity) {
        InventoryComponent inventoryComponent = entityWorld.getComponent(entity, InventoryComponent.class);
        if (inventoryComponent != null) {
            int[] inventoryItemEntities = inventoryComponent.getItemEntities();
            if (inventoryItemEntities.length < MAX_INVENTORY_SIZE) {
                int[] newInventoryItemEntities = addEntity(inventoryItemEntities, itemEntity);
                entityWorld.setComponent(entity, new InventoryComponent(newInventoryItemEntities));
                entityWorld.setComponent(entity, new RequestUpdateAttributesComponent());
                return;
            }
        }
        BagComponent bagComponent = entityWorld.getComponent(entity, BagComponent.class);
        if (bagComponent != null) {
            int[] bagItemEntities = bagComponent.getItemEntities();
            if (bagItemEntities.length < MAX_BAG_SIZE) {
                int[] newBagItemEntities = addEntity(bagItemEntities, itemEntity);
                entityWorld.setComponent(entity, new BagComponent(newBagItemEntities));
            }
        }
    }

    private static int[] addEntity(int[] entities, int entity) {
        int[] newEntities = new int[entities.length + 1];
        System.arraycopy(entities, 0, newEntities, 0, entities.length);
        newEntities[entities.length] = entity;
        return newEntities;
    }
}
