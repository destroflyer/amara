package amara.applications.ingame.entitysystem.systems.items;

import amara.applications.ingame.entitysystem.components.attributes.RequestUpdateAttributesComponent;
import amara.applications.ingame.entitysystem.components.items.BagComponent;
import amara.applications.ingame.entitysystem.components.items.InventoryComponent;
import amara.applications.ingame.entitysystem.systems.general.EntityArrayUtil;
import amara.applications.ingame.network.messages.objects.commands.ItemIndex;
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
        if (!addItemToInventory(entityWorld, entity, itemEntity)) {
            addItemToBag(entityWorld, entity, itemEntity);
        }
    }

    public static boolean addItemToInventory(EntityWorld entityWorld, int entity, int itemEntity) {
        InventoryComponent inventoryComponent = entityWorld.getComponent(entity, InventoryComponent.class);
        if (inventoryComponent != null) {
            int[] inventoryItemEntities = inventoryComponent.getItemEntities();
            if (inventoryItemEntities.length < MAX_INVENTORY_SIZE) {
                int[] newInventoryItemEntities = EntityArrayUtil.add(inventoryItemEntities, itemEntity);
                entityWorld.setComponent(entity, new InventoryComponent(newInventoryItemEntities));
                entityWorld.setComponent(entity, new RequestUpdateAttributesComponent());
                return true;
            }
        }
        return false;
    }

    public static boolean addItemToBag(EntityWorld entityWorld, int entity, int itemEntity) {
        BagComponent bagComponent = entityWorld.getComponent(entity, BagComponent.class);
        if (bagComponent != null) {
            int[] bagItemEntities = bagComponent.getItemEntities();
            if (bagItemEntities.length < MAX_BAG_SIZE) {
                int[] newBagItemEntities = EntityArrayUtil.add(bagItemEntities, itemEntity);
                entityWorld.setComponent(entity, new BagComponent(newBagItemEntities));
                return true;
            }
        }
        return false;
    }

    public static boolean removeItem(EntityWorld entityWorld, int entity, ItemIndex itemIndex, boolean collapse) {
        int itemEntity = getItemEntity(entityWorld, entity, itemIndex);
        if (itemEntity != -1) {
            if (itemIndex.getItemSet() == ItemIndex.ItemSet.INVENTORY) {
                return removeItemFromInventory(entityWorld, entity, itemEntity, collapse);
            } else if (itemIndex.getItemSet() == ItemIndex.ItemSet.BAG) {
                return removeItemFromBag(entityWorld, entity, itemEntity, collapse);
            }
        }
        return false;
    }

    public static int getItemEntity(EntityWorld entityWorld, int entity, ItemIndex itemIndex) {
        int[] itemEntities = null;
        if (itemIndex.getItemSet() == ItemIndex.ItemSet.INVENTORY) {
            InventoryComponent inventoryComponent = entityWorld.getComponent(entity, InventoryComponent.class);
            if (inventoryComponent != null) {
                itemEntities = inventoryComponent.getItemEntities();
            }
        } else if (itemIndex.getItemSet() == ItemIndex.ItemSet.BAG) {
            BagComponent bagComponent = entityWorld.getComponent(entity, BagComponent.class);
            if (bagComponent != null) {
                itemEntities = bagComponent.getItemEntities();
            }
        }
        if (itemEntities != null) {
            if (itemIndex.getIndex() < itemEntities.length) {
                return itemEntities[itemIndex.getIndex()];
            }
        }
        return -1;
    }

    public static boolean removeItemFromInventory(EntityWorld entityWorld, int entity, int itemEntity, boolean collapse) {
        InventoryComponent inventoryComponent = entityWorld.getComponent(entity, InventoryComponent.class);
        if (inventoryComponent != null) {
            int[] oldItemsEntities = inventoryComponent.getItemEntities();
            int[] newItemEntities = EntityArrayUtil.remove(oldItemsEntities, itemEntity, collapse);
            if (newItemEntities != oldItemsEntities) {
                entityWorld.setComponent(entity, new InventoryComponent(newItemEntities));
                entityWorld.setComponent(entity, new RequestUpdateAttributesComponent());
            }
            return true;
        }
        return false;
    }

    public static boolean removeItemFromBag(EntityWorld entityWorld, int entity, int itemEntity, boolean collapse) {
        BagComponent bagComponent = entityWorld.getComponent(entity, BagComponent.class);
        if (bagComponent != null) {
            int[] oldItemsEntities = bagComponent.getItemEntities();
            int[] newItemEntities = EntityArrayUtil.remove(oldItemsEntities, itemEntity, collapse);
            if (newItemEntities != oldItemsEntities) {
                entityWorld.setComponent(entity, new BagComponent(newItemEntities));
            }
            return true;
        }
        return false;
    }

    public static void swapItems(EntityWorld entityWorld, int entity, ItemIndex itemIndex1, ItemIndex itemIndex2) {
        int itemEntity1 = getItemEntity(entityWorld, entity, itemIndex1);
        int itemEntity2 = getItemEntity(entityWorld, entity, itemIndex2);
        setItem(entityWorld, entity, itemIndex1, itemEntity2);
        setItem(entityWorld, entity, itemIndex2, itemEntity1);
    }

    public static void setItem(EntityWorld entityWorld, int entity, ItemIndex itemIndex, int itemEntity) {
        if (itemIndex.getItemSet() == ItemIndex.ItemSet.INVENTORY) {
            setItemInInventory(entityWorld, entity, itemIndex.getIndex(), itemEntity);
        } else if (itemIndex.getItemSet() == ItemIndex.ItemSet.BAG) {
            setItemInBag(entityWorld, entity, itemIndex.getIndex(), itemEntity);
        }
    }

    public static boolean setItemInInventory(EntityWorld entityWorld, int entity, int index, int itemEntity) {
        InventoryComponent inventoryComponent = entityWorld.getComponent(entity, InventoryComponent.class);
        if (inventoryComponent != null) {
            int[] oldItemsEntities = inventoryComponent.getItemEntities();
            int[] newItemEntities = EntityArrayUtil.set(oldItemsEntities, index, itemEntity);
            entityWorld.setComponent(entity, new InventoryComponent(newItemEntities));
            entityWorld.setComponent(entity, new RequestUpdateAttributesComponent());
            return true;
        }
        return false;
    }

    public static boolean setItemInBag(EntityWorld entityWorld, int entity, int index, int itemEntity) {
        BagComponent bagComponent = entityWorld.getComponent(entity, BagComponent.class);
        if (bagComponent != null) {
            int[] oldItemsEntities = bagComponent.getItemEntities();
            int[] newItemEntities = EntityArrayUtil.set(oldItemsEntities, index, itemEntity);
            entityWorld.setComponent(entity, new BagComponent(newItemEntities));
            return true;
        }
        return false;
    }
}
