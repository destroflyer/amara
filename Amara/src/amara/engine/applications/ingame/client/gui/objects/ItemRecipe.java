/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.gui.objects;

import amara.Util;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;

/**
 *
 * @author Carl
 */
public class ItemRecipe{

    public ItemRecipe(EntityWrapper item, int gold, ItemRecipe[] ingredientsRecipes, int depth){
        this.item = item;
        this.gold = gold;
        this.ingredientsRecipes = ingredientsRecipes;
        this.depth = depth;
        generateDescription();
        updateTotalGold();
    }
    private EntityWrapper item;
    private int gold;
    private ItemRecipe[] ingredientsRecipes;
    private int depth;
    private String description;
    private int totalGold;
    private int resolvedGold = -1;

    public EntityWrapper getItem(){
        return item;
    }

    public int getGold(){
        return gold;
    }

    public ItemRecipe[] getIngredients(){
        return ingredientsRecipes;
    }

    public int getDepth(){
        return depth;
    }
    
    private void generateDescription(){
        description = "";
        BonusFlatWalkSpeedComponent bonusFlatWalkSpeedComponent = item.getComponent(BonusFlatWalkSpeedComponent.class);
        if(bonusFlatWalkSpeedComponent != null){
            addDescriptionSeperator();
            description += getSignedValueText(bonusFlatWalkSpeedComponent.getValue()) + " Walk Speed";
        }
        BonusPercentageWalkSpeedComponent bonusPercentageWalkSpeedComponent = item.getComponent(BonusPercentageWalkSpeedComponent.class);
        if(bonusPercentageWalkSpeedComponent != null){
            addDescriptionSeperator();
            description += getSignedValueText(bonusPercentageWalkSpeedComponent.getValue() * 100) + "% Walk Speed";
        }
        BonusFlatMaximumHealthComponent bonusFlatMaximumHealthComponent = item.getComponent(BonusFlatMaximumHealthComponent.class);
        if(bonusFlatMaximumHealthComponent != null){
            addDescriptionSeperator();
            description += getSignedValueText(bonusFlatMaximumHealthComponent.getValue()) + " Health";
        }
        BonusFlatHealthRegenerationComponent bonusFlatHealthRegenerationComponent = item.getComponent(BonusFlatHealthRegenerationComponent.class);
        if(bonusFlatHealthRegenerationComponent != null){
            addDescriptionSeperator();
            description += getSignedValueText(bonusFlatHealthRegenerationComponent.getValue()) + " Health Regeneration (per Second)";
        }
        BonusFlatAttackDamageComponent bonusFlatAttackDamageComponent = item.getComponent(BonusFlatAttackDamageComponent.class);
        if(bonusFlatAttackDamageComponent != null){
            addDescriptionSeperator();
            description += getSignedValueText(bonusFlatAttackDamageComponent.getValue()) + " Attack Damage";
        }
        BonusPercentageAttackSpeedComponent bonusPercentageAttackSpeedComponent = item.getComponent(BonusPercentageAttackSpeedComponent.class);
        if(bonusPercentageAttackSpeedComponent != null){
            addDescriptionSeperator();
            description += getSignedValueText(bonusPercentageAttackSpeedComponent.getValue() * 100) + "% Attack Speed";
        }
        BonusPercentageCriticalChanceComponent bonusPercentageCriticalChanceComponent = item.getComponent(BonusPercentageCriticalChanceComponent.class);
        if(bonusPercentageCriticalChanceComponent != null){
            addDescriptionSeperator();
            description += getSignedValueText(bonusPercentageCriticalChanceComponent.getValue() * 100) + "% Critical Chance";
        }
        BonusPercentageLifestealComponent bonusPercentageLifestealComponent = item.getComponent(BonusPercentageLifestealComponent.class);
        if(bonusPercentageLifestealComponent != null){
            addDescriptionSeperator();
            description += getSignedValueText(bonusPercentageLifestealComponent.getValue() * 100) + "% Lifesteal";
        }
        BonusFlatAbilityPowerComponent bonusFlatAbilityPowerComponent = item.getComponent(BonusFlatAbilityPowerComponent.class);
        if(bonusFlatAbilityPowerComponent != null){
            addDescriptionSeperator();
            description += getSignedValueText(bonusFlatAbilityPowerComponent.getValue()) + " Ability Power";
        }
        BonusPercentageCooldownSpeedComponent bonusPercentageCooldownSpeedComponent = item.getComponent(BonusPercentageCooldownSpeedComponent.class);
        if(bonusPercentageCooldownSpeedComponent != null){
            addDescriptionSeperator();
            description += getSignedValueText(bonusPercentageCooldownSpeedComponent.getValue()) + " Cooldown Speed";
        }
        BonusFlatArmorComponent bonusFlatArmorComponent = item.getComponent(BonusFlatArmorComponent.class);
        if(bonusFlatArmorComponent != null){
            addDescriptionSeperator();
            description += getSignedValueText(bonusFlatArmorComponent.getValue()) + " Armor";
        }
        BonusFlatMagicResistanceComponent bonusFlatMagicResistanceComponent = item.getComponent(BonusFlatMagicResistanceComponent.class);
        if(bonusFlatMagicResistanceComponent != null){
            addDescriptionSeperator();
            description += getSignedValueText(bonusFlatMagicResistanceComponent.getValue()) + " Magic Resistance";
        }
    }
    
    private void addDescriptionSeperator(){
        if(description.length() > 0){
            description += ", ";
        }
    }
    
    private String getSignedValueText(float value){
        value = Util.compensateFloatRoundingErrors(value);
        String text = "";
        if(value >= 0){
            text += "+";
        }
        int valueInt = (int) value;
        if(value == valueInt){
            text += valueInt;
        }
        else{
            text += value;
        }
        return text;
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

    public int getTotalGold(){
        return totalGold;
    }
    
    public void updateResolvedGold(ItemRecipe[] inventoryItemsRecipes){
        resolvedGold = resolveGold(inventoryItemsRecipes, new boolean[inventoryItemsRecipes.length]);
    }
    
    private int resolveGold(ItemRecipe[] inventoryItemsRecipes, boolean[] usedInventoryIngredients){
        int neededGold = gold;
        for(ItemRecipe ingredientRecipe : ingredientsRecipes){
            boolean ingrendientHasToBeBought = true;
            for(int r=0;r<inventoryItemsRecipes.length;r++){
                if((!usedInventoryIngredients[r]) && (inventoryItemsRecipes[r] == ingredientRecipe)){
                    ingrendientHasToBeBought = false;
                    usedInventoryIngredients[r] = true;
                    break;
                }
            }
            if(ingrendientHasToBeBought){
                neededGold += ingredientRecipe.resolveGold(inventoryItemsRecipes, usedInventoryIngredients);
            }
        }
        return neededGold;
    }

    public int getResolvedGold(){
        return resolvedGold;
    }
}
