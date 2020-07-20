package amara.applications.ingame.entitysystem.systems.shop;

import java.util.HashMap;
import java.util.LinkedList;

import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.costs.GoldCostComponent;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.shop.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.applications.ingame.entitysystem.systems.effects.triggers.EffectTriggerUtil;
import amara.applications.ingame.entitysystem.systems.items.ItemUtil;
import amara.applications.ingame.entitysystem.systems.units.CostUtil;
import amara.applications.ingame.network.messages.objects.commands.ItemIndex;
import amara.core.Util;
import amara.libraries.entitysystem.*;
import amara.libraries.entitysystem.templates.EntityTemplate;

public class ShopUtil {

    public static final String CATEGORY_TOTAL = "total";
    private static LinkedList<Integer> tmpItemEntities = new LinkedList<>();

    public static boolean isInShopRange(EntityWorld entityWorld, int entity) {
        for (int shopEntity : entityWorld.getEntitiesWithAll(ShopRangeComponent.class, PositionComponent.class)) {
            if (canUseShop(entityWorld, entity, shopEntity)) {
                return true;
            }
        }
        return false;
    }

    public static boolean canUseShop(EntityWorld entityWorld, int entity, int shopEntity) {
        TeamComponent entityTeamComponent = entityWorld.getComponent(entity, TeamComponent.class);
        TeamComponent shopTeamComponent = entityWorld.getComponent(shopEntity, TeamComponent.class);
        if ((shopTeamComponent == null) || ((entityTeamComponent != null) && (entityTeamComponent.getTeamEntity() == shopTeamComponent.getTeamEntity()))) {
            PositionComponent entityPositionComponent = entityWorld.getComponent(entity, PositionComponent.class);
            PositionComponent shopPositionComponent = entityWorld.getComponent(shopEntity, PositionComponent.class);
            if ((entityPositionComponent != null) && (shopPositionComponent != null)) {
                float shopRange = entityWorld.getComponent(shopEntity, ShopRangeComponent.class).getRange();
                return shopPositionComponent.getPosition().distanceSquared(entityPositionComponent.getPosition()) <= (shopRange * shopRange);
            }
        }
        return false;
    }

    public static boolean buy(EntityWorld entityWorld, int entity, String itemId) {
        tmpItemEntities.clear();
        InventoryComponent inventoryComponent = entityWorld.getComponent(entity, InventoryComponent.class);
        if (inventoryComponent != null) {
            for (int itemEntity : inventoryComponent.getItemEntities()) {
                tmpItemEntities.add(itemEntity);
            }
        }
        int itemEntity = entityWorld.createEntity();
        LinkedList<Integer> costEntities = resolveItemRecipeCosts(entityWorld, itemId, tmpItemEntities, itemEntity);
        entityWorld.removeEntity(itemEntity);
        int newItemIndex = 0;
        for (int tmpItemEntity : tmpItemEntities) {
            if (tmpItemEntity != -1) {
                newItemIndex++;
            } else {
                break;
            }
        }
        if (newItemIndex < tmpItemEntities.size()) {
            tmpItemEntities.set(newItemIndex, itemEntity);
        }  else {
            tmpItemEntities.add(itemEntity);
        }
        if ((tmpItemEntities.size() <= ItemUtil.MAX_INVENTORY_SIZE) && CostUtil.isPayable(entityWorld, entity, costEntities)) {
            CostUtil.pay(entityWorld, entity, costEntities);
            EntityTemplate.loadTemplate(entityWorld, itemEntity, "items/" + itemId);
            addToShopGoldExpenses(entityWorld, entity, itemEntity, costEntities);
            entityWorld.setComponent(entity, new InventoryComponent(Util.convertToArray_Integer(tmpItemEntities)));
            entityWorld.setComponent(entity, new RequestUpdateAttributesComponent());
            triggerShopUsageEffects(entityWorld, entity);
            return true;
        }
        return false;
    }

    private static LinkedList<Integer> resolveItemRecipeCosts(EntityWorld entityWorld, String itemID, LinkedList<Integer> inventoryItemEntities, int tmpItemEntity) {
        return resolveItemRecipeCosts(entityWorld, itemID, inventoryItemEntities, tmpItemEntity, new LinkedList<>());
    }

    private static LinkedList<Integer> resolveItemRecipeCosts(EntityWorld entityWorld, String itemID, LinkedList<Integer> inventoryItemEntities, int tmpItemEntity, LinkedList<Integer> costEntities) {
        entityWorld.removeEntity(tmpItemEntity);
        EntityTemplate.loadTemplate(entityWorld, tmpItemEntity, "items/" + itemID);
        ItemRecipeComponent itemRecipeComponent = entityWorld.getComponent(tmpItemEntity, ItemRecipeComponent.class);
        for (String ingredientID : itemRecipeComponent.getItemIDs()) {
            boolean ingredientHasToBeBought = true;
            for (Integer inventoryItemEntity : inventoryItemEntities) {
                if(inventoryItemEntity != -1) {
                    ItemIDComponent itemIDComponent = entityWorld.getComponent(inventoryItemEntity, ItemIDComponent.class);
                    if (itemIDComponent != null) {
                        if (itemIDComponent.getID().equals(ingredientID)) {
                            inventoryItemEntities.remove(inventoryItemEntity);
                            ingredientHasToBeBought = false;
                            break;
                        }
                    }
                }
            }
            if (ingredientHasToBeBought) {
                resolveItemRecipeCosts(entityWorld, ingredientID, inventoryItemEntities, tmpItemEntity, costEntities);
            }
        }
        costEntities.add(itemRecipeComponent.getCombineCostEntity());
        return costEntities;
    }

    public static void sell(EntityWorld entityWorld, int entity, ItemIndex itemIndex) {
        int itemEntity = ItemUtil.getItemEntity(entityWorld, entity, itemIndex);
        if (itemEntity != -1) {
            IsSellableComponent isSellableComponent = entityWorld.getComponent(itemEntity, IsSellableComponent.class);
            if (isSellableComponent != null) {
                // Remove item
                ItemUtil.removeItem(entityWorld, entity, itemIndex, false);
                // Add gold
                GoldComponent goldComponent = entityWorld.getComponent(entity, GoldComponent.class);
                if (goldComponent != null) {
                    entityWorld.setComponent(entity, new GoldComponent(goldComponent.getGold() + isSellableComponent.getGold()));
                    addToShopGoldExpenses(entityWorld, entity, itemEntity, -1 * isSellableComponent.getGold());
                }
                triggerShopUsageEffects(entityWorld, entity);
            }
        }
    }

    private static void addToShopGoldExpenses(EntityWorld entityWorld, int shopperEntity, int itemEntity, LinkedList<Integer> costEntities) {
        for (int costEntity : costEntities) {
            GoldCostComponent goldCostComponent = entityWorld.getComponent(costEntity, GoldCostComponent.class);
            if (goldCostComponent != null) {
                addToShopGoldExpenses(entityWorld, shopperEntity, itemEntity, goldCostComponent.getGold());
            }
        }
    }

    private static void addToShopGoldExpenses(EntityWorld entityWorld, int shopperEntity, int itemEntity, float goldAmount) {
        // Collect categories
        LinkedList<String> categories = new LinkedList<>();
        categories.add(CATEGORY_TOTAL);
        ItemCategoriesComponent itemCategoriesComponent = entityWorld.getComponent(itemEntity, ItemCategoriesComponent.class);
        if (itemCategoriesComponent != null) {
            for (String category : itemCategoriesComponent.getCategories()) {
                categories.add(category);
            }
        }
        // Update component
        ShopGoldExpensesComponent shopGoldExpensesComponent = entityWorld.getComponent(shopperEntity, ShopGoldExpensesComponent.class);
        HashMap<String, Float> goldPerCategory = ((shopGoldExpensesComponent != null) ? shopGoldExpensesComponent.getGoldPerCategory() : new HashMap<>());
        for (String category : categories) {
            goldPerCategory.put(category, goldPerCategory.getOrDefault(category, 0f) + goldAmount);
        }
        entityWorld.setComponent(shopperEntity, new ShopGoldExpensesComponent(goldPerCategory));
    }

    private static void triggerShopUsageEffects(EntityWorld entityWorld, int shopUserEntity) {
        for (int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, ShopUsageTriggerComponent.class)) {
            int triggerSourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
            if (triggerSourceEntity == shopUserEntity) {
                EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, shopUserEntity);
            }
        }
    }
}
