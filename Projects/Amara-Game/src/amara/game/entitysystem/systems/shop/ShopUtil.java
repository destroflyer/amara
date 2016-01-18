/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.shop;

import java.util.LinkedList;
import amara.core.Util;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.items.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.shop.*;
import amara.game.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;
import amara.libraries.entitysystem.templates.EntityTemplate;

/**
 *
 * @author Carl
 */
public class ShopUtil{
    
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
        LinkedList<Integer> inventoryItemEntities = new LinkedList<Integer>();
        InventoryComponent inventoryComponent = entityWorld.getComponent(entity, InventoryComponent.class);
        if(inventoryComponent != null){
            for(int inventoryItemEntity : inventoryComponent.getItemEntities()){
                inventoryItemEntities.add(inventoryItemEntity);
            }
        }
        int itemEntity = entityWorld.createEntity();
        int goldCost = resolveItemRecipe(entityWorld, itemID, inventoryItemEntities, itemEntity);
        entityWorld.removeEntity(itemEntity);
        if(inventoryItemEntities.size() <= 5){
            GoldComponent goldComponent = entityWorld.getComponent(entity, GoldComponent.class);
            if((goldCost == 0) || ((goldComponent != null) && (goldComponent.getGold() > goldCost))){
                EntityTemplate.loadTemplate(entityWorld, itemEntity, "items/" + itemID);
                inventoryItemEntities.add(itemEntity);
                if(goldComponent != null){
                    entityWorld.setComponent(entity, new GoldComponent(goldComponent.getGold() - goldCost));
                }
                entityWorld.setComponent(entity, new InventoryComponent(Util.convertToArray(inventoryItemEntities)));
                entityWorld.setComponent(entity, new RequestUpdateAttributesComponent());
                return true;
            }
        }
        return false;
    }
    
    private static int resolveItemRecipe(EntityWorld entityWorld, String itemID, LinkedList<Integer> inventoryItemEntities, int tmpItemEntity){
        entityWorld.removeEntity(tmpItemEntity);
        EntityTemplate.loadTemplate(entityWorld, tmpItemEntity, "items/" + itemID);
        ItemRecipeComponent itemRecipeComponent = entityWorld.getComponent(tmpItemEntity, ItemRecipeComponent.class);
        int goldCost = itemRecipeComponent.getGold();
        for(String ingredientID : itemRecipeComponent.getItemIDs()){
            boolean ingrendientHasToBeBought = true;
            for(Integer inventoryItemEntity : inventoryItemEntities){
                String inventoryItemID = entityWorld.getComponent(inventoryItemEntity, ItemIDComponent.class).getID();
                if(inventoryItemID.equals(ingredientID)){
                    inventoryItemEntities.remove(inventoryItemEntity);
                    ingrendientHasToBeBought = false;
                    break;
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
