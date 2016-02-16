/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.shop;

import java.util.LinkedList;
import amara.core.Util;
import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.shop.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;
import amara.libraries.entitysystem.templates.EntityTemplate;

/**
 *
 * @author Carl
 */
public class ShopUtil{
    
    private static LinkedList<Integer> tmpItemEntities = new LinkedList<Integer>();
    
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
        int inventorySize = 0;
        int newItemIndex = -1;
        InventoryComponent inventoryComponent = entityWorld.getComponent(entity, InventoryComponent.class);
        if(inventoryComponent != null){
            for(int i=0;i<inventoryComponent.getItemEntities().length;i++){
                int itemEntity = inventoryComponent.getItemEntities()[i];
                tmpItemEntities.add(itemEntity);
                if(itemEntity != -1){
                    inventorySize++;
                }
                else if(newItemIndex == -1){
                    newItemIndex = i;
                }
            }
        }
        int itemEntity = entityWorld.createEntity();
        float goldCost = resolveItemRecipe(entityWorld, itemID, tmpItemEntities, itemEntity);
        entityWorld.removeEntity(itemEntity);
        if(inventorySize <= 5){
            GoldComponent goldComponent = entityWorld.getComponent(entity, GoldComponent.class);
            if((goldCost == 0) || ((goldComponent != null) && (goldComponent.getGold() > goldCost))){
                EntityTemplate.loadTemplate(entityWorld, itemEntity, "items/" + itemID);
                if(newItemIndex != -1){
                    tmpItemEntities.set(newItemIndex, itemEntity);
                }
                else{
                    tmpItemEntities.add(itemEntity);
                }
                if(goldComponent != null){
                    entityWorld.setComponent(entity, new GoldComponent(goldComponent.getGold() - goldCost));
                }
                entityWorld.setComponent(entity, new InventoryComponent(Util.convertToArray(tmpItemEntities)));
                entityWorld.setComponent(entity, new RequestUpdateAttributesComponent());
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
            boolean ingrendientHasToBeBought = true;
            for(Integer inventoryItemEntity : inventoryItemEntities){
                if(inventoryItemEntity != -1){
                    String inventoryItemID = entityWorld.getComponent(inventoryItemEntity, ItemIDComponent.class).getID();
                    if(inventoryItemID.equals(ingredientID)){
                        inventoryItemEntities.remove(inventoryItemEntity);
                        ingrendientHasToBeBought = false;
                        break;
                    }
                }
            }
            if(ingrendientHasToBeBought){
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
                        entityWorld.setComponent(entity, new GoldComponent(goldComponent.getGold() + isSellableComponent.getGold()));
                    }
                    entityWorld.setComponent(entity, new InventoryComponent(newItemEntities));
                    entityWorld.setComponent(entity, new RequestUpdateAttributesComponent());
                }
            }
        }
    }
}
