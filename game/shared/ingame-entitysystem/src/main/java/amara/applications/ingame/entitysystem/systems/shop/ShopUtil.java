/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.shop;

import java.util.HashMap;
import java.util.LinkedList;

import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.shop.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.applications.ingame.entitysystem.systems.effects.triggers.EffectTriggerUtil;
import amara.applications.ingame.entitysystem.systems.items.ItemUtil;
import amara.core.Util;
import amara.libraries.entitysystem.*;
import amara.libraries.entitysystem.templates.EntityTemplate;

/**
 *
 * @author Carl
 */
public class ShopUtil{

    public static final String CATEGORY_TOTAL = "total";
    private static LinkedList<Integer> tmpItemEntities = new LinkedList<>();

    public static boolean isInShopRange(EntityWorld entityWorld, int entity){
        for(int shopEntity : entityWorld.getEntitiesWithAll(ShopRangeComponent.class, PositionComponent.class)){
            if(canUseShop(entityWorld, entity, shopEntity)){
                return true;
            }
        }
        return false;
    }
    
    public static boolean canUseShop(EntityWorld entityWorld, int entity, int shopEntity){
        TeamComponent entityTeamComponent = entityWorld.getComponent(entity, TeamComponent.class);
        TeamComponent shopTeamComponent = entityWorld.getComponent(shopEntity, TeamComponent.class);
        if((shopTeamComponent == null) || ((entityTeamComponent != null) && (entityTeamComponent.getTeamEntity() == shopTeamComponent.getTeamEntity()))){
            PositionComponent entityPositionComponent = entityWorld.getComponent(entity, PositionComponent.class);
            PositionComponent shopPositionComponent = entityWorld.getComponent(shopEntity, PositionComponent.class);
            if((entityPositionComponent != null) && (shopPositionComponent != null)){
                float shopRange = entityWorld.getComponent(shopEntity, ShopRangeComponent.class).getRange();
                return shopPositionComponent.getPosition().distanceSquared(entityPositionComponent.getPosition()) <= (shopRange * shopRange);
            }
        }
        return false;
    }

    public static boolean buy(EntityWorld entityWorld, int entity, String itemID){
        tmpItemEntities.clear();
        int newItemIndex = -1;
        InventoryComponent inventoryComponent = entityWorld.getComponent(entity, InventoryComponent.class);
        if(inventoryComponent != null){
            for(int i=0;i<inventoryComponent.getItemEntities().length;i++){
                int itemEntity = inventoryComponent.getItemEntities()[i];
                tmpItemEntities.add(itemEntity);
                if((itemEntity == -1) && (newItemIndex == -1)){
                    newItemIndex = i;
                }
            }
        }
        int itemEntity = entityWorld.createEntity();
        float goldCost = resolveItemRecipe(entityWorld, itemID, tmpItemEntities, itemEntity);
        entityWorld.removeEntity(itemEntity);
        if(newItemIndex != -1){
            tmpItemEntities.set(newItemIndex, itemEntity);
        }
        else{
            tmpItemEntities.add(itemEntity);
        }
        int inventorySize = 0;
        for(int tmpItemEntity : tmpItemEntities){
            if(tmpItemEntity != -1){
                inventorySize++;
            }
        }
        if(inventorySize <= ItemUtil.MAX_INVENTORY_SIZE){
            GoldComponent goldComponent = entityWorld.getComponent(entity, GoldComponent.class);
            if((goldCost == 0) || ((goldComponent != null) && (goldComponent.getGold() >= goldCost))){
                EntityTemplate.loadTemplate(entityWorld, itemEntity, "items/" + itemID);
                addItemGoldViaShop(entityWorld, entity, itemEntity, -1 * goldCost);
                entityWorld.setComponent(entity, new InventoryComponent(Util.convertToArray_Integer(tmpItemEntities)));
                entityWorld.setComponent(entity, new RequestUpdateAttributesComponent());
                triggerShopUsageEffects(entityWorld, entity);
                return true;
            }
        }
        return false;
    }
    
    private static float resolveItemRecipe(EntityWorld entityWorld, String itemID, LinkedList<Integer> inventoryItemEntities, int tmpItemEntity){
        entityWorld.removeEntity(tmpItemEntity);
        EntityTemplate.loadTemplate(entityWorld, tmpItemEntity, "items/" + itemID);
        ItemRecipeComponent itemRecipeComponent = entityWorld.getComponent(tmpItemEntity, ItemRecipeComponent.class);
        float goldCost = itemRecipeComponent.getGold();
        for(String ingredientID : itemRecipeComponent.getItemIDs()){
            boolean ingredientHasToBeBought = true;
            for(Integer inventoryItemEntity : inventoryItemEntities){
                if(inventoryItemEntity != -1){
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
            if(ingredientHasToBeBought){
                goldCost += resolveItemRecipe(entityWorld, ingredientID, inventoryItemEntities, tmpItemEntity);
            }
        }
        return goldCost;
    }

    public static void sell(EntityWorld entityWorld, int entity, int inventoryIndex){
        InventoryComponent inventoryComponent = entityWorld.getComponent(entity, InventoryComponent.class);
        if(inventoryComponent != null){
            int[] oldItemsEntities = inventoryComponent.getItemEntities();
            if((inventoryIndex >= 0) && (inventoryIndex < oldItemsEntities.length)){
                int itemEntity = oldItemsEntities[inventoryIndex];
                IsSellableComponent isSellableComponent = entityWorld.getComponent(itemEntity, IsSellableComponent.class);
                if(isSellableComponent != null){
                    int[] newItemEntities = new int[oldItemsEntities.length - 1];
                    int currentIndex = 0;
                    for(int i=0;i<oldItemsEntities.length;i++){
                        if(i != inventoryIndex){
                            newItemEntities[currentIndex] = oldItemsEntities[i];
                            currentIndex++;
                        }
                    }
                    GoldComponent goldComponent = entityWorld.getComponent(entity, GoldComponent.class);
                    if(goldComponent != null){
                        addItemGoldViaShop(entityWorld, entity, itemEntity, isSellableComponent.getGold());
                    }
                    entityWorld.setComponent(entity, new InventoryComponent(newItemEntities));
                    entityWorld.setComponent(entity, new RequestUpdateAttributesComponent());
                    triggerShopUsageEffects(entityWorld, entity);
                }
            }
        }
    }

    private static void addItemGoldViaShop(EntityWorld entityWorld, int shopperEntity, int itemEntity, float goldAmount) {
        GoldComponent goldComponent = entityWorld.getComponent(shopperEntity, GoldComponent.class);
        entityWorld.setComponent(shopperEntity, new GoldComponent(goldComponent.getGold() + goldAmount));
        addToShopGoldExpenses(entityWorld, shopperEntity, itemEntity, -1 * goldAmount);
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
