/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.gui.objects;

import java.util.LinkedList;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class ItemRecipe{

    public ItemRecipe(EntityWorld entityWorld, int entity, float gold, ItemRecipe[] ingredientsRecipes, int depth){
        this.entityWorld = entityWorld;
        this.entity = entity;
        this.gold = gold;
        this.ingredientsRecipes = ingredientsRecipes;
        this.depth = depth;
        description = ItemDescription.generate_Description(entityWorld, entity);
        updateTotalGold();
    }
    private EntityWorld entityWorld;
    private int entity;
    private float gold;
    private ItemRecipe[] ingredientsRecipes;
    private int depth;
    private String description;
    private float totalGold;
    private float resolvedGold = -1;

    public int getEntity(){
        return entity;
    }

    public float getGold(){
        return gold;
    }

    public ItemRecipe[] getIngredients(){
        return ingredientsRecipes;
    }

    public int getDepth(){
        return depth;
    }

    public String getDescription(){
        return description;
    }
    
    private void updateTotalGold(){
        totalGold = gold;
        for(ItemRecipe ingredientRecipe : ingredientsRecipes){
            ingredientRecipe.updateTotalGold();
            totalGold += ingredientRecipe.getTotalGold();
        }
    }

    public float getTotalGold(){
        return totalGold;
    }
    
    public void updateResolvedGold(LinkedList<ItemRecipe> inventoryItemsRecipes){
        resolvedGold = resolveGold(inventoryItemsRecipes, new boolean[inventoryItemsRecipes.size()]);
    }
    
    private float resolveGold(LinkedList<ItemRecipe> inventoryItemsRecipes, boolean[] usedInventoryIngredients){
        float neededGold = gold;
        for(ItemRecipe ingredientRecipe : ingredientsRecipes){
            boolean ingrendientHasToBeBought = true;
            int i = 0;
            for(ItemRecipe inventoryItemRecipe : inventoryItemsRecipes){
                if((!usedInventoryIngredients[i]) && (inventoryItemRecipe == ingredientRecipe)){
                    ingrendientHasToBeBought = false;
                    usedInventoryIngredients[i] = true;
                    break;
                }
                i++;
            }
            if(ingrendientHasToBeBought){
                neededGold += ingredientRecipe.resolveGold(inventoryItemsRecipes, usedInventoryIngredients);
            }
        }
        return neededGold;
    }

    public float getResolvedGold(){
        return resolvedGold;
    }
}
